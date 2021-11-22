package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a location or node in the dungeon along with possible neighbors
 * of the current location, the treasures, arrows, and monster present at the current location
 * and a check if the node has been visited.
 */
public class Cave implements Location {

  private int id;
  private Map<Direction, Location> directionCaveMap;
  private List<Treasure> treasureList;
  private LocationType locationType;
  private boolean isVisited;
  private int arrow;
  private Creature monster;

  /**
   * Constructs a location node with its id.
   *
   * @param id this parameter takes the id of the location
   * @throws IllegalArgumentException when then id passed is invalid
   */
  public Cave(int id) throws IllegalArgumentException {

    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be negative");
    }
    directionCaveMap = new HashMap<Direction, Location>();
    treasureList = new ArrayList<Treasure>();
    this.id = id;
    this.isVisited = false;
    this.monster = null;
    this.arrow = 0;
  }

  /**
   * Copy constructor of the Cave class to maintain a defensive copy.
   *
   * @param location for which the copy is constructed
   * @throws IllegalArgumentException when the location entered is null
   */
  public Cave(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("The location entered is null");
    }
    directionCaveMap = location.getNeighbors();
    treasureList = location.getTreasureList();
    this.id = location.getId();
    this.isVisited = location.isVisited();
    this.locationType = location.getLocationType();
    this.arrow = location.getArrow();
    this.monster = location.getMonster();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addNeighbors(Direction direction, Location location) throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    if (location == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    this.directionCaveMap.put(direction, location);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<Direction, Location> getNeighbors() {
    Map<Direction, Location> directionLocationMapCopy =
            new HashMap<Direction, Location>(this.directionCaveMap);
    return directionLocationMapCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addTreasureList(Treasure treasure) throws IllegalArgumentException {
    if (treasure == null) {
      throw new IllegalArgumentException("Treasure argument is invalid");
    }
    this.treasureList.add(treasure);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Treasure> getTreasureList() {
    return new ArrayList<Treasure>(this.treasureList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LocationType getLocationType() {
    LocationType locationTypeCopy = this.locationType;
    return locationTypeCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLocationType(LocationType locationType) throws IllegalArgumentException {
    if (locationType == null) {
      throw new IllegalArgumentException("Location Type argument is invalid");
    }
    this.locationType = locationType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateVisit(boolean visit) {
    this.isVisited = visit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisited() {
    return this.isVisited;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeTreasure() {
    this.treasureList.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getArrow() {
    return arrow;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArrow(int arrowCount) {
    this.arrow = arrowCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArrow() {
    this.arrow = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMonster() {
    this.monster = new Monster();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Creature getMonster() {
    if (this.hasMonster()) {
      Creature monsterCopy = new Monster(monster);
      return monsterCopy;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hitMonster() {
    monster.hit();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasMonster() {
    return this.monster != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (treasureList.size() > 0) {
      sb.append("\nCave has following treasures -");
      for (int i = 0; i < treasureList.size(); i++) {
        sb.append("\n" + treasureList.get(i).getTreasure());
      }
    } else {
      sb.append("\nThe cave has no treasures");
    }

    if (arrow > 0) {
      sb.append("\nThere are " + arrow + " arrows in the current location");
    } else {
      sb.append("\nThere are no arrows at the current location");
    }
    return sb.toString();
  }
}