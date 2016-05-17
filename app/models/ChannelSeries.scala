package models

import java.util.Date

import anorm.NamedParameter
import play.api.libs.json.{JsObject, JsValue, Json}

case class ChannelSeries(id: String, channelID: String, name: String, publishedAt: Date) extends Model {

	def this(jsonObj: JsObject) = this(
		(jsonObj \ "id").as[String],
		(jsonObj \ "channelId").as[String],
		(jsonObj \ "name").as[String],
		(jsonObj \ "publishedAt").as[Date]
	)

	val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelID, 'name -> name, 'publishedAt -> publishedAt)

	def toJSON: JsValue = Json.obj(
		"id" -> id,
		"channelId" -> channelID,
		"name" -> name,
		"publishedAt" -> publishedAt
	)
}