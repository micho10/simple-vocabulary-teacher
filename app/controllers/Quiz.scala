package controllers

import play.api.i18n.Lang
import play.api.mvc.Controller
import play.mvc.Http.Context.Implicit

/**
 * Created by carlos on 07/11/16.
 */
class Quiz @Implicit extends Controller {

  /**
   *
   * @param sourceLanguage
   * @param targetLanguage
   * @return 200 OK result that wraps a random word, if there is one;
   *         404 Not Found result otherwise
   */
  def quiz(sourceLanguage: Lang, targetLanguage: Lang) = TODO

  /**
   * An Action that verifies a word.
   *
   * @param sourceLanguage
   * @param word
   * @param targetLanguage
   * @param translation
   * @return 200 Ok result if the translation is correct;
   *         406 Not Acceptable otherwise
   */
  def check(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) = TODO

}

