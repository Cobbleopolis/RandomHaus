package controllers

import com.google.inject.Inject
import models.{ChannelContent, ChannelSource}
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import util.DBUtil

class DataSource @Inject()(implicit db: Database) extends Controller {

    def allSources = Action {
        Ok(Json.toJson(DBUtil.getAllSources.map(_.toJSON)))
    }

    def sources(sourceID: String) = Action{ request => {
        val source: Option[ChannelSource] = DBUtil.getSource(sourceID)
        if (source.isDefined)
            Ok(source.get.toJSON)
        else
            BadRequest(Json.toJson(Map("status" -> "KO", "message" -> "ID does not exist")))
    }
    }

}