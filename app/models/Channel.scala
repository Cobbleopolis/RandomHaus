package models

import anorm.NamedParameter
import play.api.db.Database
import play.api.libs.json.{JsObject, JsValue, Json}
import util.DBUtil

case class Channel(channelId: String, name: String) extends Model {

	def this(jsonObj: JsObject) = this(
		(jsonObj \ "channelId").as[String],
		(jsonObj \ "name").as[String]
	)

	val namedParameters: Seq[NamedParameter] = Seq('channelID -> channelId, 'name -> name)

	def toJSON: JsValue = Json.obj(
		"channelId" -> channelId,
		"name" -> name
	)

	def getSeries(implicit db: Database): List[ChannelSeries] = DBUtil.getChannelSeries(channelId)

	def getContent(implicit db: Database): List[ChannelContent] = DBUtil.getChannelContent(channelId)

}
