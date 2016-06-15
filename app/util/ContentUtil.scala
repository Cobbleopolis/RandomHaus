package util

import models.{Channel, ChannelContent, ChannelSeries}
import play.api.db.Database

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ContentUtil {

	def updateAll(implicit db: Database): Future[Unit] = Future {
		Channel.getAllChannels.foreach(channel => {
			updateChannelContent(channel.channelId)
		})
	}

	def updateChannelContent(channelId: String)(implicit db: Database): Future[Unit] = Future {
		ChannelSeries.getChannelSeries(channelId).foreach(series => {
			val playlistItems = YTUtil.getPlaylistItems(series.id)
			playlistItems.foreach(playlistItem => {
                val content = new ChannelContent(playlistItem.getContentDetails.getVideoId, channelId, series.id)
				ChannelContent.insertChannelContent(content)
			})

		})
	}

}
