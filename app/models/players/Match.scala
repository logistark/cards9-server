package models.players

import models.cards.Fight
import models.boards.{ Board, Red, Blue }
import cards9.game.GameTypes.{ PlayerId, Score }

case class Match(
  player1: PlayerId, // Red player
  player2: PlayerId, // Blue player
  board: Board,
  fights: List[Fight] = Nil) {

  /**
   * Adds a new fight to the match.
   *
   * @param fight the new fight
   *
   * @return match with the fight added
   */
  def addFight(fight: Fight): Match = this.copy(fights = fights :+ fight)

  /**
   * Get the current score of the match.
   *
   * @return a tuple with scores (Red, Blue)
   */
  def score: Score = (board.cardsOf(Red).length, board.cardsOf(Blue).length)
}
