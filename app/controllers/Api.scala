package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}
import util.DBUtil

import scala.util.Random

class Api @Inject() (implicit db: Database) extends Controller {

    val rand: Random = new Random(System.nanoTime())

    def getRandomVideo = Action {
        val content = DBUtil.getAllContent.filter(content => !content.isPlaylist)
        Ok(content(rand.nextInt(content.length)).id).as("text")
    }
    
}
