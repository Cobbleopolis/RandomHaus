package services

import models.{Channel, ChannelContent}
import util.{DBUtil, YTUtil}

import scala.concurrent.Future


trait YTData {
	val updateAllChannelData: Future[Unit]

	def updateAllChannelContent(): Unit
}
