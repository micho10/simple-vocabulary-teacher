import javax.inject.Inject

import filters.ScoreFilter
import play.api.http.HttpFilters
import play.filters.gzip.GzipFilter
import play.filters.headers.SecurityHeadersFilter

/**
 * Created by carlos on 17/11/16.
 */
class Filters @Inject()(
    // Injects the GzipFilter, which gzips responses sent to the client to speed things up a little
    gzip: GzipFilter,
    score: ScoreFilter
    // The HttpFilters trait sets up a filter chain with the filters you specify
    ) extends HttpFilters {
  // Specifies the filters you'd like to apply in the order they should be applied. Play's SecurityHeadersFilter adds
  // a number of header-based security checks and policies.
  val filters = Seq(gzip, SecurityHeadersFilter())
}
