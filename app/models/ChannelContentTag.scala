package models

import anorm._

case class ChannelContentTag(id: Int, contentId: String, tag: String) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'contentId -> contentId, 'tag -> tag)
}

object ChannelContentTag extends ModelAccessor[ChannelContentTag] {
	override val getQuery: SqlQuery = SQL("CALL getTag({id});")
	override val getAllQuery: SqlQuery = SQL("SELECT * FROM contentTags;")

	override val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
		classOf[ChannelContent] -> SQL("CALL getContentTags({contentId});")
	)

	override val insertQuery = "INSERT INTO contentTags (contentId, tag) VALUES "

	override val parser: RowParser[ChannelContentTag] = Macro.namedParser[ChannelContentTag].asInstanceOf[RowParser[ChannelContentTag]]

	override val idSymbol: Symbol = 'id

}