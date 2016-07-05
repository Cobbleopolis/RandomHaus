package util

import java.util.Date

import models.{Channel, ChannelSeries}
import play.api.Logger
import play.api.db.Database
import reference.InsideGaming

object ChannelUtil {

    def updateAllSeries()(implicit db: Database): Unit = {
        Channel.getAll.foreach(f = channel => {
            Logger.info("Updating Sources For " + channel.name + "...")
            updateSeriesFromPlaylists(channel.channelId)
            Logger.info("Finished Updating Sources For " + channel.name)
        })
    }

    def updateSeriesFromPlaylists(channelID: String)(implicit db: Database): Unit = {
        YTUtil.getAllPlaylistsFromUser(channelID).map(playlist => {
            new ChannelSeries(playlist.getId, channelID, playlist.getSnippet.getTitle, new Date(playlist.getSnippet.getPublishedAt.getValue))
        }).foreach(series => ChannelSeries.insert(series))
        if (channelID == InsideGaming.funhausChannelId)
            ChannelSeries.insert(InsideGaming.insideGamingSeries)
    }

    def getChannelSeries(channelID: String)(implicit db: Database): Unit = {

    }

}
