package controllers

import com.google.inject.Inject
import play.api.db.Database
import play.api.mvc.{Action, Controller}
import util.{ChannelUtil, JobUtil}

class JobsChannel @Inject()(implicit db: Database) extends Controller {

    def updateChannel(channelId: String) = Action {
        try {
            ChannelUtil.updateChannel(channelId)
            Ok(JobUtil.getSuccessful(s"Channel $channelId: updated"))
        } catch {
            case t: Throwable =>
                InternalServerError(JobUtil.getFailure(t.toString))
                throw t
        }
    }

    def updateSeries(channelId: String) = Action {
        try {
            ChannelUtil.updateSeries(channelId)
            Ok(JobUtil.getSuccessful(s"Channel $channelId: sources updated"))
        } catch {
            case t: Throwable =>
                InternalServerError(JobUtil.getFailure(t.toString))
                throw t
        }
    }

    def updateContent(channelId: String) = Action {
        try {
            ChannelUtil.updateContent(channelId)
            Ok(JobUtil.getSuccessful(s"Channel $channelId: content updated"))
        } catch {
            case t: Throwable =>
                InternalServerError(JobUtil.getFailure(t.toString))
                throw t
        }
    }

}
