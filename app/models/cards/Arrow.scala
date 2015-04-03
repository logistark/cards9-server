package models.cards

/**
 * Card arrows.
 *
 * Packed representation (bits):
 *
 *   N   NE  E   SE  S   SW  W   NW
 * ---------------------------------
 * | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
 * ---------------------------------
 *
 */
sealed trait Arrow { def hex: Byte }

case object N extends Arrow { val hex: Byte = 0x80.toByte }
case object NE extends Arrow { val hex: Byte = 0x40 }
case object E extends Arrow { val hex: Byte = 0x20 }
case object SE extends Arrow { val hex: Byte = 0x10 }
case object S extends Arrow { val hex: Byte = 0x08 }
case object SW extends Arrow { val hex: Byte = 0x04 }
case object W extends Arrow { val hex: Byte = 0x02 }
case object NW extends Arrow { val hex: Byte = 0x01 }

object Arrow {
  val MAX_ARROWS = 8
  val allArrows: List[Arrow] = List(N, NE, E, SE, S, SW, W, NW)

  /**
   * Extract a list of arrows from a packed byte.
   *
   * @param packed a byte with packed arrows
   *
   * @return a list with the arrows contained into the packed byte
   */
  def extract(packed: Byte): List[Arrow] = allArrows.filterNot(arrow => (arrow.hex & packed) == 0)

  /**
   * Compresses a list of arrows into a packed byte.
   *
   * @param arrows list of arrows
   * <b>Precondition:</b>
   * arrows must be a list of distinct arrows, with a max size of [[MAX_ARROWS]]
   *
   * @return a byte with the arrows compressed
   */
  def compress(arrows: List[Arrow]): Byte = {
    // Do not repeat arrows and do not exceed max arrows of card
    require(arrows.distinct.size == arrows.size)
    require(arrows.size < MAX_ARROWS + 1)

    // Card with no arrows...
    if (arrows.isEmpty) 0x00
    else arrows.foldLeft[Byte](0x00)((total, next) => (total | next.hex).toByte)
  }
}
