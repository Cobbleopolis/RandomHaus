package models

import anorm.NamedParameter

case class ChannelContentTag(id: Int, contentId: String, category: String, value: String) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'contentId -> contentId, 'category -> category, 'value -> value)
}
