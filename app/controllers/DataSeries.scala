package controllers

import com.google.inject.Inject
import models.ChannelSeries
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import reference.JsonReference._

class DataSeries @Inject()(implicit db: Database) extends Controller {

	def allSources = Action {
		Ok(Json.toJson(ChannelSeries.getAll))
	}

	def sources(sourceId: String) = Action { request => {
		val source: Option[ChannelSeries] = ChannelSeries.get(sourceId)
		if (source.isDefined)
			Ok(Json.toJson(source.get))
		else
			NotFound("Id not found")
	}
	}

}