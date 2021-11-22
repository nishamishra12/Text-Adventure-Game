package controller;

import dungeon.Dungeon;

/**
 * This interface represents the different types of commands the player uses to navigate and play
 * the dungeon game.
 */
public interface ICommandController {

  /**
   * This method executes the different command given by the dungeon model.
   *
   * @param dungeon this parameter takes the dungeon model
   * @return the string representation of the executed command
   */
  String goCommand(Dungeon dungeon);
}
