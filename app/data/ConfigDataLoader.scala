package data

import javax.inject.{Inject, Singleton}

import play.api.Configuration
import play.api.db.Database

@Singleton
class ConfigDataLoader @Inject()(config: Configuration, db: Database) {

}
