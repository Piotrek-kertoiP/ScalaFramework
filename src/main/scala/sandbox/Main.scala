package sandbox

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{concat, pathPrefix}
import cats.effect.{Blocker, ContextShift, IO, Resource}
import com.typesafe.scalalogging.StrictLogging
import doobie.free.KleisliInterpreter
import doobie.util.transactor.{Strategy, Transactor}
import org.flywaydb.core.api.FlywayException
import sandbox.customers.{CustomersApi, CustomersDb, CustomersManager}
import sandbox.status.StatusApi

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

object Main extends App with StrictLogging {
  //todo: fix StrictLogging logger + logback
  println(s"Starting...")
  implicit val system: ActorSystem = ActorSystem(Config.Sandbox.actorSystemName)
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.fromExecutor(Executors.newCachedThreadPool()))

  val useDb = false
  println(s"Implicits done, useDb = $useDb")

  if(useDb) {
    Try(Db.initializeDb()) match {
      case Success(_) =>
        println("Initialization successful")
      case Failure(ex: FlywayException) =>
        logger.error("Failed fo apply database migrations", ex)
        val _ = system.terminate()
      case Failure(ex) =>
        logger.error("Unexpected exception on initialization", ex)
        val _ = system.terminate()
    }
  } else ()
  println(s"DB done")
  val transactor: Transactor[IO] = if(useDb) {
    Db.provideTransactor().allocated.unsafeRunSync._1
  } else {
    //noinspection ScalaStyle
    Transactor(
      (),
      (_: Unit) => Resource.pure(null),
      KleisliInterpreter[IO](Blocker.liftExecutionContext(ExecutionContext.global)).ConnectionInterpreter,
      Strategy.void
    )
  }
  println("Transactor done")

  val customersDb = new CustomersDb
  val customersManager = new CustomersManager(customersDb)(ec, transactor)
  val customersApi = new CustomersApi(customersManager)
  println("Customers done")

  val routes =
    pathPrefix("api") {
      concat(
        StatusApi.routes,
        customersApi.routes
      )
    }
  println("Routes done")
  /* Start any Kafka consumers here - example: kafkaMessageConsumer.processMessages() */


  /* Bind HTTP routes and start server */
  val _ = Http().newServerAt(Config.Sandbox.host, Config.Sandbox.port).bind(routes)
}
