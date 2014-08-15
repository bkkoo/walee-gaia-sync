package net.shantitree.walee.gaia.sync

/*
import java.sql.Timestamp
import java.util.Calendar
import java.util.Calendar._
*/

import akka.actor.ActorSystem
import com.google.inject.Guice


import net.shantitree.walee.akkaguice.{GuiceAkkaExtension=>GAE}
import net.shantitree.walee.gaia.sync.SyncMaster.Initial

/* Wrap the injector in a ScalaInjector for even more rich scala magic */
import net.codingwell.scalaguice.InjectorExtensions._

//import scala.concurrent.ExecutionContext.Implicits.global

object Main {

  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(
      new ConfigModule(),
      new AkkaModule(),
      new SyncModule()
    )

    val system = injector.instance[ActorSystem]
    val syncMaster = system.actorOf(GAE(system).props(SyncMaster.name))

    syncMaster ! Initial

  }


    /*
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
    val system = injector.instance[ActorSystem]
    val puller = system.actorOf(GAE(system).props(Puller.name))

    puller ! InitialCommit(timestamp)
    */

}
