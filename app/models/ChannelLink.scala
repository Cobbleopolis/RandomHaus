package models

import anorm.NamedParameter

case class ChannelLink(id: Int, channelId: String, label: String, link: String) extends Model{

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'label -> label, 'link -> link)
    
}
