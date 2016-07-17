package util

import anorm.NamedParameter
import models._
import play.api.Logger
import play.api.db.Database
import reference.InsideGaming

object ContentUtil {

    def updateAll()(implicit db: Database): Unit = {
        Channel.getAll.foreach(channel => {
            updateChannelContent(channel.channelId)
        })
    }

    def updateChannelContent(channelId: String)(implicit db: Database): Unit = {
        Channel.updateChannelCurrentlyUpdating(channelId, "content")
        try {
            Logger.info("Updating Content For " + channelId + "...")
            val watchedTags: List[String] = FilterGroup.getBy(classOf[Channel], 'channelId -> channelId).flatMap(filterGroup =>
                Filter.getBy(classOf[FilterGroup], 'filterGroupId -> filterGroup.id).map(_.tagName.toLowerCase))
            ChannelSeries.getBy(classOf[Channel], 'channelId -> channelId).foreach(series => {
                if (series.id != InsideGaming.insideGamingSeries.id) {
                    val playlistItems = YTUtil.getPlaylistItems(series.id)
                    playlistItems.foreach(playlistItem => {
                        val id: String = playlistItem.getContentDetails.getVideoId
                        //                print(id + " | ")
                        val contentOpt: Option[ChannelContent] = ChannelContent.get(id, series.id)
                        if (contentOpt.isDefined) {
                            //                    println("Exists")
                            val tags = contentOpt.get.getTags.map(_.tag)
                            YTUtil.getVideoTags(id)
                                .filter(t => watchedTags.contains(t.toLowerCase()))
                                .filterNot(t => tags.contains(t))
                                .map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                                .foreach(tagParams => ChannelContentTag.insert(tagParams: _*))
                        } else {
                            //                    println("New")
                            ChannelContent.insert(new ChannelContent(id, channelId, series.id))
                            YTUtil.getVideoTags(id)
                                .filter(t => watchedTags.contains(t.toLowerCase()))
                                .map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                                .foreach(tagParams => ChannelContentTag.insert(tagParams: _*))
                        }
                    })
                }

            })
            if (channelId == InsideGaming.funhausChannelId)
                getInsideGamingVids(watchedTags)
            Logger.info("Finished Updating Content For " + channelId)
        } catch {
            case e: Throwable =>
                Logger.error("Error updating content for " + channelId, e)
                throw e
        } finally {
            Channel.updateChannelCurrentlyUpdating(channelId, null)
        }
    }

    def getInsideGamingVids(watchedTags: List[String])(implicit db: Database): Unit = {
        YTUtil.getAllPlaylistsFromUser(InsideGaming.insideGamingChannelId).foreach(playlist => {
            YTUtil.getPlaylistItems(playlist.getId).foreach(playlistItem => {
                if (playlistItem.getSnippet.getPublishedAt.getValue < InsideGaming.insideGamingCutoffDateTime.getValue) {
                    val id = playlistItem.getContentDetails.getVideoId
                    val contentOpt: Option[ChannelContent] = ChannelContent.get(id, InsideGaming.insideGamingSeries.id)
                    if (contentOpt.isDefined) {
                        val tags = contentOpt.get.getTags.map(_.tag)
                        YTUtil.getVideoTags(id)
                            .filter(t => watchedTags.contains(t.toLowerCase()))
                            .filterNot(t => tags.contains(t))
                            .map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                            .foreach(tagParams => ChannelContentTag.insert(tagParams: _*))
                    } else {
                        ChannelContent.insert(new ChannelContent(id, InsideGaming.funhausChannelId, InsideGaming.insideGamingSeries.id))
                        YTUtil.getVideoTags(id)
                            .filter(t => watchedTags.contains(t.toLowerCase()))
                            .map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                            .foreach(tagParams => ChannelContentTag.insert(tagParams: _*))
                    }
                }
            })
        })
    }

}
