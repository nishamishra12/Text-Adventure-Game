package dungeon;

/**
 * This interface represents the creature (Otyugh) living in the dungeon along with its health.
 */
public interface Creature {

  /**
   * This method gives the health of the creature.
   *
   * @return the health of the creature
   */
  int getHealth();

  /**
   * This method hits the monster and reduces the health when it is shot by an arrow.
   */
  void hit();
}
