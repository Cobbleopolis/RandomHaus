import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.api.libs.ws._
import play.mvc.Http.Status._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ApplicationIntegrationSpec extends PlaySpec with OneServerPerSuite {

	val ws = app.injector.instanceOf[WSClient]

	"Application" should {
		"be reachable" in {
			val res = Await.result(ws.url(s"http://localhost:$port").get(), Duration.Inf)
			res.status mustBe OK
		}

		"return 404 on bad request" in {
			val res = Await.result(ws.url(s"http://localhost:$port/badRequest").get(), Duration.Inf)
			res.status mustBe NOT_FOUND
		}
	}

}