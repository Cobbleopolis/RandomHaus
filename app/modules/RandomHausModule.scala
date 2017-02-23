package modules

import com.google.inject.AbstractModule
import data.ConfigDataLoader
import jobs.{JobActor, JobScheduler}
import play.api.libs.concurrent.AkkaGuiceSupport

class RandomHausModule extends AbstractModule with AkkaGuiceSupport {
    def configure(): Unit = {
        bindActor[JobActor]("job-actor")
        bind(classOf[JobScheduler]).asEagerSingleton()
        bind(classOf[ConfigDataLoader]).asEagerSingleton()
    }
}