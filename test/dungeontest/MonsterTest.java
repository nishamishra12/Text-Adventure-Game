package dungeontest;

import org.junit.Before;
import org.junit.Test;

import dungeon.Creature;
import dungeon.Monster;
import static org.junit.Assert.assertEquals;

/**
 * Test class to test all the implementation of Monster class.
 */
public class MonsterTest {

  private Creature otyugh;

  @Before
  public void setUp() throws Exception {
    otyugh = new Monster();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMonster() {
    new Monster(null);
  }

  @Test
  public void getHealth() {
    assertEquals(100, otyugh.getHealth());
  }

  @Test
  public void hit() {
    otyugh.hit();
    assertEquals(50,otyugh.getHealth());
  }
}