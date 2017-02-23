package jobs

import javax.inject.{Inject, Named}

import akka.actor.{ActorRef, ActorSystem, Cancellable}
import com.typesafe.config.ConfigFactory
import play.api.db.Database

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class JobScheduler @Inject()(val system: ActorSystem, @Named("job-actor") val jobActor: ActorRef)(implicit ec: ExecutionContext, db: Database) {

    val refreshInterval: FiniteDuration = ConfigFactory.load().getInt("rh.contentUpdateInterval").minutes

    val updateOnStart: Boolean = ConfigFactory.load().getBoolean("rh.updateOnStart")

    val sourceScheduler: Cancellable = system.scheduler.schedule(if (updateOnStart) 0.milliseconds else refreshInterval, refreshInterval, jobActor, "updateEverything")

}
