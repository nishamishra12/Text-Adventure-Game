package controller.commands;

import controller.ICommandController;
import dungeon.Dungeon;

/**
 * This class represents the shoot command in the dungeon game and shoots arrows in the specified
 * distance and direction to hit the Otyugh.
 */
public class Shoot implements ICommandController {

  private int distance;
  private String direction;

  /**
   * Constructs a shoot command object with the help of provided distance and direction.
   *
   * @param dist till which the arrow should travel
   * @param dir  in which the arrow should travel
   */
  public Shoot(int dist, String dir) {
    this.distance = dist;
    this.direction = dir;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String goCommand(Dungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Dungeon cannot be null");
    }
    StringBuilder sb = new StringBuilder();
    try {
      sb.append(dungeon.shootArrow(distance, direction));
    } catch (IllegalArgumentException a) {
      sb.append(a.getMessage());
    }
    return sb.toString();
  }
}
