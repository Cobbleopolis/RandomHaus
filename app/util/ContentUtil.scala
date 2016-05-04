package util

import models.{ChannelContent, ChannelContentTag}
import play.api.db.Database

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ContentUtil {

    def updateAll(implicit db: Database): Future[Unit] = Future {
        DBUtil.getAllChannels.foreach(channel => {
            updateChannelContent(channel.channelID)
        })
    }

    def updateChannelContent(channelId: String)(implicit db: Database): Future[Unit] = Future {
        DBUtil.getChannelSeries(channelId).foreach(series => {
            val playlistItems = YTUtil.getPlaylistItems(series.id)
            playlistItems.foreach(playlistItem => {
                DBUtil.insertChannelContent(new ChannelContent(playlistItem.getContentDetails.getVideoId, channelId, series.id, playlistItem.getSnippet.getTitle))
            })

        })
    }
    
}
