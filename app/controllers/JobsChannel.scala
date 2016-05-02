package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}
import util.{ChannelUtil, ContentUtil, JobUtil, YTUtil}

class JobsChannel @Inject() (implicit db: Database) extends Controller {

    def allPlaylistsAsSources(channelID: String) = Action {
        Ok("TODO")
    }

	def updateSourcesFromPlaylists(channelID: String) = Action {
		try {
			ChannelUtil.updateSourcesFromPlaylists(channelID)
			Ok(JobUtil.getSuccessful("Channel " + channelID + ": sources updated"))
		} catch {
			case e: Exception => Ok(JobUtil.getFailure(e.toString))
		}
	}
    
}
