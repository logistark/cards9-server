package models

import org.scalatest._
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import _root_.models.cards._

class ArrowSpec extends WordSpec with Matchers with ShouldMatchers with GeneratorDrivenPropertyChecks {
  val zeroByte: Byte = 0x00
  val maxByte: Byte = 0xFF.toByte

  "A packed arrows" when {
    "zero" should {
      "return empty list of arrows" in {
        Arrow.extract(zeroByte) should be(Nil)
      }
    }

    "all bits set to one" should {
      "return list of arrows of size MAX_ARROWS" in {
        Arrow.extract(maxByte).size should be(Arrow.MAX_ARROWS)
      }

      "return list of arrows composed of all arrows" in {
        Arrow.extract(maxByte).toSet should be(Arrow.allArrows.toSet)
      }
    }

    "random bits are set to one" should {
      "compressed arrows should give the same packed byte" in {
        forAll { (packed: Byte) =>
          Arrow.compress(Arrow.extract(packed)) should be(packed)
        }
      }
    }
  }

  "A list of arrows" when {
    val arrowsGen = Gen.choose(1, Arrow.MAX_ARROWS + 1) flatMap { size =>
      Gen.listOfN(size, Gen.oneOf(Arrow.allArrows))
    }

    "empty" should {
      "return a zero compressed byte" in {
        Arrow.compress(Nil) should be(zeroByte)
      }
    }

    "arrows are repeated and/or size is greater than MAX_ARROWS" should {
      "throw an IllegalArgumentException" in {
        forAll(arrowsGen) { (arrows: Seq[Arrow]) =>
          whenever(arrows.distinct.size != arrows.size || arrows.size > Arrow.MAX_ARROWS) {
            intercept[IllegalArgumentException] {
              Arrow.compress(arrows.toList)
            }
          }
        }
      }
    }

    "valid random arrows are selected" should {
      "compress and extract the same list" in {
        forAll(arrowsGen) { (arrows: List[Arrow]) =>
          whenever(arrows.distinct.size == arrows.size && arrows.size <= Arrow.MAX_ARROWS) {
            Arrow.extract(Arrow.compress(arrows)).toSet == arrows.toSet
          }
        }
      }
    }

  }

}