package models

import anorm.NamedParameter

case class ChannelContent(id: String, channelID: String, isPlaylist: Boolean) extends Model {
	val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelID -> channelID, 'isPlaylist -> isPlaylist)
}
