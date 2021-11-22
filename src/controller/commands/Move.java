package controller.commands;

import controller.ICommandController;
import dungeon.Dungeon;

/**
 * This class represents the move command in the dungeon game and moves the player based on the
 * provided direction.
 */
public class Move implements ICommandController {

  private String s;

  /**
   * Constructs a move command using the move direction value.
   *
   * @param val the direction to move in
   */
  public Move(String val) {
    this.s = val;
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
      sb.append(dungeon.nextMove(s));
    } catch (IllegalArgumentException e) {
      sb.append(e.getMessage());
    }
    return sb.toString();
  }
}
