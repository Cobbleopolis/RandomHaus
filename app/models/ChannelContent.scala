package models

import anorm._
import play.api.db.Database
import reference.DBReferences

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

object ChannelContent {

    private val getContentQuery = SQL("CALL getContent({contentId});")
    private val getAllContentQuery = SQL("SELECT * FROM channelContent;")
    private val getChannelContentQuery = SQL("CALL getChannelContent({channelId});")

    private val insertChannelContentQuery = SQL("CALL insertChannelContent({id}, {channelId}, {seriesId});")

    def getContent(contentID: String)(implicit db: Database): Option[ChannelContent] = {
        db.withConnection(implicit conn => {
            getContentQuery.on('contentId -> contentID).as(DBReferences.channelContentParser.singleOpt)
        })
    }

    def getAllContent(implicit db: Database): List[ChannelContent] = {
        db.withConnection(implicit conn => {
            getAllContentQuery.as(DBReferences.channelContentParser.*)
        })
    }

    def getChannelContent(channelId: String)(implicit db: Database): List[ChannelContent] = {
        db.withConnection(implicit conn => {
            getChannelContentQuery.on('channelId -> channelId).as(DBReferences.channelContentParser.*)
        })
    }

    def getChannelContent(channel: Channel)(implicit db: Database): List[ChannelContent] = getChannelContent(channel.channelId)

    def insertChannelContent(channelContent: ChannelContent)(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            insertChannelContentQuery.on(channelContent.namedParameters: _*).executeInsert()
        })

    }

    def insertChannelContentBatch(channelContent: Seq[ChannelContent])(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            BatchSql(insertChannelContentQuery.toString, channelContent.head.namedParameters, channelContent.tail.map(_.namedParameters): _*)
        })

    }

}
