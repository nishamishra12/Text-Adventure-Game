package dungeon;

/**
 * This class represents the Monster (Otyugh) living in the dungeon along with its health.
 */
public class Monster implements Creature {

  private int health;

  /**
   * Constructs an Otyugh with health 100.
   */
  public Monster() {
    this.health = 100;
  }

  /**
   * This is a copy constructor for Otyugh.
   *
   * @param monster the copy of monster object
   * @throws IllegalArgumentException when monster cannot be null
   */
  public Monster(Creature monster) throws IllegalArgumentException {
    if (monster == null) {
      throw new IllegalArgumentException("Monster cannot be null");
    }
    this.health = monster.getHealth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHealth() {
    return this.health;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hit() {
    this.health -= 50;
  }
}
