package controllers

import javax.inject.Inject

import play.api.i18n.Lang
import play.api.mvc.{ Action, Controller, WebSocket }
import services.VocabularyService

import play.api.Play.current

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
    val isCorrect =
      vocabulary
        .verify(sourceLanguage, word, targetLanguage, translation)
    val correctScore =
      // Reads the previous score from the session and converts the counts from String to Int
      request.session.get("correct").map(_.toInt).getOrElse(0)
    val wrongScore =
      request.session.get("wrong").map(_.toInt).getOrElse(0)
    if (isCorrect)
      // Sets a  new session with an adjusted score
      Ok.withSession(
        "correct" -> (correctScore + 1).toString,
        "wrong" -> wrongScore.toString
      )
    else
      NotAcceptable.withSession(
        "correct" -> correctScore.toString,
        "wrong" -> (wrongScore + 1).toString
      )
  }

  // Languages are provided as parameters to the handler method
  def quizEndPoint(sourceLang: Lang, targetLang: Lang) =
    // Incoming and outgoing messages are both Strings. To correctly set up the WebSocket connection, Play needs to know
    // what the encoding of the messages is going to be.
    WebSocket.acceptWithActor[String, String] {
      // RequestHeaders of the incoming request can be used to check whether the connection should be established
      request =>
        // The actor reference out represents the outgoing channel to the client
        out =>
          // The call to the helper method that returns the Props of a QuizActorto be created
          QuizActor.props(out, sourceLang, targetLang, vocabulary)
    }

}
