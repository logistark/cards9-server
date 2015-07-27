package cards9.game

import GameMessages._
import GameTypes._
import akka.actor.Actor
import models.players.Match
import models.boards.Color

class MatchActor(matchId: MatchId, creator: PlayerId) extends Actor {
  import context._

  var cards9Match: Option[Match] = None
  var currentPlayer: PlayerId = creator
  var colors: Map[PlayerId, Color] = Map.empty

  def receive: Receive = ???
}
