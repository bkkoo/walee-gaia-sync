package net.shantitree.walee.gaia.sync.puller.member


import java.sql.Timestamp

import akka.actor.{ActorLogging, Actor}
import com.google.inject.name.Named
import com.google.inject.Inject
import com.typesafe.slick.driver.ms.SQLServerDriver.backend.Database
import com.typesafe.slick.driver.ms.SQLServerDriver.simple._
import net.shantitree.walee.akkaguice.NamedActor
import net.shantitree.walee.gaia.model.Tables
import net.shantitree.walee.gaia.sync.puller.Puller.{Pull, FinishPulling}

object Initial extends NamedActor {

  override def name = "InitialMemberPuller"
}

class Initial @Inject()(@Named("gaia") database: Database ) extends Actor with ActorLogging {

  import Initial._

  def name = "initialMemberPuller"

  def receive = {

    case Pull(timestamp) =>

      val members = Tables.Tblmember
      val query = members.filter { _.dtcreate < timestamp } sortBy(_.cdmember) map { _.cdmember }

      val result = database withSession {
        implicit session => query.list
      }

      println(result)
      sender() ! FinishPulling()
  }

}
