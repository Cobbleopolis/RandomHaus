package reference

import java.text.SimpleDateFormat
import java.util.Date

import com.google.api.client.util.DateTime
import models.ChannelSeries

object InsideGaming {

    val insideGamingCutoffDate: Date = new SimpleDateFormat("mm-dd-yyyy").parse("01-30-2015")

    val insideGamingCutoffDateTime: DateTime = new DateTime(insideGamingCutoffDate)

    val insideGamingChannelId: String = "UCaDFEed7JBxC2lnP5I2AktQ"

    val funhausChannelId: String = "UCboMX_UNgaPBsUOIgasn3-Q"

    val insideGamingSeries: ChannelSeries = new ChannelSeries("InsideGaming", funhausChannelId, "Inside Gaming", insideGamingCutoffDate)
    
}
