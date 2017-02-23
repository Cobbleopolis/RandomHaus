package controllers

import com.google.inject.Inject
import models.Channel
import play.api.db.Database
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.Action
import reference.JsonReference._

class DataChannel @Inject()(implicit db: Database) extends RestfulDataController {

    override def get(channelId: String) = Action { request => {
        Channel.get(channelId) match {
            case Some(channel) => Ok(Json.toJson(channel))
            case None => NotFound("Id not found")
        }
    }
    }

    override def getAll = Action {
        Ok(Json.toJson(Channel.getAll))
    }

    override def insert: Action[JsValue] = Action(parse.json) { request => {
        request.body.validate[Channel] match {
            case s: JsSuccess[Channel] =>
                Channel.insert(s.get)
                Ok("Insert successful")
            case e: JsError => BadRequest(JsError.toJson(e))
        }
    }
    }

    override def update(channelId: String): Action[JsValue] = Action(parse.json) { request => {
        NotImplemented
    }
    }

    override def delete(channelId: String): Action[JsValue] = Action(parse.json) { request => {
        NotImplemented
    }
    }

}
