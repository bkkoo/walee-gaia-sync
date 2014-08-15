package net.shantitree.walee.gaia.sync.puller.member


import java.sql.Timestamp

import akka.actor.{ActorLogging, Actor}
import com.typesafe.slick.driver.ms.SQLServerDriver.backend.Database
import com.typesafe.slick.driver.ms.SQLServerDriver.simple._
import net.shantitree.walee.gaia.model.Tables

object Initial {
  case class Pull(timestamp: Timestamp)
  case class PullFinish(result: List[String])
}

class Initial(database: Database) extends Actor with ActorLogging {

  import Initial._

  def receive = {

    case Pull(timestamp) =>

      val members = Tables.Tblmember
      val query = members.filter { _.dtcreate < timestamp } sortBy(_.cdmember) map { _.cdmember }

      val result = database withSession {
        implicit session => query.list
      }

      sender() ! PullFinish(List(result.head))

      //println("here")
  }

}
