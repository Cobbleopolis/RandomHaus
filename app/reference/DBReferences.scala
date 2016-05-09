package reference

import anorm.SqlParser._
import anorm._
import models.{Channel, ChannelContent, ChannelSeries}

object DBReferences {

	//Channel SQL calls
	val getChannel = SQL("CALL getChannel({channelId});")
	val getAllChannels = SQL("SELECT * FROM channels;")
	val getChannelSeries = SQL("CALL getChannelSeries({channelId});")
	val getChannelContent = SQL("CALL getChannelContent({channelId});")

	//Series SQL calls
	val getSeries = SQL("CALL getSeries({seriesId});")
	val getAllSeries = SQL("SELECT * FROM channelSeries;")
	val insertChannelSeries = SQL("CALL insertChannelSeries({id}, {channelId}, {name});")

	//Content SQL calls
	val getContent = SQL("CALL getContent({contentId});")
	val getAllContent = SQL("SELECT * FROM channelContent;")
	val insertChannelContent = SQL("CALL insertChannelContent({id}, {channelId}, {seriesId});")

	//Parsers

	val channelParser = for {
		channelID <- str("channelId")
		name <- str("name")
	} yield new Channel(channelID, name)

	val channelSeriesParser = for {
		id <- str("id")
		channelID <- str("channelId")
		name <- str("name")
	} yield new ChannelSeries(id, channelID, name)

	val channelContentParser = for {
		id <- str("id")
		channelID <- str("channelId")
		seriesID <- str("seriesId")
	} yield new ChannelContent(id, channelID, seriesID)
}
