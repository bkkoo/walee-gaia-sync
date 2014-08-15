package net.shantitree.walee.gaia.sync

import akka.actor.{Actor, ActorLogging}
import com.google.inject.BindingAnnotation
import net.shantitree.walee.akkaguice.NamedActor

import scala.annotation.StaticAnnotation

@BindingAnnotation
class PushMasterRef extends StaticAnnotation

object PushMaster extends NamedActor {
  val name = "PushMaster"
}
class PushMaster extends Actor with ActorLogging {

  import SyncMaster._

  def receive = {
    case m@Initial() =>
  }

}
