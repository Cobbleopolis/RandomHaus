package models

import anorm._
import play.api.db.Database

import scala.collection.mutable.ArrayBuffer

case class Channel(channelId: String, name: String, backgroundCss: Option[String]) extends Model {

//	def this(jsonObj: JsObject) = this(
//		(jsonObj \ "channelId").as[String],
//		(jsonObj \ "name").as[String]
//	)

    val namedParameters: Seq[NamedParameter] = Seq('channelId -> channelId, 'name -> name, 'backgroundCss -> backgroundCss)

	def getSeries(implicit db: Database): List[ChannelSeries] = ChannelSeries.getBy(classOf[Channel], 'channelId -> channelId)

	def getContent(implicit db: Database): List[ChannelContent] = ChannelContent.getBy(classOf[Channel], 'channelId -> channelId)

    def getLinks(implicit db: Database): List[ChannelLink] = ChannelLink.getBy(classOf[Channel], 'channelId -> channelId)

}


object Channel extends ModelAccessor[Channel] {

    override val getQuery = SQL("CALL getChannel({channelId});")
    override val getAllQuery = SQL("SELECT * FROM channels;")

    override val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map()

    override val insertQuery = "CALL insertChannel({channelId}, {name}, {backgroundCss});"

    override val parser: RowParser[Channel] = Macro.namedParser[Channel].asInstanceOf[RowParser[Channel]]

    override val idSymbol: Symbol = 'channelId
}