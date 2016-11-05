package controllers

import play.api.i18n.Lang
import play.api.mvc.Controller

/**
 * Created by carlos on 04/11/16.
 */
class Import extends Controller {

  def importWord(
    sourceLanguage: Lang,
    word: String,
    targetLanguage: Lang,
    translation: String) = TODO

}
