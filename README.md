# Aquarium

Created by Zach Manson & Oliver Lynch 

A Java program to allow a user to play the game Aquarium.

![Aquarium Game](screenshot.png)

To compile and run from source:

```bash
make
java Run [gridsize]
```

To run from .jar release:
```bash
java -jar Aquarium.jar
```

CITS1001 Project 2
Final Grade: 100%

## Gameplay

The game is about filling aqariums (cells grouped by red outline) to different heights.  Left click to fill in boxes with water.  Each aquarium must be filled from bottom to top, and each row of an aquarium must be filled at the same time.  The goal of the game is to fill the aquariums in an arrangement so that the number of filled boxes in each row and column matches the corresponding number on the top and left of the grid.

To check if your arrangement is correct, press the `CHECK` button.  To find a solution click the `SOLVE` button.  To generate a new randomly generated puzzle, click the `+` button.

## Project Details

+ Submission deadline: 5pm Friday 29 May 2020
+ Submit via [cssubmit](https://secure.csse.uwa.edu.au/run/cssubmit)
+ Value: 25% of CITS1001

## Todos:
+ ~~Add our names and student IDs to each file~~
+ find out what we have to do in the way of error checking
+ ~~Fix the tick font issue~~ complete
+ Make program conform to clarity guidelines (e.g. 80 char line max)
	+ ~~Ensure you fill in the @author and @version fields in the header comments with your details for each submitted class.~~
	+ All code should be neatly laid out and indented, with lines no longer than eighty characters.
	+ Do not add fields to the classes, except you should add fields to AquariumViewer where required for a consistent look-and-feel.
	+ Variables should be given appropriate names.
	+ Select appropriate programming constructs for the method implementations.
	+ Keep code as simple as possible for the job it is required to do.
	+ If the logic you have used in a method is particularly complex, a brief comment should be added explaining the strategy you have adopted; but otherwise, code should not be commented unnecessarily.
+ Replace some of the magic numbers in the display code
+ Write out extension explanation to email to Lyndon

## Extension ideas:
+ ~~Auto solve~~ complete
	+ ~~Should add comments to the solving algorithm~~ complete
	+ ~~Rewrite algorithm to improve time complexity~~ complete
		1. From bottom row to top row, determine possible aquarium configurations to satisfy the sum on that row
		2. The aquarium configuration is only valid if every aquarium is filled or non-existent in the row below
		3. For rows with only one possible configuration for any aquarium, all rows below must discard configurations where that tank isn't filled

+ ~~Dark theme :^)~~ complete
+ ~~Timer~~ complete
+ ~~Puzzle generator~~ complete
