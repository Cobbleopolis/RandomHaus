import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.api.libs.ws._
import play.mvc.Http.Status._

class ApplicationSpec extends PlaySpec with OneServerPerSuite {

	implicit val ws = app.injector.instanceOf[WSClient]

	val baseURL: String = s"http://localhost:$port/"

	"Application" should {
		"be reachable" in {
			Util.getResponse(baseURL) { res =>
				res.status mustBe OK
			}
		}

		"return 404 on bad request" in {
			Util.getResponse(baseURL + "badResponse") { res =>
				res.status mustBe NOT_FOUND
			}
		}
	}

}