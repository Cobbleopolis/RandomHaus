package models

import anorm._

case class ChannelContent(id: String, channelId: String, seriesId: String) extends Model {

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'seriesId -> seriesId)

//    private var tags: ListBuffer[ChannelContentTag] = ListBuffer()
//
//    def addTag(tag: ChannelContentTag) = {
//        tags += tag
//    }
//
//    def getTags: List[ChannelContentTag] = tags.toList
}

object ChannelContent extends ModelAccessor[ChannelContent] {

    val getQuery = SQL("CALL getContent({contentId});")
    val getAllQuery = SQL("SELECT * FROM channelContent;")
    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("CALL getChannelContent({channelId});")
    )

    val insertQuery = SQL("CALL insertChannelContent({id}, {channelId}, {seriesId});")

    val parser: RowParser[ChannelContent] = Macro.namedParser[ChannelContent].asInstanceOf[RowParser[ChannelContent]]

    val idSymbol: Symbol = 'contentId

}
