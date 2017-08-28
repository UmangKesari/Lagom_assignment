package com.knoldus.impl

import akka.NotUsed
import akka.actor.{ActorSystem, Props}
import com.knoldus.api.{ExternalService, UserData, UserDataService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class UserDataServiceImpl(externalService: ExternalService)(implicit ec: ExecutionContext) extends UserDataService
{
  /*override def greetUser(name: String) = ServiceCall { _ =>
    Future.successful("Welcome" + name)
  }
*/

  val system = ActorSystem("userData")
  val userActor = system.actorOf(Props(classOf[UserActor],externalService))

    system.scheduler.schedule(
      0 milliseconds,
      50 milliseconds,
      userActor,
      "scheduler")

  override def getUserList() = ServiceCall[NotUsed,String] { _ =>
var x = ""
    val result: Future[List[UserData]] = externalService.UserList().invoke()
    result.map(response => response.map(user => x=x+user.toString +"\n"
    ))

    Future.successful(x)
  }

}