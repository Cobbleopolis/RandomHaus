package models

import anorm._

case class ChannelLink(id: Int, channelId: String, label: String, link: String) extends Model {

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'label -> label, 'link -> link)

}

object ChannelLink extends ModelAccessor[ChannelLink] {

    val getQuery = SQL("SELECT * FROM channelLinks WHERE linkId = {id}")
    val getAllQuery = SQL("SELECT * FROM channelLinks")
    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("SELECT * FROM channelLinks WHERE channelId = {channelId} ORDER BY label ASC")
    )

    val insertQuery = "INSERT IGNORE INTO channelLinks (channelId, label, link) VALUES ({channelId}, {label}, {link})"

    val parser: RowParser[ChannelLink] = Macro.namedParser[ChannelLink].asInstanceOf[RowParser[ChannelLink]]

    val idSymbol: Symbol = 'id
}