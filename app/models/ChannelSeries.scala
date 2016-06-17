package models

import java.util.Date

import anorm._

case class ChannelSeries(id: String, channelID: String, name: String, publishedAt: Date) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelID, 'name -> name, 'publishedAt -> publishedAt)
}

object ChannelSeries extends ModelAccessor[ChannelSeries] {

    override val getQuery = SQL("CALL getSeries({seriesId});")
    override val getAllQuery = SQL("SELECT * FROM channelSeries;")

    override val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("CALL getChannelSeries({channelId});")
    )

    override val insertQuery = "CALL insertChannelSeries({id}, {channelId}, {name}, {publishedAt});"

    override val parser: RowParser[ChannelSeries] = Macro.namedParser[ChannelSeries].asInstanceOf[RowParser[ChannelSeries]]

    override val idSymbol: Symbol = 'seriesId

}