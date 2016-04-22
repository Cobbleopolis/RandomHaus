package util

import anorm.BatchSql
import com.google.inject.Inject
import models.{Channel, ChannelContent, ChannelSource}
import play.api.db.Database
import reference.DBReferences

object DBUtil {

	val util = new DBUtil.DBUtil()//TODO fix everything

	private class DBUtil @Inject() (db: Database = null) {
		def getChannel(channelID: String): Option[Channel] = {
			db.withConnection(conn => {
				DBReferences.getChannel.on('channelID -> channelID).as(DBReferences.getChannelParser.singleOpt)
			})

		}

		def getAllChannels: List[Channel] = {
			db.withConnection(conn => {
				DBReferences.getAllChannels.as(DBReferences.getChannelParser.*)
			})

		}

		def getChannelSources(channelID: String): List[ChannelSource] = {
			db.withConnection(conn => {
				DBReferences.getChannelSources.as(DBReferences.channelSourceParser.*)
			})

		}

		def insertChannelContent(channelContent: ChannelContent): Unit = {
			db.withConnection(conn => {
				DBReferences.insertChannelContent.on(channelContent.namedParameters: _*).executeInsert()
			})

		}

		def insertChannelContentBatch(channelContent: Seq[ChannelContent]): Unit = {
			db.withConnection(conn => {
				BatchSql(DBReferences.insertChannelContent.toString, channelContent.head.namedParameters, channelContent.tail.map(_.namedParameters): _*)
			})

		}
	}

}
