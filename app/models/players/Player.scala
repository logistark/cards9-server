package models.players

import cards9.game.GameTypes.{ PlayerId, CardId }

case class Player(
  id: PlayerId,
  cards: List[CardId])
