package controllers

import com.google.inject.Inject
import models.{Channel, ChannelContent, ChannelSeries}
import play.api.db.Database
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.mvc.{Action, Controller, Cookie, DiscardingCookie}
import reference.JsonReference._
import reference.MatchMethod

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

    def getPlaylistQueue(channelId: String): Action[JsValue] = Action(parse.json) { request => {
        val series: Array[String] = (request.body \ "series").as[JsArray].value.map(s => s.as[String]).toArray
        val filters: Array[String] = (request.body \ "filters").as[JsArray].value.map(f => f.as[String]).toArray
        val filterOptions: JsValue = (request.body \ "options").get
        var contentPool: List[ChannelContent] = List()
        if (series.isEmpty)
            if (filters.isEmpty)
                contentPool ++= Channel.get(channelId).get.getContent
            else
                contentPool ++= ChannelContent.getWithTags(channelId, "%", filters, MatchMethod.withName((filterOptions \ "matchMethod").as[String]))
        else {
            if (filters.isEmpty)
                series.foreach(seriesId => contentPool ++= ChannelContent.getBy(classOf[ChannelSeries], 'seriesId -> seriesId))
            else
                series.foreach(seriesId => contentPool ++= ChannelContent.getWithTags(channelId, seriesId, filters, MatchMethod.withName((filterOptions \ "matchMethod").as[String])))
        }
        val indexes: ArrayBuffer[Int] = new ArrayBuffer[Int]()
        while (indexes.length < Math.min(contentPool.length, (filterOptions \ "videoCount").as[Int])) {
            val i = rand.nextInt(contentPool.length)
            if (!indexes.contains(i)) indexes += i
        }
        Ok(Json.toJson(indexes.map(i => Json.toJson(contentPool(i)))))
    }
    }

    def setChannelCookie(channelId: String) = Action {
        Redirect(routes.Application.index()).discardingCookies(DiscardingCookie("channel")).withCookies(Cookie("channel", channelId, httpOnly = false))
    }

}
