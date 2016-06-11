package controllers

import com.google.inject.Inject
import models.{Channel, ChannelLink}
import play.api.db.Database
import play.api.mvc._
import util.DBUtil

class Application @Inject()(implicit db: Database) extends Controller {

    def index = Action { res => {
        val cookie = res.cookies.get("channel")
        val channelId = if (cookie.isDefined)
            cookie.get.value
        else
            "UCboMX_UNgaPBsUOIgasn3-Q"
        implicit val channels: List[Channel] = DBUtil.getAllChannels
        implicit val currentChannel: Channel = DBUtil.getChannel(channelId).get
        implicit val links: List[ChannelLink] = DBUtil.getChannelLinks(channelId)
        Ok(views.html.index("RandomHaus")(DBUtil.getChannelSeries(channelId)))
    }
    }

}