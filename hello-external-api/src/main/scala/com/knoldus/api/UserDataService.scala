package com.knoldus.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait UserDataService extends Service {

  def getUserList(): ServiceCall[NotUsed, String]

  override final def descriptor = {

    import Service._

    named("user")
      .withCalls(
        restCall(Method.GET, "/api/user", getUserList _)
      )
      .withAutoAcl(true)
  }
}

