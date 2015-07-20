package models.cards

import org.scalatest._
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import models.cards._
import models.ModelGens.CardGenerator
import specs.ModelSpec
import services.settings.GameSettings

class CardSpec extends ModelSpec {

  "A card" when {
    implicit val defaultGameSettings: GameSettings = new GameSettings {
      override val BOARD_SIZE: Int = 4
      override val BOARD_MAX_BLOCKS: Int = 6
      override val CARD_MAX_LEVEL: Int = 16
    }

    "created" should {
      "have correct stats levels according to game settings" in {
        forAll(CardGenerator) { card: Card =>
          card.power < defaultGameSettings.CARD_MAX_LEVEL &&
            card.pdef < defaultGameSettings.CARD_MAX_LEVEL &&
            card.mdef < defaultGameSettings.CARD_MAX_LEVEL
        }
      }

      "have a number of arrows less or equal than max arrows" in {
        forAll(CardGenerator) { card: Card =>
          card.arrows.size <= Arrow.MAX_ARROWS
        }
      }

      "have a list of distinct arrows" in {
        forAll(CardGenerator) { card: Card =>
          card.arrows.distinct == card.arrows
        }
      }
    }
  }

}
