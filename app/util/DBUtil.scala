package util

import anorm.BatchSql
import models.{Channel, ChannelContent, ChannelSeries}
import play.api.db.Database
import reference.DBReferences

object DBUtil {

	def getChannel(channelID: String)(implicit db: Database): Option[Channel] = {
		db.withConnection(implicit conn => {
			DBReferences.getChannel.on('channelID -> channelID).as(DBReferences.channelParser.singleOpt)
		})

	}

	def getAllChannels(implicit db: Database): List[Channel] = {
		db.withConnection(implicit conn => {
			DBReferences.getAllChannels.as(DBReferences.channelParser.*)
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

	def getSeries(SeriesID: String)(implicit db: Database): Option[ChannelSeries] = {
		db.withConnection(implicit conn => {
			DBReferences.getSeries.on('seriesID -> SeriesID).as(DBReferences.channelSeriesParser.singleOpt)
		})
	}

	def getAllSeries(implicit db: Database): List[ChannelSeries] = {
		db.withConnection(implicit conn => {
			DBReferences.getAllSeries.as(DBReferences.channelSeriesParser.*)
		})
	}

	def getChannelSeries(channelId: String)(implicit db: Database): List[ChannelSeries] = {
		db.withConnection(implicit conn => {
			DBReferences.getChannelSeries.on('channelId -> channelId).as(DBReferences.channelSeriesParser.*)
		})

	}

	def getChannelContent(channelId: String)(implicit db: Database): List[ChannelContent] = {
		db.withConnection(implicit conn => {
			DBReferences.getChannelContent.on('channelId -> channelId).as(DBReferences.channelContentParser.*)
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

	def insertChannelSeries(channelSeries: ChannelSeries)(implicit db: Database): Unit = {
		db.withConnection(implicit conn => {
			DBReferences.insertChannelSeries.on(channelSeries.namedParameters: _*).executeInsert()
		})

	}

	def insertChannelSeriesBatch(channelSeries: Seq[ChannelSeries])(implicit db: Database): Unit = {
		db.withConnection(implicit conn => {
			BatchSql(DBReferences.insertChannelSeries.toString, channelSeries.head.namedParameters, channelSeries.tail.map(_.namedParameters): _*)
		})

	}
}
