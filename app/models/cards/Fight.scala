package models.cards

import cards9.game.GameTypes.{ CardId, FightId }

/**
 * Result of a card fight.
 *
 * @param attacker card attacking
 * @param defender card defending
 * @param atkPoints points of the attack
 * @param defPoints points of the defense
 * @param atkWinner true if attacker was the winner of the fight
 */
case class Fight(
  attacker: CardId,
  defender: CardId,
  atkPoints: Int,
  defPoints: Int,
  atkWinner: Boolean)
