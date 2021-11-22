package dungeon;

import java.util.List;
import java.util.Map;

/**
 * The interface represents a dungeon in which the player will be moving from one direction
 * to another, along with the various location types, treasures, arrows, and Otyugh present in the
 * dungeon, and the paths from start to end of the dungeon. The player can shoot the Otyugh, escape
 * from the Otyugh or get killed by it.
 */
public interface Dungeon {

  /**
   * This method moves the player from one location to the other based on the location value that
   * is passed.
   *
   * @param val this parameter takes the location value of the direction
   *            in which the player has to move
   * @return the result of the move
   * @throws IllegalArgumentException when the move entered is invalid
   */
  public String nextMove(String val) throws IllegalArgumentException;

  /**
   * This method checks if the player has reached end of the dungeon.
   *
   * @return true or false depending on if the player has reached end or not
   */
  public boolean hasReachedEnd();

  /**
   * This method provides the description of next possible moves the player can go to from the
   * current location.
   *
   * @return the string representation next possible moves
   */
  public String getNextPossibleDescription();

  /**
   * This method updates the treasure list of the location and the player once the player decides
   * to pick up the treasure at the current location.
   *
   * @return the string if the treasure has been picked up or not
   */
  public String pickTreasure();

  /**
   * This method provides the all the locations present in the dungeon.
   *
   * @return list of location objects present in the dungeon
   */
  public List<Location> getLocationList();

  /**
   * This method is used to apply the BFS algorithm on the dungeon where the root node is set as
   * the src provided in the parameters and after creating the BFS path, it will provide the list of
   * all the nodes and their levels in the BFS tree.
   *
   * @param src Source that will be used as the root node of the BFS path.
   * @return the map that contains all the nodes and their levels in the created BFS tree.
   * @throws IllegalArgumentException if the src is null.
   */
  public Map<Location, Integer> bfs(Location src) throws IllegalArgumentException;

  /**
   * This method provides the player playing in the dungeon.
   *
   * @return the player
   */
  public Player getPlayer();

  /**
   * This method provides the end cave of the dungeon.
   *
   * @return the end cave
   */
  public Location getEndCave();

  /**
   * This method provides the start cave of the dungeon.
   *
   * @return the start cave.
   */
  public Location getStartCave();

  /**
   * This method provides the description of the player consisting of the current locations,
   * treasures and arrows collected by the player.
   *
   * @return the string representation of the player description
   */
  public String getPlayerDescription();

  /**
   * This method picks up arrow from a location and updates the number of arrows the player has
   * along with removing arrows from the current location.
   *
   * @return the string is arrow is picked up or not
   */
  public String pickArrow();

  /**
   * This method provides the type of smell in the dungeon coming from the nearby locations of the
   * current location the player is in. The smell is determined by the monsters present at each
   * location.
   *
   * @return the type of Smell
   */
  public SmellType checkSmell();

  /**
   * This method shoots an arrow in the given direction and the required distance specified by the
   * player. The method shoots and kills the Otyugh if it is present at specified parameter, else
   * it misses the monster.
   *
   * @param distance this parameter takes the distance till which the arrow should travel
   * @param direction this parameter takes the direction in which the arrow should travel
   * @return true or false based on if the arrow is shot or not.
   */
  public String shootArrow(int distance, String direction);

  /**
   * This method provides the location description, along with the treasures and arrows present at
   * the location.
   *
   * @return the string representation of the location description
   */
  public String getLocationDescription();
}
