package sandbox.status

import akka.http.scaladsl.server.Directives.{complete, path}
import akka.http.scaladsl.server.Route
import cats.Eq
import cats.implicits._
import enumeratum.{Enum, EnumEntry}
import sandbox.Config
import sandbox.Utils.JsonSupport
import sandbox.status.StatusDatatypes.StatusEntity

object StatusApi extends JsonSupport {
  def routes: Route =
    path("status") {
      complete(StatusEntity("sandbox", "OK", Config.version))
    }
}

object StatusDatatypes {

  final case class StatusEntity(name: String, status: String, version: String)

  sealed abstract class SandboxStatus(override val entryName: String) extends EnumEntry

  object SandboxStatus extends Enum[SandboxStatus] {
    val values = findValues

    case object OK extends SandboxStatus("OK")

    case object Error extends SandboxStatus("ERROR")

    implicit val statusEq: Eq[SandboxStatus] = (x: SandboxStatus, y: SandboxStatus) => x.entryName === y.entryName
  }

}
