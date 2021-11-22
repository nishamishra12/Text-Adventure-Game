package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.CommandController;
import controller.ICommandController;
import controller.IDungeonController;
import controller.commands.Move;
import controller.commands.PickArrow;
import controller.commands.PickTreasure;
import controller.commands.Shoot;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import randomizer.FixedRandomizer;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for the dungeon command controller to test all the implementation of the controller
 * using the dungeon model.
 */
public class CommandControllerTest {

  private IDungeonController commandController;
  private Dungeon dungeon;

  @Before
  public void setUp() throws Exception {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 2, new FixedRandomizer(3));
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {

    StringReader input = new StringReader("P A P T");
    Appendable gameLog = new FailingAppendable();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullOutput() {
    StringReader input = new StringReader("P A M W");

    commandController = new CommandController(input, null);
    commandController.play(dungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullInput() {
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(null, gameLog);
    commandController.play(dungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullModel() {

    StringReader input = new StringReader("P A M W");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullDungeonMove() {
    ICommandController cmd = new Move("N");
    cmd.goCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullDungeonShoot() {
    ICommandController cmd = new Shoot(1, "N");
    cmd.goCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullDungeonPickArrow() {
    ICommandController cmd = new PickArrow();
    cmd.goCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullDungeonPickTreasure() {
    ICommandController cmd = new PickTreasure();
    cmd.goCommand(null);
  }

  @Test
  public void testForWrongMPS() {
    StringReader input = new StringReader("T");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Invalid entry.\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForMoveM() {
    StringReader input = new StringReader("M S M E");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 5\n" +
            "\n" +
            "The player is in CAVE: 5\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "East\n" +
            "West\n" +
            "Smell: LOW\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 6\n" +
            "\n" +
            "The player is in CAVE: 6\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPickUpP() {
    StringReader input = new StringReader("P A P T");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "What? A/T\n" +
            "Arrow picked up\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 6 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "What? A/T\n" +
            "Treasure picked up\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has following treasures: DIAMOND: 4\n" +
            "Player has 6 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForShootS() {
    StringReader input = new StringReader("S 1 E S 1 E M E");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot the monster, monster is injured\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 2 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot the monster, monster has been killed\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 1 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: NONE\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 2\n" +
            "\n" +
            "The player is in CAVE: 2\n" +
            "Player has no treasure\n" +
            "Player has 1 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: NONE\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidMove() {
    StringReader input = new StringReader("M B");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Invalid move B\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidPick() {
    StringReader input = new StringReader("P V");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "What? A/T\n" +
            "\n" +
            "Invalid entry\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidShootDirection() {
    StringReader input = new StringReader("S 1 A");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "Invalid Direction\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidShootDistance() {
    StringReader input = new StringReader("S -1 N S 0 N");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "Distance should be at least 1\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "Distance should be at least 1\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPlayerShotIntoDarkness() {
    StringReader input = new StringReader("S 4 S");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "You shot an arrow into the darkness\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidTreasurePickUp() {
    StringReader input = new StringReader("M S P T");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 5\n" +
            "\n" +
            "The player is in CAVE: 5\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "East\n" +
            "West\n" +
            "Smell: LOW\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "What? A/T\n" +
            "No treasure present at the location\n" +
            "\n" +
            "The player is in CAVE: 5\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "East\n" +
            "West\n" +
            "Smell: LOW\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidArrowPickUp() {
    StringReader input = new StringReader("M S P A");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 5\n" +
            "\n" +
            "The player is in CAVE: 5\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "East\n" +
            "West\n" +
            "Smell: LOW\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "What? A/T\n" +
            "No arrow present at the location\n" +
            "\n" +
            "The player is in CAVE: 5\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "East\n" +
            "West\n" +
            "Smell: LOW\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPlayerDead() {
    StringReader input = new StringReader("M E");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "\n" +
            "Monster in cave! Chomp, chomp, chomp, player got eaten by an Otyugh!\n" +
            "Better luck next time\n", gameLog.toString());
  }

  @Test
  public void testForPlayerEscaped() {
    StringReader input = new StringReader("S 1 E M E");
    Appendable gameLog = new StringBuilder();

    dungeon = new DungeonImpl(5, 4, 2, 20
            , false, 2, new FixedRandomizer(2));

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Sapphire\n" +
            "Sapphire\n" +
            "There are 2 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot the monster, monster is injured\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 2 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Sapphire\n" +
            "Sapphire\n" +
            "There are 2 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player escaped successfully from an injured Otyugh, and moved to location 2\n" +
            "\n" +
            "The player is in CAVE: 2\n" +
            "Player has no treasure\n" +
            "Player has 2 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Sapphire\n" +
            "Sapphire\n" +
            "There are 2 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: NONE\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPlayerReachedEnd() {
    StringReader input = new StringReader("P A S 1 W S 1 W M W P A M S M S M W S 1 W S 1 W M W");
    Appendable gameLog = new StringBuilder();

    Dungeon dungeon = new DungeonImpl(4, 4, 2, 20
            , true, 5,
            new FixedRandomizer(0, 1, 2, 3));

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 0\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Diamond\n" +
            "There are 2 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "What? A/T\n" +
            "Arrow picked up\n" +
            "\n" +
            "The player is in CAVE: 0\n" +
            "Player has no treasure\n" +
            "Player has 5 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Diamond\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot the monster, monster is injured\n" +
            "\n" +
            "The player is in CAVE: 0\n" +
            "Player has no treasure\n" +
            "Player has 4 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Diamond\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot the monster, monster has been killed\n" +
            "\n" +
            "The player is in CAVE: 0\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Diamond\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 3\n" +
            "\n" +
            "The player is in CAVE: 3\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Diamond\n" +
            "There are 1 arrows in the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "South\n" +
            "East\n" +
            "Smell: LOW\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "What? A/T\n" +
            "Arrow picked up\n" +
            "\n" +
            "The player is in CAVE: 3\n" +
            "Player has no treasure\n" +
            "Player has 4 arrows\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Diamond\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "South\n" +
            "East\n" +
            "Smell: LOW\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 7\n" +
            "\n" +
            "The player is in TUNNEL: 7\n" +
            "Player has no treasure\n" +
            "Player has 4 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "South\n" +
            "Smell: NONE\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 11\n" +
            "\n" +
            "The player is in CAVE: 11\n" +
            "Player has no treasure\n" +
            "Player has 4 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "North\n" +
            "South\n" +
            "West\n" +
            "Smell: LOW\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 10\n" +
            "\n" +
            "The player is in TUNNEL: 10\n" +
            "Player has no treasure\n" +
            "Player has 4 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot the monster, monster is injured\n" +
            "\n" +
            "The player is in TUNNEL: 10\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot the monster, monster has been killed\n" +
            "\n" +
            "The player is in TUNNEL: 10\n" +
            "Player has no treasure\n" +
            "Player has 2 arrows\n" +
            "The cave has no treasures\n" +
            "There are no arrows at the current location\n" +
            "Next possible moves: \n" +
            "East\n" +
            "West\n" +
            "Smell: NONE\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "Player moved successfully to location 9\n" +
            "\n" +
            "Player has reached the destination location 9. Game Over!!", gameLog.toString());
  }

  @Test
  public void testForShootWhenNoArrows() {
    StringReader input = new StringReader("S 1 S S 1 W S 1 E S 1 E");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot an arrow into the darkness\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 2 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot an arrow into the darkness\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 1 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot the monster, monster is injured\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has no arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "You are out of arrows, explore to find more\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has no arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForQuit() {
    StringReader input = new StringReader("S 1 S q");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot an arrow into the darkness\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 2 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "Game Quit!", gameLog.toString());
  }

  @Test
  public void testForCommandAfterGameQuit() {
    StringReader input = new StringReader("S 1 S q M N");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "No. of caves (1-5)?\n" +
            "Where to?\n" +
            "\n" +
            "Player shot an arrow into the darkness\n" +
            "\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 2 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "Game Quit!", gameLog.toString());
  }


  @Test
  public void testForCommandAfterGameEnds() {
    StringReader input = new StringReader("M E P A");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n" +
            "The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "Diamond\n" +
            "There are 3 arrows in the current location\n" +
            "Next possible moves: \n" +
            "South\n" +
            "East\n" +
            "West\n" +
            "Smell: HIGH\n" +
            "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n" +
            "\n" +
            "Please select your next move. Enter N: North, Enter S: South, Enter E: East," +
            " Enter W: West\n" +
            "\n" +
            "Monster in cave! Chomp, chomp, chomp, player got eaten by an Otyugh!\n" +
            "Better luck next time\n", gameLog.toString());
  }

  @Test
  public void testMoveAllDirection() {
    StringReader input = new StringReader("S 1 S S 1 S M S M N M W M E");
    Appendable gameLog = new StringBuilder();

    Dungeon dungeon = new DungeonImpl(4, 4, 2, 20
            , false, 5,
            new FixedRandomizer(1));

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "The player is in CAVE: 1\n"
            + "Player has no treasure\n"
            + "Player has 3 arrows\n"
            + "Cave has following treasures -\n"
            + "Ruby\n"
            + "Ruby\n"
            + "There are 1 arrows in the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster is injured\n"
            + "\n"
            + "The player is in CAVE: 1\n"
            + "Player has no treasure\n"
            + "Player has 2 arrows\n"
            + "Cave has following treasures -\n"
            + "Ruby\n"
            + "Ruby\n"
            + "There are 1 arrows in the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster has been killed\n"
            + "\n"
            + "The player is in CAVE: 1\n"
            + "Player has no treasure\n"
            + "Player has 1 arrows\n"
            + "Cave has following treasures -\n"
            + "Ruby\n"
            + "Ruby\n"
            + "There are 1 arrows in the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East,"
            + " Enter W: West\n"
            + "Player moved successfully to location 5\n"
            + "\n"
            + "The player is in CAVE: 5\n"
            + "Player has no treasure\n"
            + "Player has 1 arrows\n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East,"
            + " Enter W: West\n"
            + "Player moved successfully to location 1\n"
            + "\n"
            + "The player is in CAVE: 1\n"
            + "Player has no treasure\n"
            + "Player has 1 arrows\n"
            + "Cave has following treasures -\n"
            + "Ruby\n"
            + "Ruby\n"
            + "There are 1 arrows in the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East,"
            + " Enter W: West\n"
            + "Player moved successfully to location 0\n"
            + "\n"
            + "The player is in TUNNEL: 0\n"
            + "Player has no treasure\n"
            + "Player has 1 arrows\n"
            + "The cave has no treasures\n"
            + "There are 1 arrows in the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "Smell: HIGH\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East,"
            + " Enter W: West\n"
            + "Player moved successfully to location 1\n"
            + "\n"
            + "The player is in CAVE: 1\n"
            + "Player has no treasure\n"
            + "Player has 1 arrows\n"
            + "Cave has following treasures -\n"
            + "Ruby\n"
            + "Ruby\n"
            + "There are 1 arrows in the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }
}