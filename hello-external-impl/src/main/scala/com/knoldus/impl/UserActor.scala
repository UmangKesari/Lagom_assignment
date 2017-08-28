package com.knoldus.impl

import akka.actor.{Actor, ActorSystem, Props}
import com.knoldus.api.ExternalService
import play.api.Logger
import scala.concurrent.ExecutionContext.Implicits.global

class UserActor(externalService: ExternalService) extends Actor {

  val system = ActorSystem("UserDataActor")
  val userdata = system.actorOf(Props[UserActor], "user")
  val details = system.actorOf(Props(classOf[UserActor], userdata), "userdetails")

  override def receive(): Receive = {
    case "scheduler" =>
      externalService.UserList.invoke().onSuccess {
        case userlist => userlist.map {
          user =>
            Logger.info(user.id + "\t" + user.username + "\t" + user.mobileNumber)
        }
      }
    //      case Failure => Logger.info("Some exception occurred")
  }
}


