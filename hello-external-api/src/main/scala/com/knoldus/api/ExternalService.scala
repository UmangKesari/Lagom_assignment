package com.knoldus.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait ExternalService extends Service
{
  def UserList(): ServiceCall[NotUsed, List[UserData]]

  override final def descriptor = {
    import Service._
    named("external-service")
      .withCalls(
        pathCall("/posts", UserList _)
      ).withAutoAcl(true)
    // @formatter:on
  }
}
