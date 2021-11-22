package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import dungeon.LocationType;
import dungeon.SmellType;
import dungeon.Treasure;
import randomizer.FixedRandomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test to check all the implementation of the dungeon class and its mnethods.
 */
public class DungeonImplTest {

  private Dungeon dungeon;
  private Dungeon dungeonW;

  @Before
  public void setUp() {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 5, new FixedRandomizer(2));
    dungeonW = new DungeonImpl(5, 7, 4, 20,
            true, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRows() {
    new DungeonImpl(-1, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidColumns() {
    new DungeonImpl(5, -1, 2, 20,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterConnectivity() {
    new DungeonImpl(5, 4, -1, 20,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void largeInterConnectivity() {
    new DungeonImpl(5, 4, 55, 20,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighTreasurePercent() {
    new DungeonImpl(5, 4, 55, 110,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowTreasurePercent() {
    new DungeonImpl(5, 4, 55, -10,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowMonsterCount() {
    new DungeonImpl(5, 4, 55, -10,
            false, -1, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighMonsterCount() {
    new DungeonImpl(5, 4, 55, -10,
            false, 20, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullRandomizer() {
    new DungeonImpl(5, 4, 55, 20,
            false, 5, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void smallDungeon() {
    new DungeonImpl(2, 2, 1, 20,
            false, 5, new FixedRandomizer(1));
  }

  @Test
  public void playerReachedEndCave() {
    dungeon = new DungeonImpl(4, 4, 4, 20,
            false, 5, new FixedRandomizer(2));

    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    dungeon.nextMove("S");
    dungeon.nextMove("S");
    dungeon.nextMove("W");
    dungeon.nextMove("S");
    dungeon.nextMove("E");
    dungeon.nextMove("E");
    dungeon.nextMove("E");
    assertEquals(dungeon.getEndCave().getId(), dungeon.getPlayer().getCurrentLocation().getId());
    assertTrue(dungeon.hasReachedEnd());
  }

  @Test
  public void testForTunnel2Entrance() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    //test for non wrapping dungeon
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.TUNNEL) {
        assertTrue(l.getNeighbors().size() == 2);
      }
    }

    dungeonW = new DungeonImpl(5, 4, 2, 20,
            true, 5, new FixedRandomizer(2, 3, 4));
    //test for wrapping dungeon
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.TUNNEL) {
        assertTrue(l.getNeighbors().size() == 2);
      }
    }
  }

  @Test
  public void testForCaveEntrance() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    //test for non wrapping dungeon
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        assertTrue(l.getNeighbors().size() == 1 || l.getNeighbors().size() == 3
                || l.getNeighbors().size() == 4);
      }
    }

    dungeonW = new DungeonImpl(5, 4, 2, 20,
            true, 5, new FixedRandomizer(2, 3, 4));
    //test for wrapping dungeon
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        assertTrue(l.getNeighbors().size() == 1 || l.getNeighbors().size() == 3
                || l.getNeighbors().size() == 4);
      }
    }
  }

  @Test
  public void testForConnectivityNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    for (Location l : dungeon.getLocationList()) {
      assertTrue(dungeon.bfs(l).size() == this.dungeon.getLocationList().size());
    }
  }

  @Test
  public void testForConnectivityWrapping() {
    dungeonW = new DungeonImpl(5, 4, 4, 20,
            true, 5, new FixedRandomizer(2, 3, 4));
    for (Location l : dungeonW.getLocationList()) {
      assertTrue(dungeonW.bfs(l).size() == this.dungeonW.getLocationList().size());
    }
  }

  @Test
  public void testTreasurePercentNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    //at least 20% of the caves will have treasure from the total cave list;
    int countOfCavesWithTreasure = 0;
    for (Location l : onlyCaveList) {
      if (l.getTreasureList().size() > 0) {
        countOfCavesWithTreasure++;
      }
    }
    assertTrue((countOfCavesWithTreasure * 100) / onlyCaveList.size() >= 20);
  }

  @Test
  public void testTreasurePercentWrapping() {
    dungeonW = new DungeonImpl(5, 4, 2, 20,
            true, 5, new FixedRandomizer(2));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    //at least 20% of the caves will have treasure from the total cave list;
    int countOfCavesWithTreasure = 0;
    for (Location l : onlyCaveList) {
      if (l.getTreasureList().size() > 0) {
        countOfCavesWithTreasure++;
      }
    }
    assertTrue((countOfCavesWithTreasure * 100) / onlyCaveList.size() >= 20);
  }

  @Test
  public void testTreasurePercentExactNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 1, 20,
            false, 5, new FixedRandomizer(0));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    //exact 20% of the caves will have treasure from the total cave list;
    int countOfCavesWithTreasure = 0;
    for (Location l : onlyCaveList) {
      if (l.getTreasureList().size() > 0) {
        countOfCavesWithTreasure++;
      }
    }
    assertEquals(20, (countOfCavesWithTreasure * 100) / onlyCaveList.size());
  }

  @Test
  public void testTreasurePercentExactWrapping() {
    dungeonW = new DungeonImpl(5, 4, 1, 20,
            true, 5, new FixedRandomizer(0));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    //exact 20% of the caves will have treasure from the total cave list;
    int countOfCavesWithTreasure = 0;
    for (Location l : onlyCaveList) {
      if (l.getTreasureList().size() > 0) {
        countOfCavesWithTreasure++;
      }
    }
    assertEquals(20, (countOfCavesWithTreasure * 100) / onlyCaveList.size());
  }

  /**
   * Test to check if a cave can have more than one treasure.
   */
  @Test
  public void treasureInCave() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    assertTrue(onlyCaveList.get(0).getTreasureList().size() > 1);

    //wrapping dungeon
    dungeonW = new DungeonImpl(5, 4, 1, 20,
            true, 5, new FixedRandomizer(0));
    onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    assertTrue(onlyCaveList.get(0).getTreasureList().size() > 1);
  }

  /**
   * Test to check that tunnels do not have treasures.
   */
  @Test
  public void tunnelNoTreasure() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    //non-wrapping dungeon
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.TUNNEL) {
        assertEquals(0, l.getTreasureList().size());
      }
    }

    //wrapping dungeon
    dungeonW = new DungeonImpl(5, 4, 1, 20,
            true, 5, new FixedRandomizer(0));
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.TUNNEL) {
        assertEquals(0, l.getTreasureList().size());
      }
    }
  }

  @Test
  public void nextMoveNonWrapping() {

    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    //check current cave
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(2, dungeon.getLocationList().get(1).getNeighbors().get(Direction.EAST).getId());
    assertEquals(5, dungeon.getLocationList().get(1).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(0, dungeon.getLocationList().get(1).getNeighbors().get(Direction.WEST).getId());

    dungeon.nextMove("S");
    assertEquals(5, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(4, dungeon.getLocationList().get(5).getNeighbors().get(Direction.WEST).getId());
    assertEquals(6, dungeon.getLocationList().get(5).getNeighbors().get(Direction.EAST).getId());
    assertEquals(1, dungeon.getLocationList().get(5).getNeighbors().get(Direction.NORTH).getId());

    dungeon.nextMove("E");
    assertEquals(6, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(2, dungeon.getLocationList().get(6).getNeighbors().get(Direction.NORTH).getId());
    assertEquals(7, dungeon.getLocationList().get(6).getNeighbors().get(Direction.EAST).getId());
    assertEquals(5, dungeon.getLocationList().get(6).getNeighbors().get(Direction.WEST).getId());

    dungeon.nextMove("N");
    assertEquals(2, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(1, dungeon.getLocationList().get(2).getNeighbors().get(Direction.WEST).getId());
    assertEquals(6, dungeon.getLocationList().get(2).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(3, dungeon.getLocationList().get(2).getNeighbors().get(Direction.EAST).getId());

    dungeon.nextMove("W");
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
  }

  @Test
  public void nextMoveWrapping() {
    dungeonW = new DungeonImpl(5, 4, 4, 20,
            true, 5, new FixedRandomizer(2, 3, 4));
    //check current cave
    assertEquals(1, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(0, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.WEST).getId());

    //move to the west neighbor
    dungeonW.nextMove("W");
    assertEquals(0, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(1, dungeonW.getLocationList().get(0).getNeighbors().get(Direction.EAST).getId());
    assertEquals(4, dungeonW.getLocationList().get(0).getNeighbors().get(Direction.SOUTH).getId());

    //move to the south
    dungeonW.nextMove("S");
    assertEquals(4, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(0, dungeonW.getLocationList().get(4).getNeighbors().get(Direction.NORTH).getId());
    assertEquals(5, dungeonW.getLocationList().get(4).getNeighbors().get(Direction.EAST).getId());
    assertEquals(7, dungeonW.getLocationList().get(4).getNeighbors().get(Direction.WEST).getId());

    //move to the east
    dungeonW.nextMove("E");
    assertEquals(5, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(4, dungeonW.getLocationList().get(5).getNeighbors().get(Direction.WEST).getId());
    assertEquals(9, dungeonW.getLocationList().get(5).getNeighbors().get(Direction.SOUTH).getId());

    dungeonW.nextMove("S");
    assertEquals(9, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(5, dungeonW.getLocationList().get(9).getNeighbors().get(Direction.NORTH).getId());
    assertEquals(13, dungeonW.getLocationList().get(9).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(8, dungeonW.getLocationList().get(9).getNeighbors().get(Direction.WEST).getId());

    //move to the north
    dungeonW.nextMove("N");
    assertEquals(5, dungeonW.getPlayer().getCurrentLocation().getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    //current location of the player - cave 1
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    //enter an invalid direction
    dungeon.nextMove("T");
  }

  @Test
  public void wrongNeighborMove() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    //current location of the player - cave 1
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    //enter an invalid direction
    assertEquals("\n" + "Move not possible N",
            dungeon.nextMove("N"));
  }

  @Test
  public void dumpDungeonWrapping() {
    Dungeon dungeonW = new DungeonImpl(4, 4, 2, 20
            , true, 5,
            new FixedRandomizer(0, 1, 2, 3));
    assertEquals("\n" +
            "CAVE: 0\n" +
            "-->SOUTH Neighbor: CAVE: 4\n" +
            "-->EAST Neighbor: TUNNEL: 1\n" +
            "-->WEST Neighbor: CAVE: 3\n" +
            "Arrow Count: 2\n" +
            "\n" +
            "TUNNEL: 1\n" +
            "-->EAST Neighbor: CAVE: 2\n" +
            "-->WEST Neighbor: CAVE: 0\n" +
            "Arrow Count: 3\n" +
            "\n" +
            "CAVE: 2\n" +
            "-->WEST Neighbor: TUNNEL: 1\n" +
            "###Monster###\n" +
            "\n" +
            "CAVE: 3\n" +
            "-->NORTH Neighbor: CAVE: 15\n" +
            "-->SOUTH Neighbor: TUNNEL: 7\n" +
            "-->EAST Neighbor: CAVE: 0\n" +
            "###Monster###\n" +
            "Arrow Count: 1\n" +
            "\n" +
            "CAVE: 4\n" +
            "-->NORTH Neighbor: CAVE: 0\n" +
            "-->SOUTH Neighbor: TUNNEL: 8\n" +
            "-->EAST Neighbor: TUNNEL: 5\n" +
            "###Monster###\n" +
            "\n" +
            "TUNNEL: 5\n" +
            "-->EAST Neighbor: CAVE: 6\n" +
            "-->WEST Neighbor: CAVE: 4\n" +
            "\n" +
            "CAVE: 6\n" +
            "-->WEST Neighbor: TUNNEL: 5\n" +
            "###Monster###\n" +
            "\n" +
            "TUNNEL: 7\n" +
            "-->NORTH Neighbor: CAVE: 3\n" +
            "-->SOUTH Neighbor: CAVE: 11\n" +
            "\n" +
            "TUNNEL: 8\n" +
            "-->NORTH Neighbor: CAVE: 4\n" +
            "-->SOUTH Neighbor: CAVE: 12\n" +
            "\n" +
            "CAVE: 9\n" +
            "-->EAST Neighbor: TUNNEL: 10\n" +
            "###Monster###\n" +
            "\n" +
            "TUNNEL: 10\n" +
            "-->EAST Neighbor: CAVE: 11\n" +
            "-->WEST Neighbor: CAVE: 9\n" +
            "\n" +
            "CAVE: 11\n" +
            "-->NORTH Neighbor: TUNNEL: 7\n" +
            "-->SOUTH Neighbor: CAVE: 15\n" +
            "-->WEST Neighbor: TUNNEL: 10\n" +
            "\n" +
            "CAVE: 12\n" +
            "-->NORTH Neighbor: TUNNEL: 8\n" +
            "-->EAST Neighbor: CAVE: 13\n" +
            "-->WEST Neighbor: CAVE: 15\n" +
            "\n" +
            "CAVE: 13\n" +
            "-->WEST Neighbor: CAVE: 12\n" +
            "\n" +
            "CAVE: 14\n" +
            "-->EAST Neighbor: CAVE: 15\n" +
            "\n" +
            "CAVE: 15\n" +
            "-->NORTH Neighbor: CAVE: 11\n" +
            "-->SOUTH Neighbor: CAVE: 3\n" +
            "-->EAST Neighbor: CAVE: 12\n" +
            "-->WEST Neighbor: CAVE: 14\n", dungeonW.toString());
  }

  @Test
  public void pickTreasure() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    //current cave location of the player has treasure and treasure list of player is empty
    assertEquals(3, dungeon.getPlayer().getCurrentLocation().getTreasureList().size());
    assertEquals(0, dungeon.getPlayer().getTreasureList().size());
    //pickup treasure
    dungeon.pickTreasure();
    //treasure emptied from the current cave and added to the player's treasure list
    assertEquals(0, dungeon.getPlayer().getCurrentLocation().getTreasureList().size());
    assertEquals(3, dungeon.getPlayer().getTreasureList().size());
    List<Treasure> tList = new ArrayList<>();
    tList.add(Treasure.SAPPHIRE);
    tList.add(Treasure.SAPPHIRE);
    tList.add(Treasure.SAPPHIRE);
    assertEquals(tList, dungeon.getPlayer().getTreasureList());
  }

  @Test
  public void getLocationDescriptionNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    assertEquals("\n" +
            "Cave has following treasures -\n" +
            "Sapphire\n" +
            "Sapphire\n" +
            "Sapphire\n" +
            "There are 2 arrows in the current location", dungeon.getLocationDescription());
  }

  @Test
  public void getLocationDescriptionWrapping() {
    dungeonW = new DungeonImpl(4, 4, 4, 20,
            true, 5, new FixedRandomizer(2, 3, 4));
    assertEquals("\n" +
            "Cave has following treasures -\n" +
            "Diamond\n" +
            "Sapphire\n" +
            "Ruby\n" +
            "There are 3 arrows in the current location", dungeonW.getLocationDescription());
  }

  @Test
  public void getTreasureDescriptionNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    assertEquals("\n" +
                    "Cave has following treasures -\n" +
                    "Sapphire\n" +
                    "Sapphire\n" +
                    "Sapphire\n" +
                    "There are 2 arrows in the current location",
            dungeon.getLocationDescription());
  }

  @Test
  public void getTreasureDescriptionWrapping() {
    dungeonW = new DungeonImpl(5, 4, 4, 20,
            true, 5, new FixedRandomizer(2, 3, 4));
    assertEquals("\n" +
                    "Cave has following treasures -\n" +
                    "Sapphire\n" +
                    "Ruby\n" +
                    "Diamond\n" +
                    "Sapphire\n" +
                    "There are 2 arrows in the current location",
            dungeonW.getLocationDescription());
  }

  @Test
  public void getPlayerDescriptionNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    assertEquals("The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows", dungeon.getPlayerDescription());
    dungeon.pickTreasure();
    assertEquals("The player is in CAVE: 1\n" +
            "Player has following treasures: SAPPHIRE: 3\n" +
            "Player has 3 arrows", dungeon.getPlayerDescription());
  }

  @Test
  public void getPlayerDescriptionWrapping() {
    dungeonW = new DungeonImpl(5, 4, 4, 20,
            true, 5, new FixedRandomizer(2, 3, 4));
    assertEquals("The player is in CAVE: 1\n" +
            "Player has no treasure\n" +
            "Player has 3 arrows", dungeonW.getPlayerDescription());
    dungeonW.pickTreasure();
    assertEquals("The player is in CAVE: 1\n" +
                    "Player has following treasures: DIAMOND: 1 RUBY: 1 SAPPHIRE: 2\n" +
                    "Player has 3 arrows",
            dungeonW.getPlayerDescription());
  }

  @Test
  public void getLocationList() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    dungeonW = new DungeonImpl(5, 4, 4, 20,
            true, 5, new FixedRandomizer(2, 3, 4));
    //get list of all locations in the dungeon
    assertEquals(20, dungeon.getLocationList().size());
    assertEquals(20, dungeonW.getLocationList().size());
  }

  @Test
  public void getPlayer() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    assertEquals("John", dungeon.getPlayer().getName());
  }

  @Test
  public void getStartCave() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    assertEquals(1, dungeon.getStartCave().getId());
  }

  @Test
  public void getEndCave() {
    dungeon = new DungeonImpl(3, 4, 2, 20,
            true, 5, new FixedRandomizer(0));
    assertEquals(11, dungeon.getEndCave().getId());
  }

  @Test
  public void startEndAreCave() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    assertEquals(LocationType.CAVE, dungeon.getStartCave().getLocationType());
    assertEquals(LocationType.CAVE, dungeon.getEndCave().getLocationType());
  }

  @Test
  public void testStartEndMinimumFive() {

    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));

    Map<Location, Integer> bfsLevel = dungeon.bfs(dungeon.getStartCave());

    int distance = 0;
    for (Map.Entry<Location, Integer> levels : bfsLevel.entrySet()) {
      if (dungeon.getEndCave().getId() == levels.getKey().getId()) {
        distance = levels.getValue();
      }
    }
    assertTrue(distance >= 5);
  }

  @Test
  public void playerStartsAtStart() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 5, new FixedRandomizer(2));
    assertEquals(dungeon.getStartCave().getId(), dungeon.getPlayer().getCurrentLocation().getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForBFSNullStart() {
    dungeon.bfs(null);
  }

  //***new section

  @Test
  public void testMonsterCount() {
    int monsterCount = 0;
    for (int i = 0; i < dungeon.getLocationList().size(); i++) {
      if (dungeon.getLocationList().get(i).hasMonster()) {
        monsterCount++;
      }
    }
    assertEquals(5, monsterCount);
  }

  @Test
  public void testMonsterOnlyInCave() {
    for (int i = 0; i < dungeon.getLocationList().size(); i++) {
      if (dungeon.getLocationList().get(i).hasMonster()) {
        assertEquals(LocationType.CAVE, dungeon.getLocationList().get(i).getLocationType());
      }
    }
  }

  @Test
  public void testForArrowFrequency() {
    dungeon = new DungeonImpl(5, 4, 1, 20,
            false, 5, new FixedRandomizer(10));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }

    int treasureCount = 0;
    int arrowCount = 0;
    for (Location l : dungeon.getLocationList()) {
      if (l.getTreasureList().size() > 0) {
        treasureCount++;
      }
      if (l.getArrow() > 0) {
        arrowCount++;
      }
    }
    assertEquals((treasureCount * 100) / onlyCaveList.size(),
            (arrowCount * 100) / dungeon.getLocationList().size());
  }

  @Test
  public void testForPickUpArrowCave() {
    //no of arrows with player
    assertEquals(LocationType.CAVE, dungeon.getPlayer().getCurrentLocation().getLocationType());
    assertEquals(3, dungeon.getPlayer().getArrowCount());
    //arrows in the current cave
    assertEquals(2, dungeon.getPlayer().getCurrentLocation().getArrow());

    dungeon.pickArrow();

    //no of arrows with player
    assertEquals(5, dungeon.getPlayer().getArrowCount());
    //arrows in the current cave
    assertEquals(0, dungeon.getPlayer().getCurrentLocation().getArrow());
  }

  @Test
  public void testForPickUpArrowTunnel() {
    //current cave
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    assertEquals(3, dungeon.getPlayer().getArrowCount());

    //move player to tunnel
    dungeon.nextMove("W");

    //arrows in the current tunnel
    assertEquals(LocationType.TUNNEL, dungeon.getPlayer().getCurrentLocation().getLocationType());
    assertEquals(2, dungeon.getPlayer().getCurrentLocation().getArrow());

    dungeon.pickArrow();

    //no of arrows with player
    assertEquals(5, dungeon.getPlayer().getArrowCount());
    //arrows in the current cave
    assertEquals(0, dungeon.getPlayer().getCurrentLocation().getArrow());
  }

  @Test
  public void testForShoot() {
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    //monster shot and injured
    assertEquals("\n" + "Player shot the monster, monster is injured",
            dungeon.shootArrow(1, "E"));
    //monster shot and killed
    assertEquals("\n" + "Player shot the monster, monster has been killed",
            dungeon.shootArrow(1, "E"));

    //monster dead, so shot into darkness
    assertEquals("\n" + "Player shot an arrow into the darkness",
            dungeon.shootArrow(1, "E"));

    //move player to the East cave, monster health in the same cave will be 0
    dungeon.nextMove("E");
    assertEquals(0, dungeon.getPlayer().getCurrentLocation().getMonster().getHealth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForInvalidShootDirection() {
    dungeon.shootArrow(1, "A");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForInvalidShootDistance() {
    dungeon.shootArrow(-1, "N");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForShootZeroDistance() {
    dungeon.shootArrow(0, "N");
  }

  @Test
  public void testForOtyughHighSmell() {
    Dungeon d = new DungeonImpl(5, 4, 2, 20,
            false, 2, new FixedRandomizer(2));
    //player at location 1, location 2 has monster thus the smell should be HIGH
    assertEquals(1, d.getPlayer().getCurrentLocation().getId());
    assertTrue(d.getLocationList().get(2).hasMonster());
    assertEquals(SmellType.HIGH, d.checkSmell());
  }

  @Test
  public void testForOtyughLowSmell() {
    Dungeon d = new DungeonImpl(5, 4, 2, 20,
            false, 2, new FixedRandomizer(2));
    //player at location 1, location 2 has monster thus the smell should be HIGH
    assertEquals(1, d.getPlayer().getCurrentLocation().getId());
    assertTrue(d.getLocationList().get(2).hasMonster());
    assertEquals(SmellType.HIGH, d.checkSmell());

    //move monster to position 0
    d.nextMove("W");
    assertEquals(0, d.getPlayer().getCurrentLocation().getId());
    assertEquals(SmellType.LOW, d.checkSmell());
  }

  @Test
  public void testForOtyughNoneSmell() {
    Dungeon d = new DungeonImpl(5, 4, 2, 20,
            false, 2, new FixedRandomizer(2));
    //player at location 1, nearby only location 2 has monster thus the smell should be HIGH
    assertEquals(1, d.getPlayer().getCurrentLocation().getId());
    assertEquals(100, d.getLocationList().get(2).getMonster().getHealth());
    assertEquals(SmellType.HIGH, d.checkSmell());

    //kill the monster
    d.shootArrow(1, "E");
    d.shootArrow(1, "E");
    //health is 0
    assertEquals(0, d.getLocationList().get(2).getMonster().getHealth());
    //smell should be none
    assertEquals(SmellType.NONE, d.checkSmell());
  }


  @Test
  public void testForOtyughKill() {
    //monster in cave 2
    assertEquals(100, dungeon.getLocationList().get(2).getMonster().getHealth());

    //monster injured
    assertEquals("\n" + "Player shot the monster, monster is injured",
            dungeon.shootArrow(1, "E"));
    assertEquals(50, dungeon.getLocationList().get(2).getMonster().getHealth());
    //monster killed
    assertEquals("\n" + "Player shot the monster, monster has been killed",
            dungeon.shootArrow(1, "E"));
    assertEquals(0, dungeon.getLocationList().get(2).getMonster().getHealth());
  }

  @Test
  public void testForStartNoOtyughEndOtyugh() {
    //Otyugh should be not be there in start cave
    assertEquals(false, dungeon.getStartCave().hasMonster());
    //Otyugh should be there in start cave
    assertEquals(true, dungeon.getEndCave().hasMonster());
  }

  @Test
  public void testForOtyughOnlyInCave() {
    for (int i = 0; i < dungeon.getLocationList().size(); i++) {
      if (dungeon.getLocationList().get(i).hasMonster()) {
        assertEquals(LocationType.CAVE, dungeon.getLocationList().get(i).getLocationType());
      }
    }
  }

  @Test
  public void testForOtherItems() {
    //a cave can have monster, treasure, and arrow at the same time
    assertTrue(dungeon.getLocationList().get(2).getArrow() > 0);
    assertTrue(dungeon.getLocationList().get(2).hasMonster());
    assertTrue(dungeon.getLocationList().get(2).getTreasureList().size() > 0);
  }

  @Test
  public void testForPlayerDead() {
    assertEquals(true, dungeon.getPlayer().isAlive());
    assertEquals(100, dungeon.getLocationList().get(2).getMonster().getHealth());

    //move player to cave 2
    dungeon.nextMove("E");
    assertEquals(false, dungeon.getPlayer().isAlive());
  }

  @Test
  public void testForPlayerStartsWith3Arrow() {
    assertEquals(3, dungeon.getPlayer().getArrowCount());
  }

  @Test
  public void testPlayerEscapedIfOtyughInjured() {
    assertEquals(true, dungeon.getPlayer().isAlive());
    assertEquals(100, dungeon.getLocationList().get(2).getMonster().getHealth());
    dungeon.shootArrow(1, "E");
    assertEquals(50, dungeon.getLocationList().get(2).getMonster().getHealth());
    assertEquals(true, dungeon.getPlayer().isAlive());
  }

  @Test
  public void testOtyughHealth() {
    assertEquals(100, dungeon.getLocationList().get(2).getMonster().getHealth());
    dungeon.shootArrow(1, "E");
    assertEquals(50, dungeon.getLocationList().get(2).getMonster().getHealth());
    dungeon.shootArrow(1, "E");
    assertEquals(0, dungeon.getLocationList().get(2).getMonster().getHealth());
  }

  @Test
  public void testMultipleOtyugh() {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 15, new FixedRandomizer(2));
    int count = 0;

    Map<Location, Integer> monsterLevel = dungeon.bfs(dungeon.getStartCave());
    //check count of Otyugh's at a distance of 2 apart
    for (Map.Entry<Location, Integer> levels : monsterLevel.entrySet()) {
      if (levels.getValue() == 2) {
        count++;
      }
    }
    //multiple otyugh's, so smell will be HIGH
    assertTrue(count > 1);
    assertEquals(SmellType.HIGH, dungeon.checkSmell());
  }

  @Test
  public void testForArrowThroughCave() {
    //monster gets shot as there is a cave with monster in the opp direction that arrow can travel
    assertEquals("\n" + "Player shot the monster, monster is injured",
            dungeon.shootArrow(2, "E"));
    assertEquals("\n" + "Player shot the monster, monster has been killed",
            dungeon.shootArrow(2, "E"));
    //monster doesn't get shot as there is no cave with monster in the opp direction at distance 2
    // that arrow can travel
    assertEquals("\n" + "You shot an arrow into the darkness",
            dungeon.shootArrow(2, "S"));
  }

  @Test
  public void testForArrowThroughTunnel() {

    dungeon = new DungeonImpl(5, 4, 2, 50
            , false, 5, new FixedRandomizer(2));

    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    // location 0 is a tunnel
    assertEquals(LocationType.TUNNEL, dungeon.getLocationList().get(0).getLocationType());
    //neighbor of tunnel 0
    assertEquals(4, dungeon.getLocationList().get(0).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(1, dungeon.getLocationList().get(0).getNeighbors().get(Direction.EAST).getId());

    //monster at cave 4
    assertEquals(100, dungeon.getLocationList().get(4).getMonster().getHealth());
    //shot monster at distance 1, in West
    assertEquals("\n" + "Player shot the monster, monster is injured",
            dungeon.shootArrow(1, "W"));
    //arrow travels through tunnel and shoots the monster in cave 4
    assertEquals(50, dungeon.getLocationList().get(4).getMonster().getHealth());
  }

  @Test
  public void testArrowFoundInCaveAndTunnel() {
    int countTunnel = 0;
    int countCave = 0;

    int i = 0;
    while (i < dungeon.getLocationList().size()) {
      if (dungeon.getLocationList().get(i).getArrow() > 0) {
        if (dungeon.getLocationList().get(i).getLocationType() == LocationType.CAVE) {
          countCave++;
        } else {
          countTunnel++;
        }
      }
      i++;
    }
    assertTrue(countCave > 0);
    assertTrue(countTunnel > 0);
  }
}
