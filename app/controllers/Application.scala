package controllers

import com.google.inject.Inject
import models.Channel
import play.api.db.{DB, Database}
import play.api.mvc._
import util.DBUtil

class Application @Inject()(implicit db: Database) extends Controller {

	def index = Action { res => {
        val cookie = res.cookies.get("channel")
		implicit val channels: List[Channel] = DBUtil.getAllChannels
        Ok(views.html.index("RandomHaus")(

            DBUtil.getChannelSeries(
	            if (cookie.isDefined)
		            cookie.get.value
	            else
		            "UCboMX_UNgaPBsUOIgasn3-Q"
            )
        ))
    }
	}

}