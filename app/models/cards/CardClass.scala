package models.cards

import java.net.URL
import cards9.game.GameTypes.CardClassId

/**
 * Card class.
 *
 * @param id unique identifier of this card class
 * @param name card name
 * @param img url of the image for this card
 */
case class CardClass(
  id: CardClassId,
  name: String,
  img: URL)
