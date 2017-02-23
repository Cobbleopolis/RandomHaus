package controllers

import com.google.inject.Inject
import models.ChannelContent
import play.api.db.Database
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.Action
import reference.JsonReference._

class DataContent @Inject()(implicit db: Database) extends RestfulDataController {

    override def get(contentId: String) = Action { request => {
        ChannelContent.get(contentId) match {
            case Some(channel) => Ok(Json.toJson(channel))
            case None => NotFound("Id not found")
        }
    }
    }

    override def getAll = Action {
        Ok(Json.toJson(ChannelContent.getAll))
    }

    override def insert: Action[JsValue] = Action(parse.json) { request => {
        request.body.validate[ChannelContent] match {
            case s: JsSuccess[ChannelContent] =>
                ChannelContent.insert(s.get)
                Ok("Insert successful")
            case e: JsError => BadRequest(JsError.toJson(e))
        }
    }
    }

    override def update(id: String): Action[JsValue] = Action(parse.json) { request => {
        NotImplemented
    }
    }

    override def delete(id: String): Action[JsValue] = Action(parse.json) { request => {
        NotImplemented
    }
    }
}