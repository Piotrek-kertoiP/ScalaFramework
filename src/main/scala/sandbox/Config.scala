package sandbox

import com.typesafe.config.ConfigFactory

import scala.io.Source

object Config {

  val version: String = Source.fromResource("version").mkString.stripLineEnd

  private val config = ConfigFactory.load()
  private val sandbox = config.getConfig("sandbox")

  object Sandbox {
    val host: String = sandbox.getString("host")
    val port: Int = sandbox.getInt("port")

    val actorSystemName: String = sandbox.getString("actor-system-name")

    object Database {
      val host: String = sandbox.getString("postgres.host")
      val name: String = sandbox.getString("postgres.name")
      val pass: String = sandbox.getString("postgres.pass")
      val port: Int = sandbox.getInt("postgres.port")
      val user: String = sandbox.getString("postgres.user")
      val driver: String = sandbox.getString("postgres.driver")
      val jdbcPrefix: String = sandbox.getString("postgres.jdbcPrefix")
      val migrationDir: String = sandbox.getString("postgres.migration-dir")
      val threadPoolSize: Int = sandbox.getInt("postgres.thread-pool-size")
      val dbMaxConnections: Int = sandbox.getInt("postgres.db.max-connections")
    }
  }
}
