package models

import anorm.NamedParameter
import play.api.libs.json.{JsObject, JsValue, Json}

case class ChannelSeries(id: String, channelID: String, name: String) extends Model {

	def this(jsonObj: JsObject) = this(
		(jsonObj \ "id").as[String],
		(jsonObj \ "channelId").as[String],
		(jsonObj \ "name").as[String]
	)

	val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelID -> channelID, 'name -> name)

	def toJSON: JsValue = Json.obj(
		"id" -> id,
		"channelId" -> channelID,
		"name" -> name
	)
}