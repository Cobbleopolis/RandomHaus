package controllers

import com.google.inject.Inject
import models.ChannelContent
import play.api.mvc.{Action, Controller}
import play.api.db.Database
import util.{DBUtil, YTUtil}

class Data @Inject() (implicit db: Database) extends Controller {

    def updateAllChannelContent = Action {
        DBUtil.getAllChannels.foreach(channel => {
            DBUtil.getChannelSources(channel.channelID).foreach(source => {
                if (source.isPlaylist)
                    YTUtil.getPlaylistItems(source.id).foreach(playlistItem => {
                        DBUtil.insertChannelContent(new ChannelContent(playlistItem.getContentDetails.getVideoId, channel.channelID, false))
                    })
                DBUtil.insertChannelContent(new ChannelContent(source.id, channel.channelID, source.isPlaylist))
            })
        })
        Ok("OK")
    }
    
}
