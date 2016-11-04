import javax.inject._
import play.api.http.DefaultHttpErrorHandler
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.routing.Router
import scala.concurrent._

// Defines an ErrorHandler discoverable via dependency injection
class ErrorHandler @Inject() (
  env: Environment,
  config: Configuration,
  sourceMapper: OptionalSourceMapper,
  router: Provider[Router])
    // Extends the DefaultHttpErrorHandler trait, which is the hook to error-handling customization in Play
    extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  // Overrides the onNotFound method to intercept Play's default behaviour when an error occurs during communication with the client 
  override protected def onNotFound(
    request: RequestHeader, message: String): Future[Result] = {
    Future.successful {
      // Returns a 404 Not Found result with a message containing the String representation of the result
      NotFound("Could not find " + request + "\n")
    }
  }

}

