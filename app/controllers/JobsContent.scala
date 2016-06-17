package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}
import util.{ContentUtil, JobUtil}

class JobsContent @Inject()(implicit db: Database) extends Controller {

	def updateAllChannelContent = Action {
		try {
			ContentUtil.updateAll
			Ok(JobUtil.getSuccessful("Content updated"))
		} catch {
			case e: Exception =>
				Ok(JobUtil.getFailure(e.toString))
				throw e
		}
	}

}
