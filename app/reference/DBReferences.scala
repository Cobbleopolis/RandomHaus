package reference

import anorm.SqlParser._
import anorm._
import models.{Channel, ChannelSource}

object DBReferences {

	val getChannel = SQL("CALL getChannel({channelID});")
	val getChannelParser = for {
		channelID <- str("channelID")
		name <- str("name")
	} yield new Channel(channelID, name)

	val getAllChannels = SQL("SELECT * FROM channels;")

	val getChannelSources = SQL("CALL getChannelSources({channelID});")
	val insertChannelSource = SQL("CALL insertChannelSources({id}, {channelID}, {isPlaylist});")

	val channelSourceParser = for {
		id <- str("id")
		channelID <- str("channelID")
		isPlaylist <- bool("isPlaylist")
	} yield new ChannelSource(id, channelID, isPlaylist)

	val insertChannelContent = SQL("CALL insertChannelContent({id}, {channelID}, {isPlaylist});")
}
