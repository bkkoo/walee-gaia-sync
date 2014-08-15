package net.shantitree.walee.gaia.sync

import akka.actor.{ActorRef, ActorLogging, Actor}
import com.google.inject.{BindingAnnotation, Inject}
import net.shantitree.walee.akkaguice.NamedActor

import scala.annotation.StaticAnnotation

object SyncMaster extends NamedActor {

  val name = "Sync"

  case class Initial()
  case class PullFinish()
  case class PushFinish()

}

class SyncMaster @Inject()(
  @PullMasterRef pullMaster: ActorRef,
  @PushMasterRef pushMaster: ActorRef
) extends Actor with ActorLogging {

  import SyncMaster._

  def receive = {

    case m@Initial() =>

      context.become(initial)
      //Send initial state message to both PullMaster and PushMaster
      pullMaster ! m
      pushMaster ! m

  }

  def initial: Receive = {

    case PullFinish() => println("pull finish")

    case PushFinish() => println("push finish")

  }

}
