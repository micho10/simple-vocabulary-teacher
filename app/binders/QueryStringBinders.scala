package binders

import play.api.i18n.Lang
import play.api.mvc.QueryStringBindable

/**
 * Created by carlos on 08/11/16.
 */
object QueryStringBinders {

  // Declares a QueryStringBindable as an implicit object so it's resolved implicitly by the router
  implicit object LangQueryStringBindable extends QueryStringBindable[Lang] {

    // Implements the bind method to read a query fragment as a type
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Lang]] = {
      val code = params.get(key).flatMap(_.headOption)
      code.map { c =>
        Lang.get(c).toRight(s"$c is not a valid language")
      }
    }

    // Implements the unbind method to write a type as a path fragment
    override def unbind(key: String, value: Lang): String = value.code
  }

}
