package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}

abstract class RestfulDataController @Inject()(implicit db: Database) extends Controller {

    def get(id: String): Action[_ <: Any]

    def getAll: Action[_ <: Any]

    def insert: Action[_ <: Any]

    def update(id: String): Action[_ <: Any]

    def delete(id: String): Action[_ <: Any]

}
