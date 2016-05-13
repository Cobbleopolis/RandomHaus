package controllers

import com.google.inject.Inject
import models.Channel
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import util.DBUtil

class DataChannel @Inject()(implicit db: Database) extends Controller {

	def allChannels = Action {
		Ok(Json.toJson(DBUtil.getAllChannels.map(_.toJSON)))
	}

	def channel(channelId: String) = Action { request => {
		val channel: Option[Channel] = DBUtil.getChannel(channelId)
		if (channel.isDefined)
			Ok(channel.get.toJSON)
		else
			NotFound("Id not found")
	}
	}

}
