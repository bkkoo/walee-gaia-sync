package net.shantitree.walee.gaia.sync.puller


import akka.actor.{Actor, ActorLogging}
import net.shantitree.walee.akkaguice.{GuiceAkkaExtension=>GAE, NamedActor}
import net.shantitree.walee.gaia.sync.puller.Puller.{FinishPulling, Pull, InitialCommit}

object MemberPuller extends NamedActor {
  override def name = "MemberPuller"
}

class MemberPuller extends Actor with ActorLogging {

  def receive = {
    case InitialCommit(createBefore) =>
      val initialPuller = context.actorOf(GAE(context.system).props(member.Initial.name))

      initialPuller ! Pull(createBefore)

    case FinishPulling() =>
      println("Finish Pulling ....")
      context.system.shutdown()
      context.system.awaitTermination()

  }
}
