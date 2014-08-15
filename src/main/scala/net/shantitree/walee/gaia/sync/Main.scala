package net.shantitree.walee.gaia.sync

import java.sql.Timestamp
import java.util.Calendar
import java.util.Calendar._

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import net.shantitree.walee.gaia.sync.puller.Puller
import net.shantitree.walee.gaia.sync.puller.Puller._

import scala.concurrent.duration._
//import scala.concurrent.ExecutionContext.Implicits.global

object Main {

  def main(args: Array[String]): Unit = {

    val calendar = Calendar.getInstance

    implicit val timeout = Timeout(5 second)


    calendar.set(YEAR, 2006)
    calendar.set(MONTH, OCTOBER)
    calendar.set(DAY_OF_MONTH, 1)
    calendar.set(HOUR_OF_DAY, 0)
    calendar.set(MINUTE, 0)
    calendar.set(SECOND, 0)
    calendar.set(MILLISECOND, 0)

    val timestamp = new Timestamp(calendar.getTimeInMillis)

    val system = ActorSystem("GaiaSync")
    val puller = system.actorOf(Props[Puller])

    puller ! InitialCommit(timestamp)

  }
}
