package models.players

import models.cards.Card

case class Player(
  id: Int,
  cards: List[Card])
