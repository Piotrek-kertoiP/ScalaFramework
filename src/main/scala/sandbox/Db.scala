package sandbox

import cats.effect.{Blocker, ContextShift, IO, Resource}
import com.zaxxer.hikari.HikariConfig
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import sandbox.Config.Sandbox.Database._
import org.flywaydb.core.Flyway

class Db {

  val connString = s"$jdbcPrefix$host:$port/$name"

  def initializeDb(): Unit = {
    val flyway = new Flyway
    flyway.setDataSource(connString, user, pass)
    flyway.setLocations(migrationDir)
    val _ = flyway.migrate()
  }

  def provideTransactor()(implicit cs: ContextShift[IO]): Resource[IO, HikariTransactor[IO]] = {

    val hikariConfig = new HikariConfig()
    hikariConfig.setUsername(user)
    hikariConfig.setPassword(pass)
    hikariConfig.setJdbcUrl(s"$jdbcPrefix$host:$port/$name")
    hikariConfig.setMaximumPoolSize(dbMaxConnections)
    hikariConfig.setDriverClassName(driver)
    for {
      connectionContext <- ExecutionContexts.fixedThreadPool[IO](threadPoolSize)
      transactionsContext <- ExecutionContexts.cachedThreadPool[IO]
      xa <- HikariTransactor.fromHikariConfig[IO](
        hikariConfig,
        connectionContext,
        Blocker.liftExecutionContext(transactionsContext)
      )
    } yield xa
  }
}
