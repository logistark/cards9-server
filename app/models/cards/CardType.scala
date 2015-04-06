package models.cards

import java.net.URL

/**
 * Card type.
 *
 * @param id unique identifier of this card type
 * @param name card name
 * @param img url of the image for this card
 */
case class CardType(
  id: Int,
  name: String,
  img: URL)
