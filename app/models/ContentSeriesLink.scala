package models

import anorm.{Macro, NamedParameter, RowParser, SQL, SqlQuery}
import play.api.db.Database

case class ContentSeriesLink(contentId: String, seriesId: String) extends Model {

    val namedParameters: Seq[NamedParameter] = Seq('contentId -> contentId, 'seriesId -> seriesId)

}

object ContentSeriesLink extends ModelAccessor[ContentSeriesLink] {

    val getQuery = SQL("SELECT * FROM content_series WHERE contentId = {contentId} AND seriesId = {seriesId}")
    val getAllQuery = SQL("SELECT * FROM content_series")
    val getByQueryList: Map[Class[_ <: Model], SqlQuery] = Map(
        classOf[ChannelContent] -> SQL("SELECT * FROM content_series WHERE contentId = {contentId}"),
        classOf[ChannelSeries] -> SQL("SELECT * FROM content_series WHERE seriesId = {seriesId}")
    )

    val insertQuery = "INSERT IGNORE INTO content_series VALUES ({contentId}, {seriesId})"

    val parser: RowParser[ContentSeriesLink] = Macro.namedParser[ContentSeriesLink].asInstanceOf[RowParser[ContentSeriesLink]]

    val idSymbol: Symbol = null

    def get(contentId: String, seriesId: String)(implicit db: Database): Option[ContentSeriesLink] = {
        db.withConnection(implicit conn => {
            getQuery.on('contentId -> contentId, 'seriesId -> seriesId).as(parser.singleOpt)
        })
    }

}
