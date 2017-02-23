package reference

import models._
import play.api.libs.json.{Json, OFormat}

object JsonReference {

    implicit val channelParser: OFormat[Channel] = Json.format[Channel]

    implicit val channelContentParser: OFormat[ChannelContent] = Json.format[ChannelContent]

    implicit val channelContentTagParser: OFormat[ChannelContentTag] = Json.format[ChannelContentTag]

    implicit val channelSeriesParser: OFormat[ChannelSeries] = Json.format[ChannelSeries]

    implicit val channelLinkParser: OFormat[ChannelLink] = Json.format[ChannelLink]

}
