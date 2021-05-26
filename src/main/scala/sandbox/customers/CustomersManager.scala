package sandbox.customers

import cats.effect.IO
import cats.implicits._
import com.typesafe.scalalogging.StrictLogging
import doobie.implicits._
import doobie.util.transactor.Transactor
import sandbox.AppError
import sandbox.Utils.CommonDatatypes.CustomerId
import sandbox.customers.CustomersDatatypes.{CustomerDto, CustomerForm}

import scala.concurrent.{ExecutionContext, Future}

class CustomersManager(customersDb: CustomersDb)
                      (implicit ec: ExecutionContext, transactor: Transactor[IO]) extends StrictLogging {

  def createCustomer(form: CustomerForm): Future[Either[AppError, CustomerDto]] = {
    logger.debug(s"Creating record $form in customers table")
    customersDb.createCustomer(form).transact(transactor).attempt.unsafeToFuture().map {
      case Right(Some(newRecord)) =>
        logger.debug(s"Successfuly created customer with form $form")
        newRecord.asRight[AppError]
      case Right(None) =>
        logger.warn(s"Failed to create customer with form $form")
        AppError.BadRequest("Failed to create customer").asLeft[CustomerDto]
      case Left(throwable) =>
        logger.warn(s"Failed to create customer form $form", throwable)
        AppError.DatabaseError("Failed to create customer").asLeft[CustomerDto]
    }
  }

  def retrieveCustomer(customerId: CustomerId): Future[Either[AppError, CustomerDto]] = {
    logger.debug(s"Retrieving customer with id $customerId")
    customersDb.retrieveCustomer(customerId).transact(transactor).attempt.unsafeToFuture.map {
      case Right(Some(customerDto)) =>
        logger.debug(s"Successfully retrieved customer with id $customerId")
        customerDto.asRight[AppError]
      case Right(None) =>
        logger.warn(s"Failed to retrieve customer with id $customerId, customer not found")
        AppError.NotFound("Failed to retrieve customer").asLeft[CustomerDto]
      case Left(throwable) =>
        logger.warn(s"Failed to retrieve customer with id $customerId", throwable)
        AppError.DatabaseError("Failed to retrieve customer").asLeft[CustomerDto]
    }
  }

  def updateCustomer(customerId: CustomerId, form: CustomerForm): Future[Either[AppError, CustomerDto]] = {
    logger.debug(s"Updating customer with id $customerId, form $form")
    customersDb.updateCustomer(customerId, form).transact(transactor).attempt.unsafeToFuture().map {
      case Right(Some(newRecord)) =>
        logger.debug(s"Successfully updated customer with id $customerId, form $form")
        newRecord.asRight[AppError]
      case Right(None) =>
        logger.warn(s"Failed to update customer with id $customerId, form $form")
        AppError.NotFound("Failed to update customer").asLeft[CustomerDto]
      case Left(throwable) =>
        logger.warn(s"Failed to update customer with id $customerId, form $form", throwable)
        AppError.DatabaseError("Failed to update customer").asLeft[CustomerDto]
    }
  }

  def deleteCustomer(customerId: CustomerId): Future[Either[AppError, CustomerDto]] = {
    logger.debug(s"Deleting customer with id $customerId")
    customersDb.deleteCustomer(customerId).transact(transactor).attempt.unsafeToFuture().map {
      case Right(Some(deletedRecord)) =>
        logger.debug(s"Successfully deleted customer with id $customerId")
        deletedRecord.asRight[AppError]
      case Right(None) =>
        logger.warn(s"Failed to delete customer with id $customerId")
        AppError.NotFound("Failed to delete customer").asLeft[CustomerDto]
      case Left(throwable) =>
        logger.warn(s"Failed to delete customer with id $customerId", throwable)
        AppError.DatabaseError("Failed to delete customer").asLeft[CustomerDto]
    }
  }

}
