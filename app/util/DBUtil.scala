package util

import anorm.BatchSql
import models.{Channel, ChannelContent, ChannelLink, ChannelSeries}
import play.api.db.Database
import reference.DBReferences

object DBUtil {

	def getChannel(channelID: String)(implicit db: Database): Option[Channel] = {
		db.withConnection(implicit conn => {
			DBReferences.getChannel.on('channelId -> channelID).as(DBReferences.channelParser.singleOpt)
		})

	}

	def getAllChannels(implicit db: Database): List[Channel] = {
		db.withConnection(implicit conn => {
			DBReferences.getAllChannels.as(DBReferences.channelParser.*)
		})

	}

	def getContent(contentID: String)(implicit db: Database): Option[ChannelContent] = {
		db.withConnection(implicit conn => {
			DBReferences.getContent.on('contentId -> contentID).as(DBReferences.channelContentParser.singleOpt)
		})
	}

	def getAllContent(implicit db: Database): List[ChannelContent] = {
		db.withConnection(implicit conn => {
			DBReferences.getAllContent.as(DBReferences.channelContentParser.*)
		})
	}

	def getSeries(seriesId: String)(implicit db: Database): Option[ChannelSeries] = {
		db.withConnection(implicit conn => {
			DBReferences.getSeries.on('seriesId -> seriesId).as(DBReferences.channelSeriesParser.singleOpt)
		})
	}

	def getAllSeries(implicit db: Database): List[ChannelSeries] = {
		db.withConnection(implicit conn => {
			DBReferences.getAllSeries.as(DBReferences.channelSeriesParser.*)
		})
	}

    def getLink(linkId: String)(implicit db: Database): Option[ChannelLink] = {
        db.withConnection(implicit conn => {
            DBReferences.getLink.on('id -> linkId).as(DBReferences.channelLinkParser.singleOpt)
        })
    }

    def getAllLinks(implicit db: Database): List[ChannelLink] = {
        db.withConnection(implicit conn => {
            DBReferences.getAllLinks.as(DBReferences.channelLinkParser.*)
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

    def getChannelLinks(channelId: String)(implicit db: Database): List[ChannelLink] = {
        db.withConnection(implicit conn => {
            DBReferences.getChannelLinks.on('channelId -> channelId).as(DBReferences.channelLinkParser.*)
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

    def insertChannelLink(channelLink: ChannelLink)(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            DBReferences.insertChannelLink.on(channelLink.namedParameters: _*).executeInsert()
        })

    }

    def insertChannelLinkBatch(channelLink: Seq[ChannelSeries])(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            BatchSql(DBReferences.insertChannelLink.toString, channelLink.head.namedParameters, channelLink.tail.map(_.namedParameters): _*)
        })

    }
}
