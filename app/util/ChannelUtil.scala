package util

import java.util.Date

import models.ChannelSeries
import play.api.db.Database

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ChannelUtil {

	def updateSeriesFromPlaylists(channelID: String)(implicit db: Database): Future[Unit] = Future {
		YTUtil.getAllPlaylistsFromUser(channelID).map(playlist =>{
			new ChannelSeries(playlist.getId, channelID, playlist.getSnippet.getTitle, new Date(playlist.getSnippet.getPublishedAt.getValue))
		}
		).foreach(series => ChannelSeries.insert(series))
	}

	def getChannelSeries(channelID: String)(implicit db: Database): Future[Unit] = Future {

	}

}
