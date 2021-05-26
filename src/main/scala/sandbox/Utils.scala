package sandbox

import java.util.UUID

import akka.http.scaladsl.server.Directives.JavaUUID
import akka.http.scaladsl.server.PathMatcher1
import cats.Eq
import com.softwaremill.tagging.@@
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import doobie.Meta
import enumeratum.EnumEntry.Uppercase
import enumeratum.{Enum, EnumEntry, Json4s}
import org.json4s.native.Serialization
import org.json4s.{Formats, NoTypeHints, Serialization}
import sandbox.Utils.CommonDatatypes._
import sandbox.status.StatusDatatypes

import scala.collection.immutable.IndexedSeq

object Utils {

  trait JsonSupport extends Json4sSupport {
    implicit val serialization: Serialization = Serialization

    implicit val formats: Formats = (
      Serialization.formats(NoTypeHints)
        + Json4s.serializer(Role)
        + Json4s.serializer(StatusDatatypes.SandboxStatus)
        ++ org.json4s.ext.JavaTypesSerializers.all
      )
  }

  trait DoobieSupport extends JsonSupport {

    implicit val uuidMeta: Meta[UUID] =
      Meta[String].timap(UUID.fromString)(_.toString)

    implicit val orgIdMeta: Meta[OrgId] =
      uuidMeta.timap(OrgId.apply)(identity)

  }

  object EqImplicits {
    implicit val eqOrgId: Eq[OrgId] = Eq.fromUniversalEquals
    implicit val eqStr: Eq[String] = Eq.fromUniversalEquals
  }

  object PathMatchers {
    val OrgUUID: PathMatcher1[OrgId] = JavaUUID.map(OrgId(_))
    val CustomerUUID: PathMatcher1[CustomerId] = JavaUUID.map(CustomerId(_))
  }

  object CommonDatatypes {
    /*----------- OrgId -----------*/
    trait OrgIdTag

    type OrgId = UUID @@ OrgIdTag

    object OrgId {
      @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
      def apply(uuid: UUID): OrgId =
        uuid.asInstanceOf[OrgId]
    }
    /*----------- CustomerId -----------*/
    trait CustomerIdTag

    type CustomerId = UUID @@ CustomerIdTag

    object CustomerId {
      @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
      def apply(uuid: UUID): CustomerId =
        uuid.asInstanceOf[CustomerId]
    }
    /*----------- Role -----------*/
    sealed abstract class Role extends EnumEntry

    object Role extends Enum[Role] {

      case object Agent extends Role with Uppercase

      case object Admin extends Role with Uppercase

      case object Customer extends Role with Uppercase

      val values: IndexedSeq[Role] = findValues

    }

  }

}
