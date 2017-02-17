package models

import java.util.Date

import anorm._
import play.api.db.Database

case class ChannelSeries(id: String, channelId: String, name: String, publishedAt: Date) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'name -> name, 'publishedAt -> publishedAt)

    def getContent(implicit db: Database): List[ChannelContent] = ChannelContent.getBy(classOf[ChannelSeries], 'seriesId -> id)
}

object ChannelSeries extends ModelAccessor[ChannelSeries] {

    val getQuery = SQL("SELECT * FROM channelSeries WHERE id = {seriesId}")
    val getAllQuery = SQL("SELECT * FROM channelSeries")

    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("SELECT * FROM channelSeries WHERE channelId = {channelId} ORDER BY publishedAt ASC"),
        classOf[ChannelContent] -> SQL("SELECT channelSeries.* FROM channelSeries INNER JOIN content_series ON content_series.seriesId = channelSeries.id AND content_series.contentId = {contentId}")
    )

    val insertQuery = "INSERT IGNORE INTO channelSeries VALUES ({id}, {channelId}, {name}, {publishedAt})"

    val parser: RowParser[ChannelSeries] = Macro.namedParser[ChannelSeries].asInstanceOf[RowParser[ChannelSeries]]

    val idSymbol: Symbol = 'seriesId

}