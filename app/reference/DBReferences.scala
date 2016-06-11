package reference

import anorm.{Macro, RowParser, SQL}
import models._

object DBReferences {

    //Channel SQL calls
    val getChannel = SQL("CALL getChannel({channelId});")
    val getAllChannels = SQL("SELECT * FROM channels;")
    val getChannelSeries = SQL("CALL getChannelSeries({channelId});")
    val getChannelContent = SQL("CALL getChannelContent({channelId});")
    val getChannelLinks = SQL("CALL getChannelLinks({channelId});")

    //Series SQL calls
    val getSeries = SQL("CALL getSeries({seriesId});")
    val getAllSeries = SQL("SELECT * FROM channelSeries;")
    val insertChannelSeries = SQL("CALL insertChannelSeries({id}, {channelId}, {name}, {publishedAt});")

    //Content SQL calls
    val getContent = SQL("CALL getContent({contentId});")
    val getAllContent = SQL("SELECT * FROM channelContent;")
    val insertChannelContent = SQL("CALL insertChannelContent({id}, {channelId}, {seriesId});")

    //Link SQL calls
    val getLink = SQL("CALL getLink({id});")
    val getAllLinks = SQL("SELECT * FROM channelLinks;")
    val insertChannelLink = SQL("CALL insertChannelLink({id}, {channelId}, {label}, {link});")

    //Parsers

    implicit val channelParser = Macro.namedParser[Channel].asInstanceOf[RowParser[Channel]]

    implicit val channelContentParser = Macro.namedParser[ChannelContent].asInstanceOf[RowParser[ChannelContent]]

    implicit val channelContentTagParser = Macro.namedParser[ChannelContentTag].asInstanceOf[RowParser[ChannelContentTag]]

    implicit val channelSeriesParser = Macro.namedParser[ChannelSeries].asInstanceOf[RowParser[ChannelSeries]]

    implicit val channelLinkParser = Macro.namedParser[ChannelLink].asInstanceOf[RowParser[ChannelLink]]
}
