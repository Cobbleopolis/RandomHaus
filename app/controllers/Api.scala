package controllers

import com.google.inject.Inject
import models.{Channel, ChannelContent, ChannelSeries}
import play.api.db.Database
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, Controller, Cookie, DiscardingCookie}
import reference.JsonReference._

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Api @Inject()(implicit db: Database) extends Controller {

    val rand: Random = new Random(System.nanoTime())

    def getRandomVideo(channelId: String) = Action {
        val content = ChannelContent.getBy(classOf[Channel], 'channelId -> channelId)
        val c = content(rand.nextInt(content.length))
        Ok(if (c != null) Json.toJson(c) else Json.obj()).as("application/json")
    }

    def getRandomPlaylist(channelId: String) = Action {
        val series = ChannelSeries.getBy(classOf[Channel], 'channelId -> channelId)
        val s: ChannelSeries = series(rand.nextInt(series.length))
        Ok(if (s != null) Json.toJson(s) else Json.obj()).as("application/json")
    }

    def getPlaylistQueue(channelId: String) = Action(parse.json) { request => {
        val series: Array[String] = (request.body \ "series").as[JsArray].value.map(s => s.as[String]).toArray
        val filters: Array[String] = (request.body \ "filters").as[JsArray].value.map(f => f.as[String]).toArray
        val channelContent = if (series.isEmpty)
            if (filters.isEmpty)
                ChannelContent.getBy(classOf[Channel], 'channelId -> channelId)
            else
                ChannelContent.getWithTags(channelId, filters)
        else {
            if (filters.isEmpty)
                ChannelContent.getBy(classOf[Channel], 'channelId -> channelId)
            else
                ChannelContent.getWithTags(channelId, filters)
        }.filter(content => series.contains(content.seriesId))
        val indexes: ArrayBuffer[Int] = new ArrayBuffer[Int]()
        while (indexes.length < Math.min(channelContent.length, 50)) {
            val i = rand.nextInt(channelContent.length)
            if (!indexes.contains(i)) indexes += i
        }
        Ok(Json.toJson(indexes.map(i => Json.toJson(channelContent(i)))))
    }
    }

    def setChannelCookie(channelId: String) = Action {
        Redirect(routes.Application.index()).discardingCookies(DiscardingCookie("channel")).withCookies(Cookie("channel", channelId, httpOnly = false))
    }

}
