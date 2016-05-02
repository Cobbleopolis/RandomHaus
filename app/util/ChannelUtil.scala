package util

import models.ChannelSeries
import play.api.db.Database

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ChannelUtil {

	def updateSeriesFromPlaylists(channelID: String)(implicit db: Database): Future[Unit] = Future {
        YTUtil.getAllPlaylistsFromUser(channelID).map(playlist =>
            new ChannelSeries(playlist.getId, channelID, playlist.getSnippet.getTitle)
        ).foreach(series => DBUtil.insertChannelSeries(series))
	}

    def getChannelSeries(channelID: String)(implicit db: Database): Future[Unit] = Future {

    }
    
}
