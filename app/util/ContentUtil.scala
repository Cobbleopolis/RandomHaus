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
        Channel.updateChannelCurrentlyUpdating(channelId, currentlyUpdating = true)
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
                        val contentOpt: Option[ChannelContent] = ChannelContent.get(id)
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
                            ChannelContent.insert(new ChannelContent(id, channelId))
                            YTUtil.getVideoTags(id)
                                .filter(t => watchedTags.contains(t.toLowerCase()))
                                .map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                                .foreach(tagParams => ChannelContentTag.insert(tagParams: _*))
                        }
                    })
                }

            })
            if (channelId == InsideGaming.funhausChannelId)
                updateInsideGamingVids(watchedTags)
            Logger.info("Finished Updating Content For " + channelId)
        } catch {
            case e: Throwable =>
                Logger.error("Error updating content for " + channelId, e)
                throw e
        } finally {
            Channel.updateChannelCurrentlyUpdating(channelId, currentlyUpdating = false)
        }
    }

    def updateInsideGamingVids(watchedTags: List[String])(implicit db: Database): Unit = {
        YTUtil.getUserPlaylists(InsideGaming.insideGamingChannelId).foreach(playlist => {
            YTUtil.getPlaylistItems(playlist.getId).foreach(playlistItem => {
                if (playlistItem.getSnippet.getPublishedAt.getValue < InsideGaming.insideGamingCutoffDateTime.getValue) {
                    val id = playlistItem.getContentDetails.getVideoId
                    val contentOpt: Option[ChannelContent] = ChannelContent.get(id)
                    if (contentOpt.isDefined) {
                        val tags = contentOpt.get.getTags.map(_.tag)
                        YTUtil.getVideoTags(id)
                            .filter(t => watchedTags.exists(watchedTag => t.matches("(?i)" + watchedTag)))
                            .filterNot(t => tags.contains(t))
                            .map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                            .foreach(tagParams => ChannelContentTag.insert(tagParams: _*))
                    } else {
                        ChannelContent.insert(new ChannelContent(id, InsideGaming.funhausChannelId))
                        YTUtil.getVideoTags(id)
                            .filter(t => watchedTags.exists(watchedTag => t.matches("(?i)" + watchedTag)))
                            .map(tag => Seq[NamedParameter]('contentId -> id, 'tag -> tag))
                            .foreach(tagParams => ChannelContentTag.insert(tagParams: _*))
                    }
                }
            })
        })
    }

    def updateContentTags(channelContent: ChannelContent)(implicit db: Database): Unit = updateContentTags(channelContent.id, channelContent.channelId)

    def updateContentTags(contentId: String, channelId: String)(implicit db: Database): Unit = {
        Channel.updateChannelCurrentlyUpdating(channelId, currentlyUpdating = true)
        try {
            val watchedTags: List[String] = FilterGroup.getBy(classOf[Channel], 'channelId -> channelId).flatMap(filterGroup =>
                Filter.getBy(classOf[FilterGroup], 'filterGroupId -> filterGroup.id).map(_.tagName.toLowerCase))
            val contentOpt: Option[ChannelContent] = ChannelContent.get(contentId)
            if (contentOpt.isDefined) {
                val tags: List[String] = contentOpt.get.getTags.map(_.tag)
                YTUtil.getVideoTags(contentId)
                    .filter(t => watchedTags.exists(watchedTag => t.matches("(?i)" + watchedTag)))
                    .filterNot(t => tags.contains(t))
                    .map(t => Seq[NamedParameter]('contentId -> contentId, 'tag -> t))
                    .foreach(tagParams => {
                        //                        Logger.debug(s"Tag Content Id: $contentId \n Tag Value: ${tagParams.get(1).value}")
                        ChannelContentTag.insert(tagParams: _*)
                    })
            } else {
                YTUtil.getVideoTags(contentId)
                    .filter(t => watchedTags.exists(watchedTag => t.matches("(?i)" + watchedTag)))
                    .map(t => Seq[NamedParameter]('contentId -> contentId, 'tag -> t))
                    .foreach(tagParams => {
                        //                        Logger.debug(s"Tag Content Id: $contentId \n Tag Value: ${tagParams.get(1).value}")
                        ChannelContentTag.insert(tagParams: _*)
                    })
            }
        } catch {
            case e: Throwable =>
                Logger.error("Error updating content tags for " + channelId, e)
                throw e
        } finally {
            Channel.updateChannelCurrentlyUpdating(channelId, currentlyUpdating = false)
        }
    }

}
