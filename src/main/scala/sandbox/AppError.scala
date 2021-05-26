package sandbox

import akka.http.scaladsl.model.{StatusCode, StatusCodes}

trait AppError extends Exception {
  def message: String

  def statusCode: StatusCode

  override def getMessage: String = s"$statusCode: $message"
}

object AppError {

  final case class Conflict(message: String) extends AppError {
    override def statusCode: StatusCode = StatusCodes.Conflict
  }

  final case class NotFound(message: String) extends AppError {
    override def statusCode: StatusCode = StatusCodes.NotFound
  }

  final case class BadRequest(message: String) extends AppError {
    override def statusCode: StatusCode = StatusCodes.BadRequest
  }

  final case class Unauthorized(message: String) extends AppError {
    override def statusCode: StatusCode = StatusCodes.Unauthorized
  }

  final case class DatabaseError(message: String) extends AppError {
    override def statusCode: StatusCode = StatusCodes.InternalServerError
  }

  final case class InternalServerError(message: String) extends AppError {
    override def statusCode: StatusCode = StatusCodes.InternalServerError
  }

}
