package models.players

import models.cards.Fight
import models.boards.{ Board, Red, Blue }
import Match.Score

case class Match(
  player1: Player, // Red player
  player2: Player, // Blue player
  board: Board,
  fights: List[Fight]) {

  def addFight(fight: Fight): Match =
    Match(
      player1,
      player2,
      board,
      fights :+ fight
    )

  /**
   * Get the current score of the match.
   *
   * @return a tuple with scores (Red, Blue)
   */
  def score: Score = (board.cardsOf(Red).length, board.cardsOf(Blue).length)
}

object Match {
  type Score = (Int, Int)
}