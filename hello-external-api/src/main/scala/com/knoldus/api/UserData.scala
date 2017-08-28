package com.knoldus.api

import play.api.libs.json.{Format, Json}

case class UserData(id : Int, username : String, mobileNumber : Long)

object UserData {
  implicit val format: Format[UserData] = Json.format[UserData]
}