package in.cybergen.blog

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http

object SprayMain extends App {

  implicit val system = ActorSystem()

  // the handler actor replies to incoming HttpRequests
  val handler = system.actorOf(Props[SprayBenckMarkService], name = "handler")

  val interface = system.settings.config.getString("app.interface")
  val port = system.settings.config.getInt("app.port")
  IO(Http) ! Http.Bind(handler, interface, port)
}
