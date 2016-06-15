package models

import java.util.Date

import anorm._
import play.api.db.Database
import reference.DBReferences

case class ChannelSeries(id: String, channelID: String, name: String, publishedAt: Date) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelID, 'name -> name, 'publishedAt -> publishedAt)
}

object ChannelSeries {

    private val getSeriesQuery = SQL("CALL getSeries({seriesId});")
    private val getAllSeriesQuery = SQL("SELECT * FROM channelSeries;")
    private val getChannelSeriesQuery = SQL("CALL getChannelSeries({channelId});")

    private val insertChannelSeriesQuery = SQL("CALL insertChannelSeries({id}, {channelId}, {name}, {publishedAt});")

    def getSeries(seriesId: String)(implicit db: Database): Option[ChannelSeries] = {
        db.withConnection(implicit conn => {
            getSeriesQuery.on('seriesId -> seriesId).as(DBReferences.channelSeriesParser.singleOpt)
        })
    }

    def getAllSeries(implicit db: Database): List[ChannelSeries] = {
        db.withConnection(implicit conn => {
            getAllSeriesQuery.as(DBReferences.channelSeriesParser.*)
        })
    }

    def getChannelSeries(channelId: String)(implicit db: Database): List[ChannelSeries] = {
        db.withConnection(implicit conn => {
            getChannelSeriesQuery.on('channelId -> channelId).as(DBReferences.channelSeriesParser.*)
        })

    }

    def getChannelSeries(channel: Channel)(implicit db: Database): List[ChannelSeries] = getChannelSeries(channel.channelId)

    def insertChannelSeries(channelSeries: ChannelSeries)(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            insertChannelSeriesQuery.on(channelSeries.namedParameters: _*).executeInsert()
        })

    }

    def insertChannelSeriesBatch(channelSeries: Seq[ChannelSeries])(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            BatchSql(insertChannelSeriesQuery.toString, channelSeries.head.namedParameters, channelSeries.tail.map(_.namedParameters): _*)
        })

    }
}