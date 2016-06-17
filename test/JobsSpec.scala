import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.libs.ws.WSClient
import play.mvc.Http.Status._
import util.JobUtil

class JobsSpec  extends PlaySpec with OneServerPerSuite {

	implicit val ws = app.injector.instanceOf[WSClient]

	val baseURL: String = s"http://localhost:$port/jobs/"

	val channelID: String = "UCboMX_UNgaPBsUOIgasn3-Q"

//	"Content" must {
//
//		"return a successful job message on /jobs/content/updateAll" in {
//			Util.getResponse(baseURL + "content/updateAll") { res =>
//				res.status mustBe OK
//				val json: JsObject = Json.parse(res.body).asInstanceOf[JsObject]
//				assert((json \ "message").as[String] == JobUtil.JOB_SUCCESSFUL)
//				assert(!(json \ "details").as[String].isEmpty)
//			}
//		}
//
//	}
//
//	"Channel" must {
//
//		"return a successful job message on /jobs/channel/:channelID/updateAllSources" in {
//			Util.getResponse(baseURL + "channel/" + channelID + "/updateAllSources") { res =>
//				res.status mustBe OK
//				val json: JsObject = Json.parse(res.body).asInstanceOf[JsObject]
//				assert((json \ "message").as[String] == JobUtil.JOB_SUCCESSFUL)
//				assert(!(json \ "details").as[String].isEmpty)
//			}
//		}
//
//	}
}

