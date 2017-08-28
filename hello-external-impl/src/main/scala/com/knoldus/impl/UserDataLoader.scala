package com.knoldus.impl

import com.knoldus.api.{ExternalService, UserDataService}
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents

class UserDataLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new UserDataApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new UserDataApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[UserDataService])
}

abstract class UserDataApplication(context: LagomApplicationContext)
  extends LagomApplication(context)

    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[UserDataService](wire[UserDataServiceImpl])


  lazy val externalService = serviceClient.implement[ExternalService]

  // Register the JSON serializer registry
}
