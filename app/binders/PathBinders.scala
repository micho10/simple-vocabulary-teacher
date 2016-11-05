package binders

import play.api.i18n.Lang
import play.api.mvc.PathBindable

/**
 * Created by micho on 06/11/16.
 *
 * Places all binders in one object to simplify importing them into the router
 */
object PathBinders {

  // Declares the PathBindable as an implicit object so it's resolved implicitly by the router
  implicit object LangPathBindable extends PathBindable[Lang] {
    // Implements the bind method to read a query fragment as a type
    override def bind(key: String, value: String): // Encodes the result of a binding as Either[String, Lang], which means the resulting of a binding is either
    // an error message, or the successfully read Lang value
    Either[String, Lang] =
      // Check if there's a language for the input value; otherwise returns an error message
      Lang.get(value).toRight(s"Language $value is not recognized")

    // Implements the unbind method to write a type as a path fragment
    override def unbind(key: String, value: Lang): String = value.code
  }

}
