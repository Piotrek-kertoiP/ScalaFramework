package sandbox.customers

import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, entity, failWith, onComplete, pathEnd, pathPrefix, _}
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.StrictLogging
import sandbox.AppError.DatabaseError
import sandbox.Utils.CommonDatatypes.CustomerId
import sandbox.Utils.JsonSupport
import sandbox.Utils.PathMatchers.CustomerUUID
import sandbox.customers.CustomersDatatypes.CustomerForm

import scala.util.{Failure, Success}

class CustomersApi(customersManager: CustomersManager) extends StrictLogging with JsonSupport {

  val routes: Route =
    pathPrefix("customers") {
      createCustomer ~ retrieveCustomer ~ updateCustomer ~ deleteCustomer
    }

  private def createCustomer: Route =
    (pathEnd & post) {
      entity(as[CustomerForm]) { form =>
        onComplete(customersManager.createCustomer(form)) {
          case Success(Right(result)) =>
            complete(result)
          case Success(Left(err: DatabaseError)) =>
            logger.warn(err.message)
            complete(HttpResponse(status = StatusCodes.BadRequest, entity = HttpEntity(err.getMessage)))
          case Success(Left(err)) =>
            logger.warn(err.message)
            complete(HttpResponse(status = err.statusCode, entity = HttpEntity(err.getMessage)))
          case Failure(ex) =>
            logger.error("Ooops", ex)
            failWith(ex)
        }
      }
    }

  private def retrieveCustomer: Route =
    (path(CustomerUUID) & get) { customerId =>
      onComplete(customersManager.retrieveCustomer(customerId)) {
        case Success(Right(result)) =>
          complete(result)
        case Success(Left(err: DatabaseError)) =>
          logger.warn(err.message)
          complete(HttpResponse(status = StatusCodes.BadRequest, entity = HttpEntity(err.getMessage)))
        case Success(Left(err)) =>
          logger.warn(err.message)
          complete(HttpResponse(status = err.statusCode, entity = HttpEntity(err.getMessage)))
        case Failure(ex) =>
          logger.error("Ooops", ex)
          failWith(ex)
      }
    }

  private def updateCustomer: Route =
    (path(CustomerUUID) & put) { customerId =>
      entity(as[CustomerForm]) { form =>
        onComplete(customersManager.updateCustomer(customerId, form)) {
          case Success(Right(result)) =>
            complete(result)
          case Success(Left(err: DatabaseError)) =>
            logger.warn(err.message)
            complete(HttpResponse(status = StatusCodes.BadRequest, entity = HttpEntity(err.getMessage)))
          case Success(Left(err)) =>
            logger.warn(err.message)
            complete(HttpResponse(status = err.statusCode, entity = HttpEntity(err.getMessage)))
          case Failure(ex) =>
            logger.error("Ooops", ex)
            failWith(ex)
        }
      }
    }

  private def deleteCustomer: Route =
    (path(CustomerUUID) & delete) { customerId =>
      onComplete(customersManager.deleteCustomer(customerId)) {
        case Success(Right(result)) =>
          complete(result)
        case Success(Left(err: DatabaseError)) =>
          logger.warn(err.message)
          complete(HttpResponse(status = StatusCodes.BadRequest, entity = HttpEntity(err.getMessage)))
        case Success(Left(err)) =>
          logger.warn(err.message)
          complete(HttpResponse(status = err.statusCode, entity = HttpEntity(err.getMessage)))
        case Failure(ex) =>
          logger.error("Ooops", ex)
          failWith(ex)
      }
    }

}

object CustomersDatatypes {

  final case class CustomerForm(firstName: String, lastName: String, pesel: Long)

  final case class CustomerDto(id: CustomerId, firstName: String, lastName: String, pesel: Long)

}
