package controllers

import com.google.inject.Inject
import models.ChannelContent
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import util.DBUtil
import reference.JsonReference._

class DataContent @Inject()(implicit db: Database) extends Controller {

	def allContent = Action {
		Ok(Json.toJson(DBUtil.getAllContent))
	}

	def content(contentId: String) = Action { request => {
		val content: Option[ChannelContent] = DBUtil.getContent(contentId)
		if (content.isDefined)
			Ok(Json.toJson(content.get))
		else
			NotFound("Id not found")
	}
	}

}