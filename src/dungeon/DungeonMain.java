package dungeon;

import java.io.InputStreamReader;

import controller.CommandController;
import randomizer.ActualRandomizer;

/**
 * This a DungeonMain class which acts like a user input for the project.
 */
public class DungeonMain {

  /**
   * This is a main class which will be used to start the DungeonMain class.
   *
   * @param args Args can be provided as any
   */
  public static void main(String[] args) {

    Dungeon dungeon;
    try {
      if (args.length < 5) {
        throw new IllegalArgumentException("Invalid command line arguments given. "
                + "Please provide correct arguments");
      }
      int rows = Integer.parseInt(args[0]);
      int cols = Integer.parseInt(args[1]);
      int interConnectivity = Integer.parseInt(args[2]);
      int treasurePercent = Integer.parseInt(args[3]);
      boolean wrapping = Boolean.parseBoolean(args[4]);
      int monsterCount = Integer.parseInt(args[5]);

      dungeon = new DungeonImpl(rows, cols, interConnectivity, treasurePercent, wrapping,
              monsterCount, new ActualRandomizer());

      System.out.println("*********** Dungeon Created ***************");
      System.out.println(dungeon);
      System.out.println("*************** Game Begins ***************");
      System.out.println("Start Cave: " + dungeon.getStartCave().getId());
      System.out.println("End Cave: " + dungeon.getEndCave().getId());

      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      new CommandController(input, output).play(dungeon);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
