package models

import anorm._

case class Filter(id: Int, filterGroupId: Int, name: String, tagName: String) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'filterGroupId -> filterGroupId, 'name -> name, 'tagName -> tagName)
}

object Filter extends ModelAccessor[Filter] {
    val getQuery = SQL("SELECT SELECT * from filters WHERE id = {id}")
    val getAllQuery = SQL("SELECT SELECT * from filters")

    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[FilterGroup] -> SQL("SELECT * from filters WHERE filterGroupId = {filterGroupId}")
    )

    val insertQuery = "insert into filters (channelId, name, tagName) VALUES ({channelId}, {name}, {tagName})"

    val parser: RowParser[Filter] = Macro.namedParser[Filter].asInstanceOf[RowParser[Filter]]

    val idSymbol: Symbol = 'id
}
