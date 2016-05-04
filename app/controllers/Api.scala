package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}
import util.DBUtil

import scala.util.Random

class Api @Inject() (implicit db: Database) extends Controller {

    val rand: Random = new Random(System.nanoTime())

    def getRandomVideo(channelID: String) = Action {
        val content = DBUtil.getAllContent
	    val c = content(rand.nextInt(content.length))
        Ok(if (c != null) c.id else "").as("text")
    }
    
}
