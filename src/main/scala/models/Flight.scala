package scala.models

import play.api.libs.json.{Json, OFormat}
import java.time.ZonedDateTime

case class Flight(
                   id: String,
                   airline: String,
                   flightNumber: String,
                   departure: String,
                   destination: String,
                   departureTime: ZonedDateTime,
                   arrivalTime: ZonedDateTime
                 )

object Flight {
  implicit val format: OFormat[Flight] = Json.format[Flight]
}
