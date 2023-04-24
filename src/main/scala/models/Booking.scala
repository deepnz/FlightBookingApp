package scala.models

import play.api.libs.json.{Json, OFormat}

case class Booking(
                    id: String,
                    flightId: String,
                    passengerName: String,
                    passengerEmail: String,
                    date: LocalDate)

object Booking {
  implicit val format: OFormat[Booking] = Json.format[Booking]
}