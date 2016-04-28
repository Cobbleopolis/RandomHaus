package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.Controller

class Data @Inject() (implicit db: Database) extends Controller {
    
}
