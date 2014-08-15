package net.shantitree.walee.gaia.sync.puller

import akka.actor.{Actor, ActorLogging}
import net.shantitree.walee.gaia.sync.puller.Puller.InitialCommit
import com.typesafe.slick.driver.ms.SQLServerDriver.backend.Database

class MemberPuller(databaseConnector: Database ) extends Actor with ActorLogging {
  def receive = {
    case InitialCommit(createBefore) =>
  }
}
