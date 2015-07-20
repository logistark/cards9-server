package models

import models.cards._
import models.cards.BattleClass._
import services.settings.GameSettings
import org.scalacheck.Gen
import java.net.URL
import scala.util.Random

object ModelGens {
  implicit val CardClassGenerator: Gen[CardClass] = for {
    id <- Gen.choose(0, Int.MaxValue)
    name <- Gen.alphaStr
    img <- Gen.alphaStr
  } yield {
    CardClass(id, name, new URL("http://" + img))
  }

  implicit val BattleClassGenerator: Gen[BattleClass] = Gen.oneOf(Physical, Magical, Flexible, Assault)

  implicit val ArrowsGenerator: Gen[Seq[Arrow]] = Gen.someOf(Arrow.values)

  implicit def CardGenerator(implicit gameSettings: GameSettings): Gen[Card] = for {
    id <- Gen.choose(0, Int.MaxValue)
    ownerId <- Gen.choose(0, Int.MaxValue)
    cardType <- CardClassGenerator
    power <- Gen.choose(0, gameSettings.CARD_MAX_LEVEL - 1)
    battleClass <- BattleClassGenerator
    pdef <- Gen.choose(0, gameSettings.CARD_MAX_LEVEL - 1)
    mdef <- Gen.choose(0, gameSettings.CARD_MAX_LEVEL - 1)
    arrows <- ArrowsGenerator
  } yield {
    Card(id, ownerId, cardType, power, battleClass, pdef, mdef, arrows.toList)
  }
}
