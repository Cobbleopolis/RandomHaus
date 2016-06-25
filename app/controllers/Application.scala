package controllers

import com.google.inject.Inject
import models.{Channel, ChannelLink, FilterGroup, Filter}
import play.api.db.Database
import play.api.mvc._

class Application @Inject()(implicit db: Database) extends Controller {

    def index = Action { res => {
        val cookie = res.cookies.get("channel")
        val channelId = if (cookie.isDefined)
            cookie.get.value
        else
            "UCboMX_UNgaPBsUOIgasn3-Q"
        implicit val channels: List[Channel] = Channel.getAll
        implicit val currentChannel: Channel = Channel.get(channelId).get
        implicit val links: List[ChannelLink] = currentChannel.getLinks
        val filterGroups: List[FilterGroup] = FilterGroup.getBy(classOf[Channel], 'channelId -> currentChannel.channelId)
        val filterMap: Map[String, List[Filter]] = Map[String, List[Filter]](
            filterGroups.map(filterGroup =>
                filterGroup.name -> Filter.getBy(classOf[FilterGroup], 'filterGroupId -> filterGroup.id)
            ): _*
        )
        Ok(views.html.index("RandomHaus")(currentChannel.getSeries, filterGroups, filterMap))
    }
    }

    def about = Action { res => {
        val cookie = res.cookies.get("channel")
        val channelId = if (cookie.isDefined)
            cookie.get.value
        else
            "UCboMX_UNgaPBsUOIgasn3-Q"
        implicit val channels: List[Channel] = Channel.getAll
        implicit val currentChannel: Channel = Channel.get(channelId).get
        implicit val links: List[ChannelLink] = currentChannel.getLinks
        Ok(views.html.about())
    }}

}