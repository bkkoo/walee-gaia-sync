package net.shantitree.walee.gaia.sync

import akka.actor.ActorSystem
import com.google.inject.{AbstractModule, Injector, Inject, Provider}
import com.typesafe.config.Config
import net.codingwell.scalaguice.ScalaModule
import net.shantitree.walee.akkaguice.GuiceAkkaExtension
import net.shantitree.walee.gaia.sync.AkkaModule.ActorSystemProvider

object AkkaModule {
  class ActorSystemProvider @Inject() (val config: Config, val injector: Injector) extends Provider[ActorSystem] {
    override def get() = {
      val system = ActorSystem("walee-gaia-sync-system", config)
      GuiceAkkaExtension(system).initialize(injector)
      system
    }
  }

}

/** A module providing an Akka ActorSystem. */
class AkkaModule extends AbstractModule with ScalaModule {

  override def configure() {

    bind[ActorSystem].toProvider[ActorSystemProvider].asEagerSingleton()

  }


}
