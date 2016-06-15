package models

import anorm._
import play.api.db.Database
import reference.DBReferences

case class Channel(channelId: String, name: String) extends Model {

//	def this(jsonObj: JsObject) = this(
//		(jsonObj \ "channelId").as[String],
//		(jsonObj \ "name").as[String]
//	)

    val namedParameters: Seq[NamedParameter] = Seq('channelID -> channelId, 'name -> name)

	def getSeries(implicit db: Database): List[ChannelSeries] = ChannelSeries.getChannelSeries(this)

	def getContent(implicit db: Database): List[ChannelContent] = ChannelContent.getChannelContent(this)

    def getLinks(implicit db: Database): List[ChannelLink] = ChannelLink.getChannelLinks(this)

}


object Channel {

    private val getChannelQuery = SQL("CALL getChannel({channelId});")
    private val getAllChannelsQuery = SQL("SELECT * FROM channels;")

    def getChannel(channelId: String)(implicit db: Database): Option[Channel] = {
        db.withConnection(implicit conn => {
            getChannelQuery.on('channelId -> channelId).as(DBReferences.channelParser.singleOpt)
        })
    }

    def getAllChannels(implicit db: Database): List[Channel] = {
        db.withConnection(implicit conn => {
            getAllChannelsQuery.as(DBReferences.channelParser.*)
        })

    }
}