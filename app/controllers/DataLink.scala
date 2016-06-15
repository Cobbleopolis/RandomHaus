package controllers

import com.google.inject.Inject
import models.ChannelLink
import play.api.db.Database
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.Action
import reference.JsonReference._

class DataLink @Inject()(implicit db: Database) extends RestfulDataController {

    override def get(linkId: String) = Action { request => {
        ChannelLink.get(linkId) match {
            case Some(channel) => Ok(Json.toJson(channel))
            case None => NotFound("Id not found")
        }
    }
    }

    override def getAll = Action {
        Ok(Json.toJson(ChannelLink.getAll))
    }

    override def insert = Action(parse.json) { request => {
        request.body.validate[ChannelLink] match {
            case s: JsSuccess[ChannelLink] =>
                ChannelLink.insert(s.get)
                Ok("Insert successful")
            case e: JsError => BadRequest(JsError.toJson(e))
        }
    }
    }

    override def update(channelId: String) = Action(parse.json) { request => {
        NotImplemented
    }
    }

    override def delete(channelId: String) = Action(parse.json) { request => {
        NotImplemented
    }
    }

}
