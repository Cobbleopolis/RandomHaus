package models

import anorm.NamedParameter
import play.api.libs.json.{JsObject, JsValue, Json}

case class ChannelContentTag(id: Int, contentID: String, category: String, value: String) extends Model {

	def this(jsonObj: JsObject) = this(
		(jsonObj \ "id").as[Int],
		(jsonObj \ "contentId").as[String],
		(jsonObj \ "category").as[String],
		(jsonObj \ "value").as[String]
	)

	val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'contentID -> contentID, 'category -> category, 'value -> value)

	def toJSON: JsValue = Json.obj(
		"id" -> id,
		"contentId" -> contentID,
		"category" -> category,
		"value" -> value
	)
}
