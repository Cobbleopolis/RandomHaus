package models

import anorm._

case class ChannelContentTag(id: Int, contentId: String, tag: String) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'contentId -> contentId, 'tag -> tag)
}

object ChannelContentTag extends ModelAccessor[ChannelContentTag] {
    val getQuery = SQL("SELECT * FROM contentTags WHERE tagId = {tagId}")
    val getAllQuery = SQL("SELECT * FROM contentTag")
    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[ChannelContent] -> SQL("SELECT * FROM contentTags WHERE contentId = {contentId}")
    )

    val insertQuery = "INSERT INTO contentTags (contentId, tag) values ({contentId}, {tag})"

    val parser: RowParser[ChannelContentTag] = Macro.namedParser[ChannelContentTag].asInstanceOf[RowParser[ChannelContentTag]]

    val idSymbol: Symbol = 'tagId

}
