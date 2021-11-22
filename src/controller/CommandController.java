package controller;

import java.io.IOException;
import java.util.Scanner;

import controller.commands.Move;
import controller.commands.PickArrow;
import controller.commands.PickTreasure;
import controller.commands.Shoot;
import dungeon.Dungeon;

/**
 * This class represents the controller of the entire dungeon. It is used to play the dungeon
 * game by moving the player, picking arrows, treasure, and slaying the monsters. The game is over
 * when the player is eaten by the monster or wins by reaching the destination.
 */
public class CommandController implements IDungeonController {

  private final Appendable out;
  private final Scanner sc;

  /**
   * Constructs the output message and sets the input parameter.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public CommandController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    sc = new Scanner(in);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void play(Dungeon dungeon) throws IllegalArgumentException, IllegalStateException {

    if (dungeon == null) {
      throw new IllegalArgumentException("The Dungeon model cannot be null");
    }

    try {
      ICommandController cmd = null;
      while (!dungeon.hasReachedEnd() && dungeon.getPlayer().isAlive()) {
        try {
          out.append("\n" + dungeon.getPlayerDescription());
          out.append(dungeon.getLocationDescription());
          out.append(dungeon.getNextPossibleDescription());
          out.append("\nSmell: " + dungeon.checkSmell());
          out.append("\nWhat do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n");
          if (!sc.hasNext()) {
            break;
          }
          String in = sc.next();
          switch (in) {
            case "M":
            case "m":
              out.append("\nPlease select your next move.");
              out.append(" Enter N: North, Enter S: South, Enter E: East, Enter W: West\n");
              String input = sc.next();
              cmd = new Move(input);
              break;

            case "P":
            case "p":
              out.append("\nWhat? A/T\n");
              String pick = sc.next();
              if (pick.equalsIgnoreCase("A")) {
                cmd = new PickArrow();
              } else if (pick.equalsIgnoreCase("T")) {
                cmd = new PickTreasure();
              } else {
                out.append("\nInvalid entry");
              }
              break;

            case "S":
            case "s":
              out.append("\nNo. of caves (1-5)?\n");
              int dist = parseInt(sc.next());
              out.append("Where to?\n");
              String dir = sc.next();
              cmd = new Shoot(dist, dir);
              break;

            case "q":
            case "Q":
              out.append("Game Quit!");
              return;

            default:
              out.append("\nInvalid entry.");
              break;
          }
          if (cmd != null) {
            out.append(cmd.goCommand(dungeon)).append("\n");
            cmd = null;
          }
        } catch (IllegalArgumentException a) {
          out.append(a.getMessage());
        }
      }

      if (dungeon.hasReachedEnd()) {
        out.append("\nPlayer has reached the destination location "
                + dungeon.getPlayer().getCurrentLocation().getId() + ". Game Over!!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("\nAppend failed ", ioe);
    } catch (IllegalArgumentException ae) {
      throw new IllegalArgumentException(ae.getMessage());
    }
  }

  private int parseInt(String s) throws IllegalArgumentException {
    int a = 0;
    try {
     a = Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid input for dist");
    }
    return a;
  }
}
