package reference

import models._
import play.api.libs.json.Json

object JsonReference {

    implicit val channelParser = Json.format[Channel]

    implicit val channelContentParser = Json.format[ChannelContent]

    implicit val channelContentTagParser = Json.format[ChannelContentTag]

    implicit val channelSeriesParser = Json.format[ChannelSeries]

    implicit val channelLinkParser = Json.format[ChannelLink]
    
}
