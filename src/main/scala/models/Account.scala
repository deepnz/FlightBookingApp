package models

import play.api.libs.json.{Json, OFormat}

case class Account(id: String, name: String, email: String, password: String)

object Account {
  implicit val accountFormat: OFormat[Account] = Json.format[Account]
}
