import play.api.libs.json.JsValue
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object Util {

	val timeout: Duration = 120 seconds

	def getResponse(url: String)(callback: WSResponse => Unit)(implicit ws: WSClient): Unit =
		callback(Await.result(ws.url(url).get(), timeout))

    def postResponse(url: String, data: JsValue)(callback: WSResponse => Unit)(implicit ws: WSClient): Unit =
        callback(Await.result(ws.url(url).post(data), timeout))

}
