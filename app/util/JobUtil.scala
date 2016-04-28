package util

import play.api.libs.json.{JsValue, Json}

object JobUtil {

    val JOB_SUCCESSFUL = "JobUtil Successful"

    val JOB_FAIL = "JobUtil Failed"

    def getSuccessful(details: String): JsValue = Json.obj(
        "message" -> JOB_SUCCESSFUL,
        "details" -> details
    )

    def getFailure(details: String): JsValue = Json.obj(
        "message" -> JOB_FAIL,
        "details" -> details
    )
    
}
