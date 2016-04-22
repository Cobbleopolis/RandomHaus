package services

import models.ChannelContent
import util.{DBUtil, YTUtil}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class ServiceYouTubeData extends YTData {

	def updateAllChannelContent(): Unit = {
		DBUtil.getAllChannels.foreach(channel => {
			DBUtil.getChannelSources(channel.channelID).foreach(source => {
				if (source.isPlaylist)
					YTUtil.getPlaylistItems(source.id).foreach(playlistItem => {
						DBUtil.insertChannelContent(new ChannelContent(playlistItem.getId, channel.channelID, false))
					})
			})
		})
	}

	val updateAllChannelData: Future[Unit] = Future {
		updateAllChannelContent()
	}

	println("Beginning YouTube Data update...")
	updateAllChannelData onSuccess {
		case _ => println("Update done")
	}

	updateAllChannelData onFailure {
		case e => println("Error occurred while updating \n" + e.toString)
	}

}
