package reference

import anorm.SqlParser._
import anorm._
import models.{Channel, ChannelContent, ChannelSeries}

object DBReferences {

	val getChannel = SQL("CALL getChannel({channelId});")
	val getChannelParser = for {
		channelID <- str("channelId")
		name <- str("name")
	} yield new Channel(channelID, name)

	val getAllChannels = SQL("SELECT * FROM channels;")

	val getAllSeries = SQL("SELECT * FROM channelSeries;")
	val getSeries = SQL("CALL getSeries({seriesId});")
	val getChannelSeries = SQL("CALL getChannelSeries({channelId});")
	val getChannelContent = SQL("CALL getChannelContent({channelId});")
	val insertChannelSeries = SQL("CALL insertChannelSeries({id}, {channelId}, {name});")

	val channelSeriesParser = for {
		id <- str("id")
		channelID <- str("channelId")
		name <- str("name")
	} yield new ChannelSeries(id, channelID, name)

	val insertChannelContent = SQL("CALL insertChannelContent({id}, {channelId}, {seriesId});")

	val getAllContent = SQL("SELECT * FROM channelContent;")
	val getContent = SQL("CALL getContent({contentId});")
	val channelContentParser = for {
		id <- str("id")
		channelID <- str("channelId")
		seriesID <- str("seriesId")
	} yield new ChannelContent(id, channelID, seriesID)
}
