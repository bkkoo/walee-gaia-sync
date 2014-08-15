package net.shantitree.walee.gaia.sync.puller

import akka.actor.{Actor, ActorLogging, Props}

object Puller {
  import java.sql.Timestamp

  //Message --
  case class GetDatabaseConnector()
  case class InitialCommit(createdBefore: Timestamp)

  // --

  case class DatabaseConfig(
    jdbcDriver: String,
    slickDriver: String,
    name: String,
    host: String,
    port: Int,
    user: String,
    password: String,
    url: String
  )

  private def getDatabaseConfig(config: java.util.Map[String, AnyRef]) = {
    import scala.collection.JavaConverters._
    val c = config.asScala
    DatabaseConfig(
      c("jdbc-driver").asInstanceOf[String],
      c("slick-driver").asInstanceOf[String],
      c("name").asInstanceOf[String],
      c("host").asInstanceOf[String],
      c("port").asInstanceOf[Int],
      c("user").asInstanceOf[String],
      c("password").asInstanceOf[String],
      c("url").asInstanceOf[String]
    )
  }
  private def getDatabaseConnector(config:DatabaseConfig) = {

    // load driver (may fail!)
    Class.forName(config.jdbcDriver)

    import com.typesafe.slick.driver.ms.SQLServerDriver.simple._
    Database.forURL(config.url, driver=config.slickDriver, user=config.user, password=config.password)
  }
}

class Puller extends Actor with ActorLogging{

  import net.shantitree.walee.gaia.sync.puller.Puller._
  import net.shantitree.walee.gaia.sync.puller.member.Initial
  import net.shantitree.walee.gaia.sync.puller.member.Initial._

  val databaseConfig = getDatabaseConfig(context.system.settings.config.getObject("walee-gaia-sync.puller.database").unwrapped())
  val databaseConnector = getDatabaseConnector(databaseConfig)


  override def preStart = {
    context.actorOf(Props(classOf[MemberPuller], databaseConnector), "memberPuller")
  }

  def receive = {

    case GetDatabaseConnector =>
      sender ! databaseConnector

    case m@InitialCommit(createBefore) =>


    case PullFinish(result) =>
      println(result)
      context.system.shutdown()
  }

}
