package controllers

import com.google.inject.Inject
import play.api.db.{DB, Database}
import play.api.mvc._
import util.DBUtil

class Application @Inject()(implicit db: Database) extends Controller {

	def index = Action { res => {
        Ok(views.html.index("RandomHaus")(DBUtil.getAllChannels, DBUtil.getChannelSeries(res.cookies.get("channel").get.value)))
    }
	}

}