package models

import anorm._

case class ChannelLink(id: Int, channelId: String, label: String, link: String) extends Model{

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'label -> label, 'link -> link)
    
}

object ChannelLink extends ModelAccessor[ChannelLink] {

    val getQuery = SQL("CALL getLink({id});")
    val getAllQuery = SQL("SELECT * FROM channelLinks;")
    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("CALL getChannelLinks({channelId});")
    )

    val insertQuery = "CALL insertChannelLink({id}, {channelId}, {label}, {link});"

    val parser: RowParser[ChannelLink] = Macro.namedParser[ChannelLink].asInstanceOf[RowParser[ChannelLink]]

    val idSymbol: Symbol = 'id
}