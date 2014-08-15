package net.shantitree.walee.gaia.sync.puller

import akka.actor.{Actor, ActorLogging}
import com.google.inject.Inject
import com.google.inject.name.Named
import net.shantitree.walee.akkaguice.{NamedActor, GuiceAkkaExtension=>GAE}

object Puller extends NamedActor {

  import java.sql.Timestamp

  override def name = "SupervisorPuller"

  case class InitialCommit(createdBefore: Timestamp)
  case class Pull(createBefore: Timestamp)
  case class FinishPulling()

}

class Puller @Inject() (@Named("pullerActorNames") pullerActorNames: List[String]) extends Actor with ActorLogging{

  import Puller._

  override def preStart = {

    // Create children pulling system.
    pullerActorNames.map { name =>
      context.actorOf(GAE(context.system).props(name), name)
    }
  }

  def receive = {

    case m@InitialCommit(_) =>
      context.children foreach { _ ! m}

  }

}
