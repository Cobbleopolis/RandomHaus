package models

import anorm.NamedParameter

abstract class Model {

	val namedParameters: Seq[NamedParameter]

}
