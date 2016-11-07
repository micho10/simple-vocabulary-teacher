package controllers

import javax.inject.Inject

import models.Vocabulary
import play.api.i18n.Lang
import play.api.mvc.{ Action, Controller }
import services.VocabularyService

/**
 * Created by carlos on 04/11/16.
 *
 * The Import class requires a VocabularyService to be constructed.
 */
class Import @Inject() (vocabulary: VocabularyService) extends Controller {

  def importWord(
    sourceLanguage: Lang,
    word: String,
    targetLanguage: Lang,
    translation: String // Uses the Action constructor to build a simple Action
    ) = Action { request =>
    val added = vocabulary.addVocabulary(
      Vocabulary(sourceLanguage, targetLanguage, word, translation)
    )
    // If adding was successful, returns a 200 Ok response
    if (added) Ok
    // If adding didn't work (because you already added this word), returns a 409 Conflict response
    else Conflict
  }

}
