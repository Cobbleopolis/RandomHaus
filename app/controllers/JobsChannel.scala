package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}
import util.{ChannelUtil, JobUtil}

class JobsChannel @Inject()(implicit db: Database) extends Controller {

	def allPlaylistsAsSources(channelID: String) = Action {
		Ok("TODO")
	}

	def updateSourcesFromPlaylists(channelID: String) = Action {
		try {
			ChannelUtil.updateSeriesFromPlaylists(channelID)
			Ok(JobUtil.getSuccessful("Channel " + channelID + ": sources updated"))
		} catch {
			case e: Exception =>
				Ok(JobUtil.getFailure(e.toString))
				throw e
		}
	}

}
