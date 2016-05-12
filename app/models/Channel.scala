package models

import anorm.NamedParameter
import play.api.db.Database
import play.api.libs.json.{JsValue, Json}
import util.DBUtil

case class Channel(channelID: String, name: String) extends Model {

	val namedParameters: Seq[NamedParameter] = Seq('channelID -> channelID, 'name -> name)

	def toJSON: JsValue = Json.obj(
		"channelId" -> channelID,
		"name" -> name
	)

	def getSeries(implicit db: Database): List[ChannelSeries] = DBUtil.getChannelSeries(channelID)

	def getContent(implicit db: Database): List[ChannelContent] = DBUtil.getChannelContent(channelID)

}
