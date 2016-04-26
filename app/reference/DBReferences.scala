package reference

import anorm.SqlParser._
import anorm._
import models.{Channel, ChannelContent, ChannelSource}

object DBReferences {

    val getChannel = SQL("CALL getChannel({channelID});")
    val getChannelParser = for {
        channelID <- str("channelID")
        name <- str("name")
    } yield new Channel(channelID, name)

    val getAllChannels = SQL("SELECT * FROM channels;")

    val getAllSources = SQL("SELECT * FROM channelSources;")
    val getSource = SQL("CALL getSource({sourceID});")
    val getChannelSources = SQL("CALL getChannelSources({channelID});")
    val insertChannelSource = SQL("CALL insertChannelSources({id}, {channelID}, {isPlaylist});")

    val channelSourceParser = for {
        id <- str("id")
        channelID <- str("channelID")
        isPlaylist <- bool("isPlaylist")
    } yield new ChannelSource(id, channelID, isPlaylist)

    val insertChannelContent = SQL("CALL insertChannelContent({id}, {channelID}, {isPlaylist});")

    val getAllContent = SQL("SELECT * FROM channelContent;")
    val getContent = SQL("CALL getContent({contentID});")
    val channelContentParser = for {
        id <- str("id")
        channelID <- str("channelID")
        isPlaylist <- bool("isPlaylist")
    } yield new ChannelContent(id, channelID, isPlaylist)
}
