package models

import anorm.{BatchSql, NamedParameter, RowParser, SqlQuery}
import play.api.db.Database

trait ModelAccessor[T <: Model] {

    val getQuery: SqlQuery
    val getAllQuery: SqlQuery
    val getByQueryList: Map[Class[_ <: Model], SqlQuery]

    val insertQuery: SqlQuery

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
            insertQuery.on(model.namedParameters: _*).executeInsert()
        })
    }

    def insertBatch(models: Seq[T])(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            BatchSql(insertQuery.toString, models.head.namedParameters, models.tail.map(_.namedParameters): _*)
        })
    }
}
