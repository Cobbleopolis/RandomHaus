package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc._
import util.DBUtil

class Application @Inject() (implicit db: Database) extends Controller {

    def index = Action {
        Ok(views.html.index("RandomHaus")(DBUtil.getChannelSeries("UCboMX_UNgaPBsUOIgasn3-Q")))
    }

}