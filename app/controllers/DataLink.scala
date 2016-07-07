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
        try {
            ChannelLink.insert('channelId -> (request.body \ "channelId").as[String],
                'label -> (request.body \ "label").as[String],
                'link -> (request.body \ "link").as[String])
            Ok("Insert successful")
        } catch {
            case t: Throwable => BadRequest(t.toString)
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
