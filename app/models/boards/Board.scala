package models.boards

import models.cards.Card
import Array._
import scala.util.Random
import Board.Hand
import models.cards.Arrow
import models.cards.Arrow.Coords
import services.settings.GameSettings

/**
 * Possible colors of a card.
 */
sealed trait Color { def flip: Color }
case object Red extends Color { def flip: Color = Blue }
case object Blue extends Color { def flip: Color = Red }

/**
 * Possible states of a square.
 */
sealed trait Square
case class Occupied(card: Card, color: Color) extends Square { override def toString = card.id.toString + "," + color }
case object Block extends Square { override def toString = "B" }
case object Free extends Square { override def toString = "F" }

case class Board(
  grid: Array[Array[Square]],
  redPlayer: Hand,
  bluePlayer: Hand)(implicit gameSettings: GameSettings) {

  /**
   * Adds a new occupied square to the board.
   *
   * @param i row
   * @param j column
   * @param occupied card and color
   *
   * @return a new board with the occupied square
   */
  def add(i: Int, j: Int, occupied: Occupied, player: Color): Board = {
    // Target square position must be free
    require(Board.areValidCoords(i, j))
    require(grid(i)(j) == Free)

    // Occupied card must be part of the hand of the selected player
    require(
      (redPlayer.contains(occupied.card) && player == Red) ||
        (bluePlayer.contains(occupied.card) && player == Blue)
    )

    grid(i)(j) = occupied

    val (newRed, newBlue) = player match {
      case Red  => (redPlayer - occupied.card, bluePlayer)
      case Blue => (redPlayer, bluePlayer - occupied.card)
    }

    Board(grid.clone, newRed, newBlue)
  }

  /**
   * Flips the card on the specified position.
   *
   * @param i row
   * @param j column
   *
   * @return a new board with the card flipped
   */
  def flip(i: Int, j: Int): Board = {
    require(Board.areValidCoords(i, j))
    require(grid(i)(j).isInstanceOf[Occupied])

    grid(i)(j) match {
      case Occupied(card, color) => grid(i)(j) = Occupied(card, color.flip)
      case Block | Free          => // ERROR
    }

    this.copy(grid = grid.clone)
  }

  /**
   * Flips all the cards on the specified positions.
   *
   * @param i row
   * @param j column
   *
   * @return a new board with the card flipped
   */
  def flipAll(coords: List[(Int, Int)]): Board = {
    coords
      .filter(coords => Board.areValidCoords(coords._1, coords._2))
      .map(coords => flip(coords._1, coords._2))
    this.copy(grid = grid.clone)
  }

  /**
   * Get the opponents for a card on the given coords.
   *
   * @param i row
   * @param j column
   *
   * @return a list of possible opponents and the direction of the attack
   */
  def opponents(i: Int, j: Int): List[(Card, Arrow)] = {
    require(Board.areValidCoords(i, j))
    require(grid(i)(j).isInstanceOf[Occupied])

    grid(i)(j) match {
      case Occupied(card, color) =>

        card.arrows
          .map(arrow => (arrow, Arrow.arrowCoords(i, j, arrow)))
          .filter { case (arrow: Arrow, coords: Coords) => Board.areValidCoords(coords._1, coords._2) }
          .collect {
            case (arrow, (x, y)) =>
              grid(x)(y) match {
                case Occupied(enemyCard, enemyColor) if (color != enemyColor) =>
                  (enemyCard, arrow)
              }
          }

      case Block | Free => List.empty // Maybe error
    }
  }

  /**
   * Retrieve all the cards from the board of the given color.
   *
   * @param color color of the cards to be retrieved
   *
   * @return a list with all the cards on the board with that color
   */
  def cardsOf(color: Color): List[Card] = {
    (grid flatMap { row =>
      row.collect {
        case Occupied(card, sqColor) if (sqColor == color) => card
      }
    }).toList

  }
  override def toString = grid.map(row => row.mkString(" ")).mkString("\n")
}

object Board {
  type Hand = Set[Card]

  // Check against 
  private def areValidCoords(i: Int, j: Int)(implicit gameSettings: GameSettings): Boolean = {
    (i >= 0 && i < gameSettings.BOARD_SIZE && j >= 0 && j < gameSettings.BOARD_SIZE)
  }

  /**
   * Creates a fresh free board with some random blocks on it and the red and
   * blue player hands of cards.
   */
  def random(redPlayer: Hand, bluePlayer: Hand)(implicit gameSettings: GameSettings): Board = {
    val randomBlocks: Int = Random.nextInt(gameSettings.BOARD_MAX_BLOCKS + 1)

    // All possible coords of the grid
    val coords: Array[(Int, Int)] = Array((for {
      i <- (0 until gameSettings.BOARD_SIZE)
      j <- (0 until gameSettings.BOARD_SIZE)
    } yield (i, j)): _*)

    // Fisher-Yates
    for {
      i <- (coords.length - 1 to 1 by -1)
      j = Random.nextInt(i + 1)
    } {
      val aux = coords(i)
      coords(i) = coords(j)
      coords(j) = aux
    }

    // Create a new grid of Free squares and then throw in the random blocks
    val grid: Array[Array[Square]] = Array.fill(gameSettings.BOARD_SIZE, gameSettings.BOARD_SIZE)(Free)

    coords.take(randomBlocks).map {
      case (i, j) =>
        grid(i)(j) = Block
    }

    Board(grid, redPlayer, bluePlayer)
  }
}
