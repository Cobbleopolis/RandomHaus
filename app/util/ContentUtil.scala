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
					val contentTags: List[String] = contentOpt.get.getTags.map(tag => tag.tag)
					val tags = YTUtil.getVideoTags(id).filterNot(s => contentTags.contains(s))
					ChannelContentTag.insertBatchParams(tags.map(s => Seq[NamedParameter]('contentId -> id, 'tag -> s)))
				} else {
					println("New")
					val content = new ChannelContent(id, channelId, series.id)
					ChannelContent.insert(content)
					val tags = YTUtil.getVideoTags(id)
					ChannelContentTag.insertBatchParams(tags.map(s => Seq[NamedParameter]('contentId -> id, 'tag -> s)))
				}
			})

		})
	}

}
