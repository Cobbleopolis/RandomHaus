package modules

import com.google.inject.AbstractModule
import jobs.{JobActor, JobScheduler}
import play.api.libs.concurrent.AkkaGuiceSupport

class JobModule extends AbstractModule with AkkaGuiceSupport {
    def configure() = {
        bindActor[JobActor]("job-actor")
        bind(classOf[JobScheduler]).asEagerSingleton()
    }
}