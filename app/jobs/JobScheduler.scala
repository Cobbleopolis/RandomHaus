package jobs

import javax.inject.{Inject, Named}

import akka.actor.{ActorRef, ActorSystem}
import play.api.db.Database

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._



class JobScheduler @Inject() (val system: ActorSystem, @Named("job-actor") val jobActor: ActorRef)(implicit ec: ExecutionContext, db: Database) {

    val sourceScheduler = system.scheduler.schedule(0.milliseconds, 2.hours, jobActor, "updateEverything")
    
}
