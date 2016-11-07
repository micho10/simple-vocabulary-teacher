package services

import javax.inject.Singleton

import models.Vocabulary
import play.api.i18n.Lang

/**
  * Created by carlos on 07/11/16.
  */
// Specifies that the VocabularyService class has singleton scope, which means the same instance will be injected in all
// classes having a dependency on it.
@Singleton
class VocabularyService {

  // Bootstraps the list with a minimal vocabulary because the list will be lost with each application reload
  private var allVocabulary = List(
    Vocabulary(Lang("en"), Lang("fr"), "hello", "bonjour"),
    Vocabulary(Lang("en"), Lang("fr"), "play", "jouer")
  )

  def addVocabulary(v: Vocabulary): Boolean = {
    // Only adds vocabulary that doesn't exist yet, and returns a Boolean
    if (!allVocabulary.contains(v)) {
      allVocabulary = v :: allVocabulary
      true
    } else false
  }

}
