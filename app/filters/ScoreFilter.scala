package filters

import play.api.libs.iteratee.Enumerator
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.Future

/**
 * Created by carlos on 17/11/16.
 */
class ScoreFilter extends Filter {

  override def apply(
    // The nextFilter function represents the next request handler in the chain, which is usually a filter
    nextFilter: (RequestHeader) => Future[Result] // Provides the request header of the current request as well
    )(rh: RequestHeader): Future[Result] = {
    // Applies the request header to the next filter to get the result of the operation
    val result = nextFilter(rh)
    // Imports an ExecutionContext to run the Future request
    import play.api.libs.concurrent.Execution.Implicits._
    result.map { res =>
      // Only deals with Ok or Not Acceptable requests
      if (res.header.status == 200 || res.header.status == 406) {
        val correct = res.session(rh).get("correct").getOrElse(0)
        val wrong = res.session(rh).get("wrong").getOrElse(0)
        val score = s"\nYour current score is: $correct correct answers and $wrong wrong answers"
        // Concatenates the existing response body and your score result
        val newBody = res.body andThen Enumerator(score.getBytes("UTF-8"))
        // Returns a copy of the result containing the modified body
        res.copy(body = newBody)
      } else res
    }
  }

}
