package util

import models.ContentSeriesLink
import play.api.Logger
import play.api.db.Database
import reference.InsideGaming

object SeriesUtil {

    def updateLinks(seriesId: String)(implicit db: Database): Unit = {
        //TODO update channel's updating status
        try {
            Logger.info(s"Updating series links for $seriesId...")
            if (seriesId != InsideGaming.insideGamingSeries.id) {
                YTUtil.getPlaylistItems(seriesId).foreach(playlistItem =>
                    ContentSeriesLink.insert(ContentSeriesLink(playlistItem.getContentDetails.getVideoId, seriesId))
                )
            } else {
                YTUtil.getInsideGamingVideos.foreach(playlistItem =>
                    ContentSeriesLink.insert(ContentSeriesLink(playlistItem.getContentDetails.getVideoId, seriesId))
                )
            }
            Logger.info("Finished updating series links for " + seriesId)
        } catch {
            case e: Throwable =>
                Logger.error("Error updating series links for " + seriesId, e)
                throw e
        } finally {
            //TODO update channel's updating status
        }

    }

}
