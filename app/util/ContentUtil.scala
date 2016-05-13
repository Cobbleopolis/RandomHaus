package util

import models.ChannelContent
import play.api.db.Database

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ContentUtil {

	def updateAll(implicit db: Database): Future[Unit] = Future {
		DBUtil.getAllChannels.foreach(channel => {
			updateChannelContent(channel.channelId)
		})
	}

	def updateChannelContent(channelId: String)(implicit db: Database): Future[Unit] = Future {
		DBUtil.getChannelSeries(channelId).foreach(series => {
			val playlistItems = YTUtil.getPlaylistItems(series.id)
			playlistItems.foreach(playlistItem => {
				DBUtil.insertChannelContent(new ChannelContent(playlistItem.getContentDetails.getVideoId, channelId, series.id))
			})

		})
	}

}
