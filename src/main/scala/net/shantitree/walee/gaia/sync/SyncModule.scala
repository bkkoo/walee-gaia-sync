package net.shantitree.walee.gaia.sync

import akka.actor.{ActorRef, ActorSystem, Actor}
import com.google.inject.name.Named
import com.google.inject._
import net.codingwell.scalaguice.ScalaModule
import com.typesafe.config.Config
import com.typesafe.slick.driver.ms.SQLServerDriver.simple._
import net.shantitree.walee.akkaguice.GuiceAkkaExtension
import net.shantitree.walee.gaia.sync.SyncModule.PullerDatabaseProvider
import net.shantitree.walee.gaia.sync.puller.{Puller, MemberPuller, member}

object SyncModule {

  class PullerDatabaseProvider @Inject() (@Named("pullerDbConf") val config:Config ) extends Provider[Database] {

    override def get() = {
      val jdbcDriver = config.getString("jdbc-driver")

      // Register JDBC Driver.
      Class.forName(jdbcDriver)

      val slickDriver = config.getString("slick-driver")
      val dbUser = config.getString("user")
      val dbPassword = config.getString("password")
      val url = config.getString("url")

      Database.forURL(url, driver=slickDriver, user=dbUser, password=dbPassword)

    }
  }
}

class SyncModule extends AbstractModule with ScalaModule {

  val pullerActorNames = List(MemberPuller.name)

  override def configure() {

    bind[Database].annotatedWithName("gaia").toProvider[PullerDatabaseProvider].asEagerSingleton()

    bind[List[String]].annotatedWithName("pullerActorNames").toInstance(pullerActorNames)

    bind[Actor].annotatedWithName(Puller.name).to[Puller]
    bind[Actor].annotatedWithName(MemberPuller.name).to[MemberPuller]
    bind[Actor].annotatedWithName(member.Initial.name).to[member.Initial]

  }
  private def provideActorRef(system: ActorSystem, name: String): ActorRef = {
    system.actorOf(GuiceAkkaExtension(system).props(name))
  }

  @Provides @Named("pullerDbConf")
  def getPullerDbConfig(@Inject() config: Config):Config =  {
    config.getConfig("walee-gaia-sync.puller.database")
  }

  @Provides @PullMasterRef
  def providePullMasterRef(@Inject() system: ActorSystem): ActorRef = provideActorRef(system, PullMaster.name)

  @Provides @PushMasterRef
  def providePushMasterRef(@Inject() system: ActorSystem): ActorRef = provideActorRef(system, PushMaster.name)

}
