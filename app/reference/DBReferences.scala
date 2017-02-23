package reference

import anorm.{Macro, RowParser, SQL}
import models._

object DBReferences {

    //Parsers

    implicit val channelParser: RowParser[Channel] = Macro.namedParser[Channel].asInstanceOf[RowParser[Channel]]

    implicit val channelContentParser: RowParser[ChannelContent] = Macro.namedParser[ChannelContent].asInstanceOf[RowParser[ChannelContent]]

    implicit val channelContentTagParser: RowParser[ChannelContentTag] = Macro.namedParser[ChannelContentTag].asInstanceOf[RowParser[ChannelContentTag]]

    implicit val channelSeriesParser: RowParser[ChannelSeries] = Macro.namedParser[ChannelSeries].asInstanceOf[RowParser[ChannelSeries]]

    implicit val channelLinkParser: RowParser[ChannelLink] = Macro.namedParser[ChannelLink].asInstanceOf[RowParser[ChannelLink]]
}
