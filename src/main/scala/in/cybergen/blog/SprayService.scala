package in.cybergen.blog

import spray.routing.SimpleRoutingApp
import akka.actor.ActorSystem
import spray.http._
import HttpMethods._

/**
 * Created by vishnu on 16/11/14.
 */
object SprayService extends App with SimpleRoutingApp {
  implicit val actorSystem = ActorSystem();
  startServer(interface = "localhost",port = 8080){
    get{
      path("hello"){
        complete{
          "welcome to Silicon Valley"
        }
      }
    }
  }
}

//class SprayService extends Specification {
//  class Actor1 extends Actor {
//    //# simple-reply
//    def receive = {
//      case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
//        sender ! HttpResponse(entity = "PONG")
//    }
//    //#
//  }
//  "bind-example" in compileOnly {
//    import akka.io.IO
//    import spray.can.Http
//
//    implicit val system = ActorSystem()
//
//    val myListener: ActorRef = // ...
//      null // hide
//
//    IO(Http) ! Http.Bind(myListener, interface = "localhost", port = 8080)
//  }
//}