package models

import java.util.Date

import anorm._

case class ChannelSeries(id: String, channelID: String, name: String, publishedAt: Date) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelID, 'name -> name, 'publishedAt -> publishedAt)
}

object ChannelSeries extends ModelAccessor[ChannelSeries] {

    val getQuery = SQL("SELECT * FROM channelSeries WHERE id = {seriesId}")
    val getAllQuery = SQL("SELECT * FROM channelSeries")

    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("SELECT * FROM channelSeries WHERE channelId = {channelId} ORDER BY publishedAt ASC")
    )

    val insertQuery = "INSERT IGNORE INTO channelSeries VALUES ({id}, {channelId}, {name}, {publishedAt})"

    val parser: RowParser[ChannelSeries] = Macro.namedParser[ChannelSeries].asInstanceOf[RowParser[ChannelSeries]]

    val idSymbol: Symbol = 'seriesId

}