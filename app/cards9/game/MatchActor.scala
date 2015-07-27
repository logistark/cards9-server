package cards9.game

import GameMessages._
import GameTypes._
import akka.actor.Actor
import models.players.Match

class MatchActor(matchId: MatchId, creator: PlayerId) extends Actor {
  import context._

  var cards9Match: Option[Match] = None
  var currentPlayer: PlayerId = creator

  def receive: Receive = ???
}
