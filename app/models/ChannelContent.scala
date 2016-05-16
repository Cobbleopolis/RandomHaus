package models

import anorm.NamedParameter
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.collection.mutable.ListBuffer

case class ChannelContent(id: String, channelId: String, seriesId: String) extends Model {

	def this(jsonObj: JsObject) = this(
		(jsonObj \ "id").as[String],
		(jsonObj \ "channelId").as[String],
		(jsonObj \ "seriesId").as[String]
	)

	val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'seriesId -> seriesId)

	private var tags: ListBuffer[ChannelContentTag] = ListBuffer()

	def addTag(tag: ChannelContentTag) = {
		tags += tag
	}

	def getTags: List[ChannelContentTag] = tags.toList

	def toJSON: JsValue = Json.obj(
		"id" -> id,
		"channelId" -> channelId,
		"seriesId" -> seriesId
	)
}
