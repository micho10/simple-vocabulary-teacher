package controllers

import javax.inject.Inject

import play.api.i18n.Lang
import play.api.mvc.{ Action, Controller }
import services.VocabularyService

/**
 * Created by carlos on 07/11/16.
 */
class Quiz @Inject() (vocabulary: VocabularyService) extends Controller {

  /**
   *
   * @param sourceLanguage
   * @param targetLanguage
   * @return 200 OK result that wraps a random word, if there is one;
   *         404 Not Found result otherwise
   */
  def quiz(sourceLanguage: Lang, targetLanguage: Lang) = Action { request =>
    val foundWord = vocabulary.findRandomVocabulary(sourceLanguage, targetLanguage)
    foundWord match {
      case Some(_) => Ok
      case None => NotFound
    }
  }

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
  def check(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) = Action { request =>
    val correct = vocabulary.verify(sourceLanguage, word, targetLanguage, translation)
    if (correct) Ok
    else NotAcceptable
  }

}
