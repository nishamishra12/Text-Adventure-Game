# Readme Documentation - Text Adventure Game

## About/Overview
The game consists of a dungeon, a network of tunnels and caves that are interconnected
so that player can explore the entire world by traveling from cave to cave through the tunnels that
connect them.

The project creates a dungeon which will allow a player to enter in to the dungeon, move from one
location to another location via any of the possible directions - North, South, East, and West.
Pickup treasure or arrows from the cave if they are present. The dungeon also consists of Otyugh 
which are type of Monster, the player can slay or kill the Otyugh. The dungeon created can be wrapped 
or unwrapped along with different degree of interconnectivity. The player can lose if eaten by an Otyugh 
or wins when player reaches the destination.

## List of Features
1. The dungeon starts by taking rows, columns, treasure percentage, wrapping status, number of monsters, and interconnectivity of the dungeon.
2. Treasures and arrows are randomly added to specific percentage of the caves.
3. Dungeon can wrap or not from one side to another.
4. A new dungeon is created randomly every time resulting in a different network each time the game begins.
5. Every location in the dungeon is connected to every other location. Increasing the degree of inter-connectivity increases number of paths from one location to another.
6. Player can move from one location to another in any of the 4 directions - North, South, East, West.
7. Player can decide to pick treasure or arrows located in the dungeon.
8. A list is maintained with the player of all the treasures.
9. A count is maintained with the player of all the arrows collected.
10. Otyughs are present in the dungeon, and they have health which is reduced when the Otyugh gets shot.
11. While moving in the dungeon, player can decide to slay a monster.
12. If monster is present in the cave and player moves to that cave, the player gets eaten by the Otyugh.
13. If the Otyugh is injured and player moves to that cave, the player has 50% chance to excape alive.

## How to Run
1. Open Terminal
2. Navigate to the location where you have stored the jar file.
3. You need to provide the below-mentioned parameters as command line arguments in the order:
   1. number of rows in the dungeon 2D matrix
   2. number of columns in the dungeon 2D matrix
   3. The degree of interconnectivity
   4. The percentage of caves which should contain treasure
   5. Boolean value showing if the dungeon is wrapping or non-wrapping
      1. Wrapping Dungeon : true
      2. Non-wrapping Dungeon : false
   6. The number of monsters that should be present in the dungeon
4. Run the jar file using the
   command java - jar Adventure_Game.jar rows cols interConnectivity treasurePercent wrapping monsterCount

## How to use the program
1. Enter the no of rows, no of columns, treasure percentage, interconnectivity level, wrapping status, and monster count.
2. Dumping the dungeon
3. Displaying the description of the player, which includes the current location the player is in and the treasures and arrows that it holds.
4. Displaying the location description of the current cave the player is in.
5. Displaying the next possible moves the player can make from the current location.
6. Displaying the smell the treasure can sense.
7. Enter whether the player wants to Move, Shoot, or Pick
8. If Move, enter the direction in which to move
9. If Pick, enter whether to pickup Treasure or Arrow
10. If Shoot, enter the distance and direction in which to shoot
12. The player has to continue giving M, P, S in each iteration till the player reaches the end or the player gets eaten by the Otyugh.
13. The program will terminate if incorrect values is given in the command line.

## Description of Examples
### Run 1 – RUN 1.txt
This run shows an un-wrapped dungeon where player is moved from start to end.
1. Enter the no of rows, no of columns, treasure percentage, interconnectivity level, wrapping status, and monster count.
2. Dumping the dungeon
3. Displaying the description of the player, which includes the current location the player is in and the treasures and arrows that it holds.
4. Displaying the location description of the current cave the player is in.
5. Displaying the next possible moves the player can make from the current location.
6. Displaying the smell the player can sense.
7. The player picks up Treasure
8. The player picks up Arrows
9. The player kills a monster
10. The player moves in different directions
11. The player shoots into darkness. 
13. Iterate the same process till the player reaches near the end cave, kill Otyugh at the end cave and move player to the end Cave.

### Run 2 – RUN 2.txt
This run shows a wrapped dungeon where player is moved in every location in the dungeon.
1. Enter the no of rows, no of columns, treasure percentage, interconnectivity level, wrapping status, and monster count.
2. Dumping the dungeon
3. Displaying the description of the player, which includes the current location the player is in and the treasures and arrows that it holds.
4. Displaying the location description of the current cave the player is in.
5. Displaying the next possible moves the player can make from the current location.
6. Displaying the smell the player can sense.
7. The player kills a monster in South Cave.
8. Move player to South Cave
9. Move player to East cave, monster in east cave.
10. Player got eaten by the monster, game over.

## Design/Model Changes
1. Initially the simple controller was going to be implemented, but later in the actual design
implemented a command controller inorder to simplify the usages of the command.
2. Smell is an enum instead of a plain string, to ensure that there is no spelling modifications.

## Assumptions
1. Each cave can have upto three treasures of any type
2. Each cave can have upto three arrows
3. Treasure percentage is an integer

## Limitations
1. The player will pick up all the treasure at once from a location
2. The player will pick up all the arrows at once from a location

## Citations
1. https://www.baeldung.com/java-spanning-trees-kruskal
2. https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
3. https://northeastern.instructure.com/courses/90366/pages/8-dot-6-command-design-pattern?module_item_id=6535611
4. https://www.tutorialspoint.com/mvc_framework/mvc_framework_introduction.htm

