package controllers

import com.google.inject.Inject
import models.ChannelSeries
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import util.DBUtil

class DataSeries @Inject()(implicit db: Database) extends Controller {

	def allSources = Action {
		Ok(Json.toJson(DBUtil.getAllSeries.map(_.toJSON)))
	}

	def sources(sourceId: String) = Action { request => {
		val source: Option[ChannelSeries] = DBUtil.getSeries(sourceId)
		if (source.isDefined)
			Ok(source.get.toJSON)
		else
			NotFound("Id not found")
	}
	}

}