package models

import anorm.NamedParameter
import play.api.libs.json.JsValue

abstract class Model {

	val namedParameters: Seq[NamedParameter]

	def toJSON: JsValue

}
