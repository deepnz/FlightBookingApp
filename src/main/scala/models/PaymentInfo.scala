package models

import play.api.libs.json.{Json, OFormat}

case class PaymentInfo(cardNumber: String, cardHolderName: String, expirationDate: String, securityCode: String)

object PaymentInfo {
  implicit val paymentInfoFormat: OFormat[PaymentInfo] = Json.format[PaymentInfo]
}
