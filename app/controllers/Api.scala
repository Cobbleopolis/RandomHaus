package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import util.DBUtil

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Api @Inject()(implicit db: Database) extends Controller {

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

	def getPlaylistQueue(channelId: String, filtersRaw: String) = Action { request => {
		val filters: Array[String] = filtersRaw.split(",")
		val channelContent = if (filters.length == 1 && filters(0) == "all")
			DBUtil.getChannelContent(channelId)
		else
			DBUtil.getChannelContent(channelId).filter(content => filters.contains(content.seriesId))
		val indexes: ArrayBuffer[Int] = new ArrayBuffer[Int]()
		while (indexes.length < Math.min(channelContent.length, 50)) {
			val i = rand.nextInt(channelContent.length)
			if (!indexes.contains(i)) indexes += i
		}
		Ok(Json.obj("queue" -> indexes.map(i => channelContent(i).toJSON)))
	}
	}

}
