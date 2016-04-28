package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}

class JobsChannel @Inject() (implicit db: Database) extends Controller {

    def allPlaylistsAsSources(channelID: String) = Action {
        Ok("TODO")
    }
    
}
