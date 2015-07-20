package models.cards

import java.net.URL

/**
 * Card class.
 *
 * @param id unique identifier of this card class
 * @param name card name
 * @param img url of the image for this card
 */
case class CardClass(
  id: Int,
  name: String,
  img: URL)
