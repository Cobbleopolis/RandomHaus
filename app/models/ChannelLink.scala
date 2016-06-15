package models

import anorm._
import play.api.db.Database
import reference.DBReferences

case class ChannelLink(id: Int, channelId: String, label: String, link: String) extends Model{

    val namedParameters: Seq[NamedParameter] = Seq('id -> id, 'channelId -> channelId, 'label -> label, 'link -> link)
    
}

object ChannelLink {

    private val getLinkQuery = SQL("CALL getLink({id});")
    private val getAllLinksQuery = SQL("SELECT * FROM channelLinks;")
    private val getChannelLinksQuery = SQL("CALL getChannelLinks({channelId});")

    private val insertChannelLinkQuery = SQL("CALL insertChannelLink({id}, {channelId}, {label}, {link});")

    def getLink(linkId: String)(implicit db: Database): Option[ChannelLink] = {
        db.withConnection(implicit conn => {
            getLinkQuery.on('id -> linkId).as(DBReferences.channelLinkParser.singleOpt)
        })
    }

    def getAllLinks(implicit db: Database): List[ChannelLink] = {
        db.withConnection(implicit conn => {
            getAllLinksQuery.as(DBReferences.channelLinkParser.*)
        })
    }

    def getChannelLinks(channelId: String)(implicit db: Database): List[ChannelLink] = {
        db.withConnection(implicit conn => {
            getChannelLinksQuery.on('channelId -> channelId).as(DBReferences.channelLinkParser.*)
        })
    }

    def getChannelLinks(channel: Channel)(implicit db: Database): List[ChannelLink] = getChannelLinks(channel.channelId)

    def insertChannelLink(channelLink: ChannelLink)(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            insertChannelLinkQuery.on(channelLink.namedParameters: _*).executeInsert()
        })

    }

    def insertChannelLinkBatch(channelLink: Seq[ChannelLink])(implicit db: Database): Unit = {
        db.withConnection(implicit conn => {
            BatchSql(insertChannelLinkQuery.toString, channelLink.head.namedParameters, channelLink.tail.map(_.namedParameters): _*)
        })

    }
}