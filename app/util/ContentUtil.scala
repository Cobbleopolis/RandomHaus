package util

import anorm.NamedParameter
import models.{Channel, ChannelContent, ChannelContentTag, ChannelSeries}
import play.api.db.Database

object ContentUtil {

    def updateAll(implicit db: Database): Unit = {
        Channel.getAll.foreach(channel => {
            updateChannelContent(channel.channelId)
        })
    }

    def updateChannelContent(channelId: String)(implicit db: Database): Unit = {
        ChannelSeries.getBy(classOf[Channel], 'channelId -> channelId).foreach(series => {
            val playlistItems = YTUtil.getPlaylistItems(series.id)
            playlistItems.foreach(playlistItem => {
                val id: String = playlistItem.getContentDetails.getVideoId
                print(id + " | ")
                val contentOpt: Option[ChannelContent] = ChannelContent.get(id)
                if (contentOpt.isDefined) {
                    println("Exists")
                    val tags = contentOpt.get.getTags.map(_.tag)
                    ChannelContentTag.insertBatchParams(
                        YTUtil.getVideoTags(id).filterNot(t => tags.contains(t)).map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                    )
                } else {
                    println("New")
                    ChannelContent.insert(new ChannelContent(id, channelId, series.id))
                    ChannelContentTag.insertBatchParams(
                        YTUtil.getVideoTags(id).map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                    )
                }
            })

        })
    }

}
