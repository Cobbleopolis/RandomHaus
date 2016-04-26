package models

import anorm.NamedParameter
import play.api.libs.json.{JsValue, Writes}

import scala.util.parsing.json.JSONObject

abstract class Model {

	val namedParameters: Seq[NamedParameter]

    def toJSON: JsValue

}
