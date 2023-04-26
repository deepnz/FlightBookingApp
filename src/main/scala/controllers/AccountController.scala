package scala.controllers

import javax.inject.{Inject, Singleton}
import models.{Account, PaymentInfo}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.AccountService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class AccountController @Inject()(cc: ControllerComponents, accountService: AccountService) extends AbstractController(cc) {

  def createAccount(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[Account].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Invalid account data"))),
      account => {
        accountService.createAccount(account).map { _ =>
          Created(Json.obj("message" -> "Account created successfully"))
        } recover {
          case _: Exception => InternalServerError(Json.obj("message" -> "Failed to create account"))
        }
      }
    )
  }

  def getAccount(id: String): Action[AnyContent] = Action.async {
    accountService.getAccountById(id).map {
      case Some(account) => Ok(Json.toJson(account))
      case None => NotFound(Json.obj("message" -> "Account not found"))
    } recover {
      case _: Exception => InternalServerError(Json.obj("message" -> "Failed to get account"))
    }
  }

  def updatePaymentInfo(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[PaymentInfo].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Invalid payment info data"))),
      paymentInfo => {
        accountService.updatePaymentInfo(id, paymentInfo).map { _ =>
          Ok(Json.obj("message" -> "Payment info updated successfully"))
        } recover {
          case _: Exception => InternalServerError(Json.obj("message" -> "Failed to update payment info"))
        }
      }
    )
  }

}
