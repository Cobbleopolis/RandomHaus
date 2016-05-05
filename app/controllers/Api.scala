package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import util.DBUtil

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Api @Inject() (implicit db: Database) extends Controller {

    val rand: Random = new Random(System.nanoTime())

    def getRandomVideo(channelID: String) = Action {
        val content = DBUtil.getAllContent
	    val c = content(rand.nextInt(content.length))
        Ok(if (c != null) c.toJSON else Json.obj()).as("application/json")
    }

    def getRandomPlaylist(channelId: String) = Action {
        val series = DBUtil.getChannelSeries(channelId)
        val s = series(rand.nextInt(series.length))
        Ok(if (s != null) s.toJSON else Json.obj()).as("application/json")
    }

    def getPlaylistQueue(channelId: String) = Action {
        val channelContent = DBUtil.getChannelContent(channelId)
        val indexes: ArrayBuffer[Int] = new ArrayBuffer[Int]()
        while (indexes.length < 50) {
            val i = rand.nextInt(channelContent.length)
            if (!indexes.contains(i)) indexes += i
        }
        Ok(Json.obj("queue" -> indexes.map(i => channelContent(i).toJSON)))
    }
    
}
