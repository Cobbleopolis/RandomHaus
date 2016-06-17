package models

import java.util.Date

import anorm._

case class ChannelSeries(id: String, channelID: String, name: String, publishedAt: Date) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelID, 'name -> name, 'publishedAt -> publishedAt)
}

object ChannelSeries extends ModelAccessor[ChannelSeries] {

    val getQuery = SQL("CALL getSeries({seriesId});")
    val getAllQuery = SQL("SELECT * FROM channelSeries;")

    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("CALL getChannelSeries({channelId});")
    )

    val insertQuery = "CALL insertChannelSeries({id}, {channelId}, {name}, {publishedAt});"

    val parser: RowParser[ChannelSeries] = Macro.namedParser[ChannelSeries].asInstanceOf[RowParser[ChannelSeries]]

    val idSymbol: Symbol = 'seriesId

}