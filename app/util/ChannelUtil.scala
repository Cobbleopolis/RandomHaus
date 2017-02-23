package util

import java.util.Date

import com.google.api.services.youtube.model.PlaylistItem
import models.{Channel, ChannelContent, ChannelSeries, FilterGroup}
import play.api.Logger
import play.api.db.Database
import reference.InsideGaming

object ChannelUtil {

    def updateAll()(implicit db: Database): Unit = {
        Channel.getAll.foreach(channel => updateChannel(channel.channelId))
    }

    def updateAllSeries()(implicit db: Database): Unit = {
        Channel.getAll.foreach(channel => updateSeries(channel.channelId))
    }

    def updateAllContent()(implicit db: Database): Unit = {
        Channel.getAll.foreach(channel => updateContent(channel.channelId))
    }

    def updateChannel(channelId: String)(implicit db: Database): Unit = {
        val channel: Option[Channel] = Channel.get(channelId)
        if (channel.isDefined) {
            updateSeries(channelId)
            updateContent(channelId)
            if(FilterGroup.getBy(classOf[Channel], 'channelId -> channel.get.channelId).nonEmpty)
                channel.get.getContent.foreach(ContentUtil.updateContentTags)
            channel.get.getSeries.foreach(series => SeriesUtil.updateLinks(series.id))
        } else {
            throw new Exception(s"Channel id $channelId is not found...")
        }
    }

    def updateSeries(channelId: String)(implicit db: Database): Unit = {
        Channel.updateChannelCurrentlyUpdating(channelId, currentlyUpdating = true)
        try {
            Logger.info(s"Updating sources for $channelId...")
            YTUtil.getUserPlaylists(channelId).foreach(playlist =>
                ChannelSeries.insert(new ChannelSeries(
                    playlist.getId,
                    channelId,
                    playlist.getSnippet.getTitle,
                    new Date(playlist.getSnippet.getPublishedAt.getValue)
                ))
            )
            if (channelId == InsideGaming.funhausChannelId)
                ChannelSeries.insert(InsideGaming.insideGamingSeries)
            Logger.info("Finished updating sources for " + channelId)
        } catch {
            case e: Throwable =>
                Logger.error("Error updating series for " + channelId, e)
                throw e
        } finally {
            Channel.updateChannelCurrentlyUpdating(channelId, currentlyUpdating = false)
        }
    }

    def updateContent(channelId: String)(implicit db: Database): Unit = {
        Channel.updateChannelCurrentlyUpdating(channelId, currentlyUpdating = true)
        try {
            Logger.info(s"Updating content for $channelId...")
            var contentList: List[PlaylistItem] = YTUtil.getUserContent(channelId)
            if (channelId == InsideGaming.funhausChannelId)
                contentList ++= YTUtil.getInsideGamingVideos
            contentList.foreach(playlistItem =>
                ChannelContent.insert(new ChannelContent(playlistItem.getContentDetails.getVideoId, channelId)))
            Logger.info("Finished updating content for " + channelId)
        } catch {
            case e: Throwable =>
                Logger.error("Error updating content for " + channelId, e)
                throw e
        } finally {
            Channel.updateChannelCurrentlyUpdating(channelId, currentlyUpdating = false)
        }
    }



}
