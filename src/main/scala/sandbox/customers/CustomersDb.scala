package sandbox.customers

import java.util.UUID

import cats.Monad
import cats.syntax.option._
import doobie.ConnectionIO
import sandbox.Utils.CommonDatatypes.CustomerId
import sandbox.customers.CustomersDatatypes.{CustomerDto, CustomerForm}
import doobie.implicits._

class CustomersDb {

  def createCustomer(form: CustomerForm): ConnectionIO[Option[CustomerDto]] =
    Monad[ConnectionIO].pure(CustomerDto(CustomerId(UUID.randomUUID), form.firstName, form.lastName, form.pesel).some)

  def retrieveCustomer(customerId: CustomerId): ConnectionIO[Option[CustomerDto]] =
    Monad[ConnectionIO].pure(CustomerDto(customerId, "Adam", "Miałczyński", 1234567890).some)

  def updateCustomer(customerId: CustomerId, form: CustomerForm): ConnectionIO[Option[CustomerDto]] =
    Monad[ConnectionIO].pure(CustomerDto(customerId, form.firstName, form.lastName, form.pesel).some)

  def deleteCustomer(customerId: CustomerId): ConnectionIO[Option[CustomerDto]] =
    Monad[ConnectionIO].pure(CustomerDto(customerId, "Adam", "Miałczyński", 1234567890).some)

}
