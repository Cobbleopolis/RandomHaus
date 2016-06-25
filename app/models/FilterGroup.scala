package models

import anorm.{Macro, RowParser, SqlQuery, _}

case class FilterGroup(id: Int, channelId: String, name: String) extends Model {

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'name -> name)
    
}

object FilterGroup extends ModelAccessor[FilterGroup] {
    val getQuery = SQL("SELECT SELECT * from filterGroups WHERE id = {id};")
    val getAllQuery = SQL("SELECT SELECT * from filterGroups")

    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("SELECT * from filterGroups WHERE channelId = {channelId};")
    )

    val insertQuery = "insert into filterGroups (channelId, name) VALUES ({channelId}, {name});"

    val parser: RowParser[FilterGroup] = Macro.namedParser[FilterGroup].asInstanceOf[RowParser[FilterGroup]]

    val idSymbol: Symbol = 'id
}
