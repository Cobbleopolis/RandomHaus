package modules

import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}
import services.{ServiceYouTubeData, YTData}

class YouTubeDataModule extends Module {
	override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = Seq(bind[YTData].to[ServiceYouTubeData])

}
