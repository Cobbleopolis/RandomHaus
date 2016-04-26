package models

import anorm.NamedParameter
import play.api.libs.json.{JsValue, Json, Writes}

import scala.util.parsing.json.JSONObject

case class Channel(channelID: String, name: String) extends Model {

    val namedParameters: Seq[NamedParameter] = Seq('channelID -> channelID, 'name -> name)
    def toJSON: JsValue = Json.obj(
        "channelID" -> channelID,
        "name" -> name
    )

}
