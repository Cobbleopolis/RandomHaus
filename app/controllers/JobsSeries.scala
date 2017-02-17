package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}
import util.{JobUtil, SeriesUtil}

class JobsSeries @Inject()(implicit db: Database) extends Controller {

    def updateLinks(seriesId: String) = Action {
        try {
            SeriesUtil.updateLinks(seriesId)
            Ok(JobUtil.getSuccessful(s"Series links for $seriesId updated"))
        } catch {
            case t: Throwable =>
                InternalServerError(JobUtil.getFailure(t.toString))
                throw t
        }
    }

}
