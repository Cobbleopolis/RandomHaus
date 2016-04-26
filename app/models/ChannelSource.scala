package models

import anorm.NamedParameter
import play.api.libs.json.{JsValue, Json}

case class ChannelSource(id: String, channelID: String, isPlaylist: Boolean) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelID -> channelID, 'isPlaylist -> isPlaylist)

    def toJSON: JsValue = Json.obj(
        "id" -> id,
        "channelID" -> channelID,
        "isPlaylist" -> isPlaylist
    )
}