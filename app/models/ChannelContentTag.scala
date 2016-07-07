package models

import anorm._

case class ChannelContentTag(id: Int, contentId: String, tag: String) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'contentId -> contentId, 'tag -> tag)
}

object ChannelContentTag extends ModelAccessor[ChannelContentTag] {
    val getQuery = SQL("CALL getTag({tagId})")
    val getAllQuery = SQL("SELECT * FROM contentTag")
    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[ChannelContent] -> SQL("CALL getContentTags({contentId})")
    )

    val insertQuery = "CALL insertContentTag({contentId}, {tag})"

    val parser: RowParser[ChannelContentTag] = Macro.namedParser[ChannelContentTag].asInstanceOf[RowParser[ChannelContentTag]]

    val idSymbol: Symbol = 'tagId

}
