import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Util {

	def getResponse(url: String)(callback: WSResponse => Unit)(implicit ws: WSClient): Unit = callback(Await.result(ws.url(url).get(), Duration.Inf))

}
