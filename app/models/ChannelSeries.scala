package models

import anorm.NamedParameter
import play.api.libs.json.{JsValue, Json}

case class ChannelSeries(id: String, channelID: String, name: String) extends Model {
	val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelID -> channelID, 'name -> name)

	def toJSON: JsValue = Json.obj(
		"id" -> id,
		"channelId" -> channelID,
		"name" -> name
	)
}