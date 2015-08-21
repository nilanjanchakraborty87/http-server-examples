package in.cybergen.blog

import akka.actor.Actor
import spray.can.Http
import spray.http.HttpMethods._
import spray.http.StatusCodes._
import spray.http._

class SprayBenckMarkService extends Actor {
  import spray.http.Uri.Path._
  import spray.http.Uri._

  def jsonResponseEntity = HttpEntity(
    contentType = ContentTypes.`application/json`,
    string = "{\"status\":\"ok\"}")

  def fastPath: Http.FastPath = {
    case HttpRequest(GET, Uri(_, _, Slash(Segment("fast-ping", Path.Empty)), _, _), _, _, _) =>
      HttpResponse(entity = "FAST-PONG!")

    case HttpRequest(GET, Uri(_, _, Slash(Segment("fast-json", Path.Empty)), _, _), _, _, _) =>
      HttpResponse(entity = jsonResponseEntity)
  }

  def receive = {
    case _: Http.Connected => sender ! Http.Register(self, fastPath = fastPath)
    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) => sender ! HttpResponse(entity = "\"status\":\"ok\"")
    case HttpRequest(GET, Uri.Path("/"), _, _, _) => sender ! HttpResponse(
      entity = HttpEntity(MediaTypes.`text/html`,
        <html>
          <body>
            <h1>Tiny <i>spray-can</i> benchmark server</h1>
            <p>Defined resources:</p>
            <ul>
              <li><a href="/ping">/ping</a></li>
            </ul>
          </body>
        </html>.toString()
      )
    )
    case _: HttpRequest => sender ! HttpResponse(NotFound, entity = "Unknown resource!")
  }
}
