package models

import anorm.NamedParameter
import play.api.libs.json.{JsValue, Json}

case class Channel(channelID: String, name: String) extends Model {

    val namedParameters: Seq[NamedParameter] = Seq('channelID -> channelID, 'name -> name)

    def toJSON: JsValue = Json.obj(
        "channelId" -> channelID,
        "name" -> name
    )

}
