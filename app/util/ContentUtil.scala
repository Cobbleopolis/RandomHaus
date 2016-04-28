package util

import models.ChannelContent
import play.api.db.Database

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ContentUtil {

    def updateAll(implicit db: Database): Future[Unit] = Future {
        DBUtil.getAllChannels.foreach(channel => {
            DBUtil.getChannelSources(channel.channelID).foreach(source => {
                if (source.isPlaylist)
                    YTUtil.getPlaylistItems(source.id).foreach(playlistItem => {
                        DBUtil.insertChannelContent(new ChannelContent(playlistItem.getContentDetails.getVideoId, channel.channelID, false))
                    })
                DBUtil.insertChannelContent(new ChannelContent(source.id, channel.channelID, source.isPlaylist))
            })
        })
    }
    
}
