package models

import anorm._
import play.api.db.Database

trait ModelAccessor[T <: Model] {

    val getQuery: SqlQuery
    val getAllQuery: SqlQuery
    val getByQueryList: Map[Class[_ <: Model], SqlQuery]

    val insertQuery: String

    val parser: RowParser[T]

    val idSymbol: Symbol

    def get(id: String)(implicit db: Database): Option[T] = {
        db.withConnection(implicit conn => {
            getQuery.on(idSymbol -> id).as(parser.singleOpt)
        })
    }

    def getAll(implicit db: Database): List[T] = {
        db.withConnection(implicit conn => {
            getAllQuery.as(parser.*)
        })
    }

    def getBy(byType: Class[_ <: Model], params: NamedParameter*)(implicit db: Database): List[T] = {
        val query: Option[SqlQuery] = getByQueryList.get(byType)
        query match {
            case Some(q) =>
                db.withConnection(implicit conn => {
                    q.on(params: _*).as(parser.*)
                })
            case None => throw new IllegalArgumentException("byType is not defined in the getByQueryList")
        }
    }


    def insert(model: T)(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            SQL(insertQuery).on(model.namedParameters: _*).executeInsert()
        })
    }

    def insert(params: NamedParameter*)(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            SQL(insertQuery).on(params: _*).executeInsert()
        })
    }

    def insertBatch(models: Seq[T])(implicit db: Database): Unit = {
        if (models.nonEmpty)
            insertBatchParams(models.map(_.namedParameters))
    }

    def insertBatchParams(params: Seq[Seq[NamedParameter]])(implicit db: Database): Unit = {
        val p: Seq[Seq[NamedParameter]] = params.filter(m => m.forall(p => p.value.show != ""))
        try {
            if (p.nonEmpty)
                db.withConnection(implicit conn => {
                    if (p.length == 1)
                        BatchSql(insertQuery, p.head).execute()
                    else
                        BatchSql(insertQuery, p.head, p.tail.flatten).execute()
                })
        } catch {
            case t: Throwable =>
                println(p)
                throw t
        }
    }
}
