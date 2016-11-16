package controllers

import akka.actor.{Actor, ActorRef}
import play.api.i18n.Lang
import services.VocabularyService

/**
  * Created by carlos on 15/11/16.
  *
  * Creates the actor based on the desired languages as well as the reference to the outgoing channel, out
  */
class QuizActor(out: ActorRef,
                sourceLang: Lang,
                targetLang: Lang,
                vocabulary: VocabularyService)
extends Actor {

  // Keeps track of which word you're currently asking for a translation of
  private var word = ""

  // When starting up, sends a new word to translate
  override def preStart(): Unit = sendWord()

  def  receive = {
    case translation: String
      if vocabulary.verify(
        sourceLang, word, targetLang, translation
      ) =>
        out ! "Correct!"
        // If a correct translation was provided, asks for a new word
        sendWord()
    case _ =>
      out ! "Incorrect, try again!"
  }

  def sendWord() = {
    vocabulary
      .findRandomVocabulary(sourceLang, targetLang).map { v =>
      out ! s"Please translate'${v.word}'"
      word = v.word
    } getOrElse {
      out ! s"I don't know any word for ${sourceLang.code} and ${targetLang.code}"
    }
  }
}
