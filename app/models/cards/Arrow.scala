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
sealed trait Arrow {
  def hex: Byte
  def opposite: Arrow
}

case object N extends Arrow { val hex: Byte = 0x80.toByte; val opposite: Arrow = S }
case object NE extends Arrow { val hex: Byte = 0x40; val opposite: Arrow = SW }
case object E extends Arrow { val hex: Byte = 0x20; val opposite: Arrow = W }
case object SE extends Arrow { val hex: Byte = 0x10; val opposite: Arrow = NW }
case object S extends Arrow { val hex: Byte = 0x08; val opposite: Arrow = N }
case object SW extends Arrow { val hex: Byte = 0x04; val opposite: Arrow = NE }
case object W extends Arrow { val hex: Byte = 0x02; val opposite: Arrow = E }
case object NW extends Arrow { val hex: Byte = 0x01; val opposite: Arrow = SE }

object Arrow {
  type Coords = (Int, Int)

  val MAX_ARROWS = 8
  val allArrows: List[Arrow] = List(N, NE, E, SE, S, SW, W, NW)

  /**
   * Get the coordinates of the arrow, given the center.
   *
   * @param i row of the center
   * @param j column of the center
   * @param arrow get coords for this arrow
   *
   * @return a tuple with the coordinates of the arrow
   */
  def arrowCoords(i: Int, j: Int, arrow: Arrow): Coords = {
    arrow match {
      case N  => (i - 1, j)
      case NE => (i - 1, j + 1)
      case E  => (i, j + 1)
      case SE => (i - 1, j + 1)
      case S  => (i - 1, j)
      case SW => (i - 1, j - 1)
      case W  => (i, j - 1)
      case NW => (i - 1, j - 1)
    }
  }

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
