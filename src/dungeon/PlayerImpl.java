package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents the player playing in the dungeon, along with the treasures that
 * it possess, the count of arrows, the current node that it is in the dungeon,
 * and the move that the player takes inside the dungeon.
 */
public class PlayerImpl implements Player {

  private Location currentCave;
  private List<Treasure> treasureList;
  private String name;
  private int arrowCount;
  private boolean isAlive;

  /**
   * Constructs a new player with name, along with its current location.
   *
   * @param name        this parameter takes the name of the player
   * @param currentCave this parameter takes the current cave the player is in
   * @throws IllegalArgumentException when the name, current cave entered is null
   */
  public PlayerImpl(String name, Location currentCave) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name of the player cannot be null");
    }
    if (currentCave == null) {
      throw new IllegalArgumentException("Start Cave of the player cannot be null");
    }
    this.name = name;
    this.currentCave = currentCave;
    treasureList = new ArrayList<Treasure>();
    this.arrowCount = 3;
    this.isAlive = true;
  }

  /**
   * Copy constructor of the Player class to maintain a defensive copy.
   *
   * @param player Player for which the copy is constructed
   * @throws IllegalArgumentException when the player entered is null
   */
  public PlayerImpl(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    this.name = player.getName();
    treasureList = player.getTreasureList();
    this.currentCave = player.getCurrentLocation();
    this.arrowCount = player.getArrowCount();
    this.isAlive = player.isAlive();
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
  public Location getCurrentLocation() {
    Location currentCaveCopy = this.currentCave;
    return currentCaveCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean move(Direction direction) throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    Map<Direction, Location> neighborMap =
            new HashMap<Direction, Location>(this.currentCave.getNeighbors());
    if (neighborMap.containsKey(direction)) {
      this.currentCave = neighborMap.get(direction);
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateTreasureList() {
    this.treasureList.addAll(this.currentCave.getTreasureList());
    this.currentCave.removeTreasure();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getArrowCount() {
    return arrowCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void decreaseArrow() {
    this.arrowCount--;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void pickUpArrow() {
    this.arrowCount += this.currentCave.getArrow();
    this.currentCave.removeArrow();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void killPlayer() {
    this.isAlive = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAlive() {
    return this.isAlive;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("The player is in " + currentCave.getLocationType() + ": "
            + currentCave.getId());
    Map<Treasure, Integer> treasureMap = new TreeMap<>();
    for (int i = 0; i < treasureList.size(); i++) {
      if (treasureMap.containsKey(treasureList.get(i))) {
        treasureMap.put(treasureList.get(i),
                treasureMap.get(treasureList.get(i)) + 1);
      } else {
        treasureMap.put(treasureList.get(i), 1);
      }
    }
    if (treasureMap.isEmpty()) {
      sb.append("\nPlayer has no treasure");
    } else {
      sb.append("\nPlayer has following treasures:");
    }
    for (Map.Entry<Treasure, Integer> m : treasureMap.entrySet()) {
      sb.append(" " + m.getKey() + ": " + m.getValue());
    }

    if (arrowCount > 0) {
      sb.append("\nPlayer has " + arrowCount + " arrows");
    } else {
      sb.append("\nPlayer has no arrows");
    }
    return sb.toString();
  }
}
