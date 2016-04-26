package util

import anorm.BatchSql
import models.{Channel, ChannelContent, ChannelSource}
import play.api.db.Database
import reference.DBReferences

object DBUtil {

    def getChannel(channelID: String)(implicit db: Database): Option[Channel] = {
        db.withConnection(implicit conn => {
            DBReferences.getChannel.on('channelID -> channelID).as(DBReferences.getChannelParser.singleOpt)
        })

    }

    def getAllChannels(implicit db: Database): List[Channel] = {
        db.withConnection(implicit conn => {
            DBReferences.getAllChannels.as(DBReferences.getChannelParser.*)
        })

    }

    def getContent(contentID: String)(implicit db: Database): Option[ChannelContent] = {
        db.withConnection(implicit conn => {
            DBReferences.getContent.on('contentID -> contentID).as(DBReferences.channelContentParser.singleOpt)
        })
    }

    def getAllContent(implicit db: Database): List[ChannelContent] = {
        db.withConnection(implicit conn => {
            DBReferences.getAllContent.as(DBReferences.channelContentParser.*)
        })
    }

    def getSource(sourceID: String)(implicit db: Database): Option[ChannelSource] = {
        db.withConnection(implicit conn => {
            DBReferences.getSource.on('sourceID -> sourceID).as(DBReferences.channelSourceParser.singleOpt)
        })
    }

    def getAllSources(implicit db: Database): List[ChannelSource] = {
        db.withConnection(implicit conn => {
            DBReferences.getAllSources.as(DBReferences.channelSourceParser.*)
        })
    }

    def getChannelSources(channelID: String)(implicit db: Database): List[ChannelSource] = {
        db.withConnection(implicit conn => {
            DBReferences.getChannelSources.on('channelID -> channelID).as(DBReferences.channelSourceParser.*)
        })

    }

    def insertChannelContent(channelContent: ChannelContent)(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            DBReferences.insertChannelContent.on(channelContent.namedParameters: _*).executeInsert()
        })

    }

    def insertChannelContentBatch(channelContent: Seq[ChannelContent])(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            BatchSql(DBReferences.insertChannelContent.toString, channelContent.head.namedParameters, channelContent.tail.map(_.namedParameters): _*)
        })

    }

}
