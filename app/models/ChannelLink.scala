package models

import anorm._

case class ChannelLink(id: Int, channelId: String, label: String, link: String) extends Model{

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'label -> label, 'link -> link)
    
}

object ChannelLink extends ModelAccessor[ChannelLink] {

    override val getQuery = SQL("CALL getLink({id});")
    override val getAllQuery = SQL("SELECT * FROM channelLinks;")
    override val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("CALL getChannelLinks({channelId});")
    )

    override val insertQuery = "CALL insertChannelLink({id}, {channelId}, {label}, {link});"

    override val parser: RowParser[ChannelLink] = Macro.namedParser[ChannelLink].asInstanceOf[RowParser[ChannelLink]]

    override val idSymbol: Symbol = 'id
}