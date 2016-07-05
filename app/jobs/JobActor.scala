package jobs

import javax.inject.{Inject, Singleton}

import akka.actor.Actor
import play.api.Logger
import play.api.db.Database
import util.{ChannelUtil, ContentUtil}

@Singleton
class JobActor @Inject() (implicit db: Database) extends Actor {
    def receive = {
        case "updateSources" => updateSources()
        case "updateContent" => updateContent()
        case "updateEverything" => updateEverything()
    }

    def updateSources(): Unit ={
        Logger.info("Updating Sources...")
        ChannelUtil.updateAllSeries()
        Logger.info("Finished Updating Sources")
    }

    def updateContent(): Unit ={
        Logger.info("Updating Content...")
        ContentUtil.updateAll()
        Logger.info("Finished Updating Content")
    }

    def updateEverything(): Unit = {
        Logger.info("Updating Everything...")
        updateSources()
        updateContent()
        Logger.info("Finished Updating Everything")
    }
}