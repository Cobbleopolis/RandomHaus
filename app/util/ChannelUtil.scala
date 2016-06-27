package util

import java.util.Date

import models.ChannelSeries
import play.api.db.Database
import reference.InsideGaming

object ChannelUtil {

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
