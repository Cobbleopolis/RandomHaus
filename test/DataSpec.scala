import models.{Channel, ChannelContent, ChannelSeries}
import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.api.libs.json._
import play.api.libs.ws._
import play.mvc.Http.Status._
import reference.JsonReference._

class DataSpec extends PlaySpec with OneServerPerSuite {

	implicit val ws = app.injector.instanceOf[WSClient]

	val baseURL: String = s"http://localhost:$port/data/"

	val channelId: String = "UCboMX_UNgaPBsUOIgasn3-Q"

	val seriesId: String = "PLbIc1971kgPAiqGL25MGyYtZsUCPvX7Fo"

	val contentId: String = "-44QrUnJk_U"

	val nonExistantId: String = "thisIdShouldNotExist"

	"Channels" must {
		"return all of the channels on /data/channels" in {
			Util.getResponse(baseURL + "channels") { res =>
				res.status mustBe OK
				val json: JsArray = Json.parse(res.body).as[JsArray]
				json.value must not be null
			}
		}

		"all the channels returned by /data/channels must be constructable" in {
			Util.getResponse(baseURL + "channels") { res =>
				res.status mustBe OK
                assert(res.json.validate[Seq[Channel]].isInstanceOf[JsSuccess[Seq[Channel]]])
			}
		}

		"return a JSON object on /data/channels/:channelId" in {
			Util.getResponse(baseURL + "channels/" + channelId) { res =>
				res.status mustBe OK
				val json: JsObject = Json.parse(res.body).as[JsObject]
				json must not be null
			}
		}

		"return a JSON Channel object on /data/channels/:channelId and must be constructable" in {
			Util.getResponse(baseURL + "channels/" + channelId) { res =>
				res.status mustBe OK
				assert(res.json.validate[Channel].isInstanceOf[JsSuccess[Channel]])
			}
		}

		"return a 404 if the a channel id does not exist on /data/channels/:channelId" in {
			Util.getResponse(baseURL + "channels/" + nonExistantId) { res =>
				res.status mustBe NOT_FOUND
			}
		}
	}

	"Series" must {
		"return all of the series on /data/series" in {
			Util.getResponse(baseURL + "series") { res =>
				res.status mustBe OK
				val json: JsArray = Json.parse(res.body).as[JsArray]
				json.value must not be null
			}
		}

		"all the content items returned by /data/series must be constructable" in {
			Util.getResponse(baseURL + "series") { res =>
				res.status mustBe OK
                assert(res.json.validate[Seq[ChannelSeries]].isInstanceOf[JsSuccess[Seq[ChannelSeries]]])
			}
		}

		"return a JSON object on /data/series/:seriesId" in {
			Util.getResponse(baseURL + "series/" + seriesId) { res =>
				res.status mustBe OK
				val json: JsObject = Json.parse(res.body).as[JsObject]
				json must not be null
			}
		}

		"return a JSON ChannelSeries object on /data/series/:seriesId and must be constructable" in {
			Util.getResponse(baseURL + "series/" + seriesId) { res =>
				res.status mustBe OK
                assert(res.json.validate[ChannelSeries].isInstanceOf[JsSuccess[ChannelSeries]])
			}
		}

		"return a 404 if the a channel id does not exist on /data/series/:seriesId" in {
			Util.getResponse(baseURL + "series/" + nonExistantId) { res =>
				res.status mustBe NOT_FOUND
			}
		}
	}

	"Content" must {
		"return all of the content on /data/content" in {
			Util.getResponse(baseURL + "content") { res =>
				res.status mustBe OK
				val json: JsArray = Json.parse(res.body).as[JsArray]
				json.value must not be null
			}
		}

		"all the content items returned by /data/content must be constructable" in {
			Util.getResponse(baseURL + "content") { res =>
				res.status mustBe OK
                assert(res.json.validate[Seq[ChannelContent]].isInstanceOf[JsSuccess[Seq[ChannelContent]]])
			}
		}

		"return a JSON object on /data/content/:contentId" in {
			Util.getResponse(baseURL + "content/" + contentId) { res =>
				res.status mustBe OK
				val json: JsObject = Json.parse(res.body).as[JsObject]
				json must not be null
			}
		}

		"return a JSON ChannelContent object on /data/content/:contentId and must be constructable" in {
			Util.getResponse(baseURL + "content/" + contentId) { res =>
				res.status mustBe OK
                assert(res.json.validate[ChannelContent].isInstanceOf[JsSuccess[ChannelContent]])
			}
		}

		"return a 404 if the a channel id does not exist on /data/content/:contentId" in {
			Util.getResponse(baseURL + "content/" + nonExistantId) { res =>
				res.status mustBe NOT_FOUND
			}
		}
	}

}
