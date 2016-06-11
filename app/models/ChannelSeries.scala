package models

import java.util.Date

import anorm.NamedParameter

case class ChannelSeries(id: String, channelID: String, name: String, publishedAt: Date) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelID, 'name -> name, 'publishedAt -> publishedAt)
}