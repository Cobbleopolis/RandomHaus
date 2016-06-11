package models

import anorm.NamedParameter
import play.api.libs.json.{Format, JsObject, JsValue, Json}

abstract class Model {

	val namedParameters: Seq[NamedParameter]

}
