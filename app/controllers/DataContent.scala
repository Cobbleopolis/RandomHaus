package controllers

import com.google.inject.Inject
import models.ChannelContent
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import util.DBUtil

class DataContent @Inject()(implicit db: Database) extends Controller {

    def allContent = Action {
        Ok(Json.toJson(DBUtil.getAllContent.map(_.toJSON)))
    }

    def content(contentID: String) = Action { request => {
        val content: Option[ChannelContent] = DBUtil.getContent(contentID)
        if (content.isDefined)
            Ok(content.get.toJSON)
        else
            BadRequest(Json.toJson(Map("status" -> "KO", "message" -> "ID does not exist")))
    }
    }

}