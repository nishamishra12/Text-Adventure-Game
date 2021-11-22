package controller.commands;

import controller.ICommandController;
import dungeon.Dungeon;

/**
 * This class represents the pick-up treasure command in the dungeon game and picks up treasures
 * from the current location.
 */
public class PickTreasure implements ICommandController {

  /**
   * {@inheritDoc}
   */
  @Override
  public String goCommand(Dungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Dungeon cannot be null");
    }
    return dungeon.pickTreasure();
  }
}
