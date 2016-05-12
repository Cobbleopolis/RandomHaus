import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.api.libs.ws._
import play.mvc.Http.Status._

class ApplicationIntegrationSpec extends PlaySpec with OneServerPerSuite {

	implicit val ws = app.injector.instanceOf[WSClient]

	"Application" should {
		"be reachable" in {
			Util.getResponse(s"http://localhost:$port") { res =>
				res.status mustBe OK
			}
		}

		"return 404 on bad request" in {
			Util.getResponse(s"http://localhost:$port/badResponse") { res =>
				res.status mustBe NOT_FOUND
			}
		}
	}

}