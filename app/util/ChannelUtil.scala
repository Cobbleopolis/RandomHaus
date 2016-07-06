package util

import java.util.Date

import models.{Channel, ChannelSeries}
import play.api.Logger
import play.api.db.Database
import reference.InsideGaming

object ChannelUtil {

    def updateAllSeries()(implicit db: Database): Unit = {
        Channel.getAll.foreach(f = channel => {
            updateSeriesFromPlaylists(channel.channelId)
        })
    }

    def updateSeriesFromPlaylists(channelId: String)(implicit db: Database): Unit = {
        Channel.updateChannelCurrentlyUpdating(channelId, "series")
        try {
            Logger.info("Updating Sources For " + channelId + "...")
            YTUtil.getAllPlaylistsFromUser(channelId).map(playlist => {
                new ChannelSeries(playlist.getId, channelId, playlist.getSnippet.getTitle, new Date(playlist.getSnippet.getPublishedAt.getValue))
            }).foreach(series => ChannelSeries.insert(series))
            if (channelId == InsideGaming.funhausChannelId)
                ChannelSeries.insert(InsideGaming.insideGamingSeries)
            Logger.info("Finished Updating Sources For " + channelId)
        } catch {
            case e: Throwable =>
                Logger.error("Error updating series for " + channelId, e)
                throw e
        } finally {
            Channel.updateChannelCurrentlyUpdating(channelId, null)
        }
    }

    def getChannelSeries(channelID: String)(implicit db: Database): Unit = {

    }

}
