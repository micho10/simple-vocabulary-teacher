package models

import play.api.i18n.Lang

/**
  * Created by carlos on 07/11/16.
  */
case class Vocabulary(
                     sourceLanguage: Lang,
                     targetLanguage: Lang,
                     word: String,
                     translation: String
                     )
