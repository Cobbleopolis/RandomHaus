package models

import anorm._
import play.api.db.Database

case class ChannelContent(id: String, channelId: String, seriesId: String) extends Model {

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'seriesId -> seriesId)

//    private var tags: ListBuffer[ChannelContentTag] = ListBuffer()
//
//    def addTag(tag: ChannelContentTag) = {
//        tags += tag
//    }
//
    def getTags(implicit db: Database): List[ChannelContentTag] = ChannelContentTag.getBy(classOf[ChannelContent], 'contentId -> id)
}

object ChannelContent extends ModelAccessor[ChannelContent] {

    override val getQuery = SQL("CALL getContent({contentId});")
    override val getAllQuery = SQL("SELECT * FROM channelContent;")
    override val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("CALL getChannelContent({channelId});")
    )

    override val insertQuery = "CALL insertChannelContent({id}, {channelId}, {seriesId});"

    override val parser: RowParser[ChannelContent] = Macro.namedParser[ChannelContent].asInstanceOf[RowParser[ChannelContent]]

    override val idSymbol: Symbol = 'contentId

}
