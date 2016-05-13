package models

import anorm.NamedParameter
import play.api.libs.json.{JsObject, JsValue}

abstract class Model {

	val namedParameters: Seq[NamedParameter]

	def toJSON: JsValue

}
