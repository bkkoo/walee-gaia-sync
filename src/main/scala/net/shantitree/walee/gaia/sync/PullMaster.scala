package net.shantitree.walee.gaia.sync

import akka.actor.{ActorLogging, Actor}
import com.google.inject.BindingAnnotation
import net.shantitree.walee.akkaguice.NamedActor

import scala.annotation.StaticAnnotation

@BindingAnnotation
class PullMasterRef extends StaticAnnotation with NamedActor { val name = "PullMaster" }

object PullMaster extends NamedActor {
  val name = "PullMaster"
}

class PullMaster extends Actor with ActorLogging {

  import SyncMaster._

  def receive = {
    case m@Initial() =>

  }

}
