package models

import anorm.NamedParameter
import play.api.libs.json.{JsValue, Json}

case class ChannelContentTag(id: Int, contentID: String, category: String, value: String) extends Model{
	val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'contentID -> contentID, 'category -> category, 'value -> value)

	def toJSON: JsValue = Json.obj(
		"id" -> id,
		"contentID" -> contentID,
        "category" -> category,
		"value" -> value
	)
}
