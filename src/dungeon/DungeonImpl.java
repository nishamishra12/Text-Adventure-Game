package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import randomizer.Randomizer;

/**
 * The class represents a dungeon in which the player will be moving from one direction
 * to another, along with the various location types, treasures, arrows, and Otyugh present in the
 * dungeon, and the paths from start to end of the dungeon. The player can shoot the Otyugh, escape
 * from the Otyugh or get killed by it.
 */
public class DungeonImpl implements Dungeon {

  private int rows;
  private int columns;
  private Randomizer randomizer;
  private List<Edge> edges;
  private List<Edge> mazeList;
  private int interconnectivity;
  private List<Location> locationList;
  private Location startCave;
  private Location endCave;
  private Player player;
  private boolean wrapping;
  private int percent;
  private int monsterCount;

  /**
   * Constructs a new dungeon where the player can move.
   *
   * @param rows              this parameter takes the no of rows the dungeon can have
   * @param columns           this parameter takes the no of columns the dungeon can have
   * @param interconnectivity this parameter takes the interconnectivity value of the dungeon
   * @param treasurePercent   this parameter takes the treasure percent of the dungeon
   * @param wrapping          this parameter takes the wrapping status of the dungeon
   * @param monsterCount      this parameter takes the number of monsters in the dungeon
   * @param randomizer        this parameter takes the randomizer
   * @throws IllegalArgumentException when the values entered are invalid or null
   */
  public DungeonImpl(int rows, int columns, int interconnectivity, int treasurePercent,
                     boolean wrapping, int monsterCount, Randomizer randomizer)
          throws IllegalArgumentException {

    if (rows <= 0) {
      throw new IllegalArgumentException("No of rows is invalid");
    }
    if (columns <= 0) {
      throw new IllegalArgumentException("No of columns is invalid");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Inter connectivity entered is invalid");
    }
    if (monsterCount < 1) {
      throw new IllegalArgumentException("Monster count should be at least 1");
    }
    if (treasurePercent < 0 || treasurePercent > 100) {
      throw new IllegalArgumentException("Treasure percent is invalid. Should be between 0-100");
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer entered is null. Enter correct randomizer");
    }
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnectivity;
    this.randomizer = randomizer;
    this.wrapping = wrapping;
    this.monsterCount = monsterCount;
    this.percent = (treasurePercent + randomizer.getNextInt(0,
            (100 - treasurePercent)));
    this.edges = new ArrayList<>();
    KruskalAlgo maze = new KruskalAlgo();
    createMaze();
    mazeList = maze.kruskalAlgo(edges, rows * columns);
    createMazeList();
    createCaves();
    addNeighbors();
    setLocationType();
    findMinPath();
    addTreasureToCave();
    addArrowsToCave();
    addMonster();

    player = new PlayerImpl("John", startCave);
  }

  private void createMaze() {
    int[][] dunArr = new int[rows][columns];
    int counter = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        dunArr[i][j] = counter;
        counter++;
      }
    }

    //create edges for rows
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns - 1; j++) {
        edges.add(new Edge(dunArr[i][j], dunArr[i][j + 1], randomizer.getNextInt(1, 10)));
      }
    }
    //create edges for columns
    for (int i = 0; i < rows - 1; i++) {
      for (int j = 0; j < columns; j++) {
        edges.add(new Edge(dunArr[i][j], dunArr[i + 1][j], randomizer.getNextInt(1, 10)));
      }
    }
    if (wrapping) {
      for (int i = 0; i < rows; i++) {
        edges.add(new Edge(dunArr[i][0], dunArr[i][columns - 1], randomizer.getNextInt(0, 10)));
      }

      for (int i = 0; i < columns; i++) {
        edges.add(new Edge(dunArr[0][i], dunArr[rows - 1][i], randomizer.getNextInt(0, 10)));
      }
    }
  }

  private void createMazeList() {
    List<Edge> extraList = new ArrayList<>(edges);
    extraList.removeAll(mazeList);
    extraList = randomizer.shuffleList(extraList);

    if (interconnectivity > extraList.size()) {
      throw new IllegalArgumentException("Interconnectivity is wrong");
    }
    for (int i = 0; i < interconnectivity; i++) {
      mazeList.add(extraList.get(i));
    }
  }

  private void createCaves() {
    locationList = new ArrayList<>();
    for (int i = 0; i < rows * columns; i++) {
      locationList.add(new Cave(i));
    }
  }

  private void addNeighbors() {
    for (int i = 0; i < locationList.size(); i++) {
      for (int j = 0; j < mazeList.size(); j++) {
        if (locationList.get(i).getId() == mazeList.get(j).getSrc()) {
          if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() == 1) {
            locationList.get(i).addNeighbors(Direction.EAST,
                    locationList.get(mazeList.get(j).getDest()));
            locationList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.WEST, locationList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc()
                  == Math.abs(columns - 1)) {
            locationList.get(i).addNeighbors(Direction.WEST,
                    locationList.get(mazeList.get(j).getDest()));
            locationList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.EAST, locationList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() == columns) {
            locationList.get(i).addNeighbors(Direction.SOUTH,
                    locationList.get(mazeList.get(j).getDest()));
            locationList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.NORTH, locationList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() > columns) {
            locationList.get(i).addNeighbors(Direction.NORTH,
                    locationList.get(mazeList.get(j).getDest()));
            locationList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.SOUTH, locationList.get(i));
          }
        }
      }
    }
  }

  //setLocationType
  private void setLocationType() {
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getNeighbors().size() == 2) {
        locationList.get(i).setLocationType(LocationType.TUNNEL);
      } else {
        locationList.get(i).setLocationType(LocationType.CAVE);
      }
    }
  }

  //give treasure to cave
  private void addTreasureToCave() {
    List<Location> exclusiveCaveList = new ArrayList<>();
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getLocationType().equals(LocationType.CAVE)) {
        exclusiveCaveList.add(locationList.get(i));
      }
    }

    exclusiveCaveList = randomizer.shuffleList(exclusiveCaveList);

    int noOfCavesWithTreasure = (int) Math.ceil((percent * exclusiveCaveList.size()) / 100.0);

    for (int i = 0; i < noOfCavesWithTreasure; i++) {
      List<Treasure> treasures = new ArrayList<>(Arrays.asList(Treasure.values()));
      for (int j = 0; j <= randomizer.getNextInt(0, treasures.size()); j++) {
        exclusiveCaveList.get(i).addTreasureList(treasures.get((randomizer
                .getNextInt(0, treasures.size())) % 3));
      }
    }
  }

  //add Arrows to the cave
  private void addArrowsToCave() {
    int noOfCavesWithArrows = (int) Math.ceil((percent * locationList.size()) / 100.0);

    List<Location> locationListCopy = new ArrayList<>(locationList);
    locationListCopy = randomizer.shuffleList(locationListCopy);
    for (int i = 0; i < noOfCavesWithArrows; i++) {
      locationListCopy.get(i).addArrow(randomizer
              .getNextInt(1, 4));
    }
  }

  private Direction convert(String input) {
    Direction direction = null;

    if (input.equalsIgnoreCase("E")) {
      direction = Direction.EAST;
    } else if (input.equalsIgnoreCase("W")) {
      direction = Direction.WEST;
    } else if (input.equalsIgnoreCase("S")) {
      direction = Direction.SOUTH;
    } else if (input.equalsIgnoreCase("N")) {
      direction = Direction.NORTH;
    }
    return direction;
  }

  //check if neighbor exist
  private boolean checkNeighborExist(Location l, Direction d) {
    return l.getNeighbors().containsKey(d);
  }

  //shoot arrow
  @Override
  public String shootArrow(int dist, String dir) throws IllegalArgumentException {

    if ((!dir.equalsIgnoreCase("N") && !dir.equalsIgnoreCase("S")
            && !dir.equalsIgnoreCase("E") && !dir.equalsIgnoreCase("W"))
            || dir == null) {
      throw new IllegalArgumentException("Invalid Direction");
    }

    if (dist < 1) {
      throw new IllegalArgumentException("Distance should be at least 1");
    }

    StringBuilder sb = new StringBuilder();

    if (player.getArrowCount() <= 0) {
      sb.append("\nYou are out of arrows, explore to find more");
      return sb.toString();
    }
    Location currArrowLoc = player.getCurrentLocation();
    Direction direction = convert(dir);


    while (dist > 0) {
      if (!(checkNeighborExist(currArrowLoc, direction))) {
        sb.append("\nYou shot an arrow into the darkness");
        return sb.toString();
      } else {
        currArrowLoc = currArrowLoc.getNeighbors().get(direction);
        if (currArrowLoc.getLocationType() == LocationType.CAVE) {
          dist--;
        } else {
          for (Map.Entry<Direction, Location> map : currArrowLoc.getNeighbors().entrySet()) {
            if (map.getKey() != direction) {
              direction = map.getKey();
              break;
            }
          }
        }
      }
    }

    if (dist == 0) {
      player.decreaseArrow();
      if (currArrowLoc.hasMonster() && currArrowLoc.getMonster().getHealth() > 0) {
        currArrowLoc.hitMonster();
        if (currArrowLoc.getMonster().getHealth() == 50) {
          sb.append("\nPlayer shot the monster, monster is injured");
        } else if (currArrowLoc.getMonster().getHealth() == 0) {
          sb.append("\nPlayer shot the monster, monster has been killed");
        }
      } else {
        sb.append("\nPlayer shot an arrow into the darkness");
      }
      return sb.toString();
    }
    sb.append("\nYou shot an arrow into the darkness");
    return sb.toString();
  }

  //add monster to the cave
  private void addMonster() throws IllegalArgumentException {
    List<Location> caveListCopy = new ArrayList<>();
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getLocationType() == LocationType.CAVE) {
        caveListCopy.add(locationList.get(i));
      }
    }

    if (monsterCount > caveListCopy.size()) {
      throw new IllegalArgumentException("Monster count is greater than no of caves");
    }

    caveListCopy.remove(startCave);
    endCave.addMonster();
    caveListCopy.remove(endCave);

    //shuffle
    randomizer.shuffleList(caveListCopy);

    for (int i = 0; i < monsterCount - 1; i++) {
      caveListCopy.get(i).addMonster();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SmellType checkSmell() {
    int count = 0;
    Map<Location, Integer> level = bfs(this.player.getCurrentLocation());
    for (Location name : level.keySet()) {
      if (level.get(name) == 1 && name.hasMonster() && name.getMonster().getHealth() > 0) {
        return SmellType.HIGH;
      } else if (level.get(name) == 2 && name.hasMonster() && name.getMonster().getHealth() > 0) {
        count++;
      }
    }

    if (count >= 2) {
      return SmellType.HIGH;
    } else if (count == 1) {
      return SmellType.LOW;
    } else {
      return SmellType.NONE;
    }
  }

  //start and end cave
  private void findMinPath() {

    List<Location> possibleStart = new ArrayList<Location>();

    //Only taking the caves as the possible start positions.
    for (Location location : locationList) {
      if (location.getLocationType() != LocationType.TUNNEL) {
        possibleStart.add(location);
      }
    }

    //Shuffling possible start to take random.
    possibleStart = randomizer.shuffleList(possibleStart);

    for (Location i : possibleStart) {
      this.startCave = i;
      Map<Location, Integer> level = bfs(this.startCave);
      this.endCave = null;
      for (Location name : level.keySet()) {
        if (level.get(name) >= 5 && name.getLocationType() == LocationType.CAVE) {
          this.endCave = name;
          break;
        }
      }
      if (this.endCave != null) {
        break;
      }
    }
    if (this.endCave == null) {
      throw new IllegalArgumentException("The matrix size is not valid, no start end possible!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<Location, Integer> bfs(Location src) {
    if (src == null) {
      throw new IllegalArgumentException("Source location cannot be null");
    }

    LinkedList<Location> queue = new LinkedList();
    Map<Location, Integer> locationLevelMap = new HashMap<>();

    src.updateVisit(true);
    queue.add(src);
    locationLevelMap.put(src, 0);

    while (queue.size() != 0) {
      src = queue.poll();

      Iterator<Location> i = new ArrayList<Location>(src.getNeighbors().values()).listIterator();

      while (i.hasNext()) {
        Location n = i.next();
        if (!n.isVisited()) {
          n.updateVisit(true);
          queue.add(n);
          locationLevelMap.put(n, locationLevelMap.get(src) + 1);
        }
      }
    }

    for (Location i : locationList) {
      i.updateVisit(false);
    }
    return new HashMap<>(locationLevelMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String nextMove(String val) throws IllegalArgumentException {
    boolean result;
    StringBuilder sb = new StringBuilder();
    if (val.equalsIgnoreCase("N")) {
      result = player.move(Direction.NORTH);
    } else if (val.equalsIgnoreCase("S")) {
      result = player.move(Direction.SOUTH);
    } else if (val.equalsIgnoreCase("E")) {
      result = player.move(Direction.EAST);
    } else if (val.equalsIgnoreCase("W")) {
      result = player.move(Direction.WEST);
    } else {
      throw new IllegalArgumentException("Invalid move " + val);
    }

    if (result) {
      if (player.getCurrentLocation().hasMonster()) {
        int escape = randomizer.getNextInt(0, 2);
        if ((player.getCurrentLocation().getMonster().getHealth() == 100)
                || (player.getCurrentLocation().getMonster().getHealth() == 50
                && escape % 2 == 1)) {
          player.killPlayer();
          sb.append("\nMonster in cave! Chomp, chomp, chomp, player got eaten by an Otyugh!");
          sb.append("\nBetter luck next time");
        } else if ((player.getCurrentLocation().getMonster().getHealth() == 50
                && escape % 2 == 0)) {
          sb.append("Player escaped successfully from an injured Otyugh, and moved to location "
                  + player.getCurrentLocation().getId());
        } else if (player.getCurrentLocation().getMonster().getHealth() == 0) {
          sb.append("Player moved successfully to location " + player.getCurrentLocation().getId());
        }
      } else {
        sb.append("Player moved successfully to location " + player.getCurrentLocation().getId());
      }
    } else {
      sb.append("\nMove not possible ").append(val);
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < locationList.size(); i++) {
      sb.append("\n" + locationList.get(i).getLocationType() + ": " + locationList.get(i).getId());
      Map<Direction, Location> treeMap = new TreeMap<Direction, Location>(
              locationList.get(i).getNeighbors());
      for (Map.Entry<Direction, Location> map : treeMap.entrySet()) {
        sb.append("\n-->" + map.getKey() + " Neighbor: " + map.getValue().getLocationType()
                + ": " + map.getValue().getId());
      }
      if (locationList.get(i).hasMonster()) {
        sb.append("\n###Monster###");
      }
      if (locationList.get(i).getArrow() > 0) {
        sb.append("\nArrow Count: " + locationList.get(i).getArrow());
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String pickTreasure() {
    StringBuilder sb = new StringBuilder();
    if (player.getCurrentLocation().getTreasureList().size() > 0) {
      player.updateTreasureList();
      sb.append("Treasure picked up");
    } else {
      sb.append("No treasure present at the location");
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String pickArrow() {
    StringBuilder sb = new StringBuilder();
    if (player.getCurrentLocation().getArrow() > 0) {
      player.pickUpArrow();
      sb.append("Arrow picked up");
    } else {
      sb.append("No arrow present at the location");
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasReachedEnd() {
    return player.getCurrentLocation().getId() != endCave.getId() ? false : true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getNextPossibleDescription() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\nNext possible moves: ");
    Map<Direction, Location> treeMap = new TreeMap<>(player.getCurrentLocation().getNeighbors());
    for (Map.Entry<Direction, Location> set : treeMap.entrySet()) {
      stringBuilder.append("\n" + set.getKey().getDirection());
    }
    return stringBuilder.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocationDescription() {
    return player.getCurrentLocation().toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPlayerDescription() {
    return player.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Location> getLocationList() {
    return new ArrayList<>(this.locationList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Player getPlayer() {
    Player playerCopy = new PlayerImpl(this.player);
    return playerCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Location getEndCave() {
    Location endCaveCopy = new Cave(this.endCave);
    return endCaveCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Location getStartCave() {
    Location startCaveCopy = new Cave(this.startCave);
    return startCaveCopy;
  }
}