package models

import anorm.NamedParameter
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable.ListBuffer

case class ChannelContent(id: String, channelId: String, seriesId: String, name: String) extends Model {
    def addTag(tag: ChannelContentTag) = {
        tags += tag
    }

    def getTags: List[ChannelContentTag] = tags.toList

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelID -> channelId, 'seriesID -> seriesId, 'name -> name)

    private var tags: ListBuffer[ChannelContentTag] = ListBuffer()

    def toJSON: JsValue = Json.obj(
        "id" -> id,
        "channelId" -> channelId,
        "seriesId" -> seriesId,
	    "name" -> name
    )
}
