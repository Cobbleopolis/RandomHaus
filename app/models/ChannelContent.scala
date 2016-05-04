package models

import anorm.NamedParameter
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable.ListBuffer

case class ChannelContent(id: String, channelID: String, seriesID: String, name: String) extends Model {
    def addTag(tag: ChannelContentTag) = {
        tags += tag
    }

    def getTags: List[ChannelContentTag] = tags.toList

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelID -> channelID, 'seriesID -> seriesID, 'name -> name)

    private var tags: ListBuffer[ChannelContentTag] = ListBuffer()

    def toJSON: JsValue = Json.obj(
        "id" -> id,
        "channelID" -> channelID,
        "seriesID" -> seriesID,
	    "name" -> name
    )
}
