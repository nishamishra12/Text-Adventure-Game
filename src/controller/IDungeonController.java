package controller;

import dungeon.Dungeon;

/**
 * This interface represents the controller of the entire dungeon. It is used to play the dungeon
 * game by moving the player, picking arrows, treasure, and slaying the monsters. The game is over
 * when the player is eaten by the monster or wins by reaching the destination.
 */
public interface IDungeonController {

  /**
   * This method plays the dungeon game with the help of user inputs and moves the player,
   * picks up treasures, arrows, and slays monster.
   *
   * @param dungeon this parameter takes the dungeon model
   * @throws IllegalArgumentException when the dungeon model is null
   * @throws IllegalStateException    when append to the output stream fails.
   */
  public void play(Dungeon dungeon) throws IllegalArgumentException, IllegalStateException;
}
