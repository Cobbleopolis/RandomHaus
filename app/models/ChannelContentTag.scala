package models

import anorm._
import play.api.db.Database

case class ChannelContentTag(contentId: String, tag: String) extends Model {
    val namedParameters: Seq[NamedParameter] = Seq('contentId -> contentId, 'tag -> tag)
}

object ChannelContentTag extends ModelAccessor[ChannelContentTag] {
    val getQuery = SQL("SELECT * FROM contentTags WHERE contentId = {contentId} AND tag = {tag}")
    val getAllQuery = SQL("SELECT * FROM contentTag")
    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[ChannelContent] -> SQL("SELECT * FROM contentTags WHERE contentId = {contentId}")
    )

    val insertQuery = "INSERT INTO contentTags (contentId, tag) SELECT * FROM (SELECT {contentId}, {tag}) AS tmp WHERE NOT EXISTS (SELECT * FROM contentTags WHERE contentId = {contentId} AND tag = {tag}) LIMIT 1"

    val parser: RowParser[ChannelContentTag] = Macro.namedParser[ChannelContentTag].asInstanceOf[RowParser[ChannelContentTag]]

    val idSymbol: Symbol = null

    def get(contentId: String, tag: String)(implicit db: Database): Option[ChannelContentTag] = {
        db.withConnection(implicit conn => {
            getQuery.on('contentId -> contentId, 'tag -> tag).as(parser.singleOpt)
        })
    }

}
