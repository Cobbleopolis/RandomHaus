package models

import anorm._
import play.api.db.Database
import reference.MatchMethod
import reference.MatchMethod.MatchMethod

case class ChannelContent(id: String, channelId: String) extends Model {

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId)

    //    private var tags: ListBuffer[ChannelContentTag] = ListBuffer()
    //
    //    def addTag(tag: ChannelContentTag) = {
    //        tags += tag
    //    }
    //
    //    def getTags: List[ChannelContentTag] = tags.toList

    def getTags(implicit db: Database): List[ChannelContentTag] = ChannelContentTag.getBy(classOf[ChannelContent], 'contentId -> id)

    def getSeries(implicit db: Database): List[ChannelSeries] = ChannelSeries.getBy(classOf[ChannelContent], 'contentId -> id)
}

object ChannelContent extends ModelAccessor[ChannelContent] {

    val getQuery = SQL("SELECT * FROM channelContent WHERE id = {contentId}")
    val getAllQuery = SQL("SELECT * FROM channelContent")
    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[Channel] -> SQL("SELECT * FROM channelContent WHERE channelId = {channelId}"),
        classOf[ChannelSeries] -> SQL("SELECT channelContent.* FROM channelContent INNER JOIN content_series ON content_series.contentId = channelContent.id AND content_series.seriesId = {seriesId}")
    )

    val insertQuery = "INSERT IGNORE INTO channelContent VALUES ({id}, {channelId})"

    val parser: RowParser[ChannelContent] = Macro.namedParser[ChannelContent].asInstanceOf[RowParser[ChannelContent]]

    val idSymbol: Symbol = 'contentId

    val tagQuery: SqlQuery = SQL("SELECT channelContent.* FROM channelContent INNER JOIN contentTags ON channelContent.id = contentTags.contentId WHERE channelContent.channelId = {channelId} AND contentTags.tag IN ({tags}) GROUP BY contentId HAVING COUNT(DISTINCT tag) >= {tagCount}")

    def getWithTags(channelId: String, tags: Array[String], matchMethod: MatchMethod)(implicit db: Database): List[ChannelContent] = {
        val count = if(matchMethod == MatchMethod.MATCH_ALL) tags.length else 1
        db.withConnection(implicit conn => {
            tagQuery.on('channelId -> channelId, 'tags -> tags.toSeq, 'tagCount -> count).as(parser.*)
        })
    }

}
