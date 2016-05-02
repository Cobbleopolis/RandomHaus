package util

import models.ChannelSource
import play.api.db.Database

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ChannelUtil {

	def updateSourcesFromPlaylists(channelID: String)(implicit db: Database): Future[Unit] = Future {
		DBUtil.insertChannelSourceBatch(
			YTUtil.getAllPlaylistsFromUser(channelID).map(playlist =>
				new ChannelSource(playlist.getId, channelID, true)
			)
		)
	}
    
}
