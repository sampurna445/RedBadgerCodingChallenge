# Mars Rover Code

This is a Kotlin code implementation of a Mars Rover simulation. It allows you to input the size of the rectangular surface, initial positions, orientations, and instructions for multiple robots. The robots move on the surface according to the provided instructions, and their final positions and statuses (LOST or not) are displayed.


## Implementation and usage:
To use this code, follow these steps

###### 1.Import the necessary package  for reading the input:
import java.util.Scanner

###### 2.Define the main function:
fun main(args: Array<String>) {
// Code logic goes here
}

###### 3.Within the main function, create a Scanner object to read user input:
val scanner = Scanner(System.`in`)

###### 4.Prompt the user to enter the upper-right coordinates of the rectangular world:
println("Please enter the upper-right coordinates of the rectangular world (format: X Y):")
val input = scanner.nextLine()
val coordinates = input.split(" ")
// Process the coordinates and validate the input

###### 5.Prompt the user to enter the initial positions, orientations, and instructions for the robots:
println("Please enter the robot's initial position (format: X Y Orientation):")
val positionInput = scanner.nextLine()
val position = positionInput.split(" ")
// Process the position input and validate the format

// Prompt the user to enter the robot's instructions
println("Please enter the robot's instructions:")
val instructionsInput = scanner.nextLine()
// Process the instructions and validate the format

###### 6.Create a RobotInput object with the processed data and add it to the robotInputs list:
val robotInput = RobotInput(listOf(x, y), orientation[0], instructions)
robotInputs.add(robotInput)

###### 7.Repeat steps 5 and 6 until the user indicates no more robots need to be entered:
println("Do you want to enter another robot? (Y/N)")
val continueInput = scanner.nextLine()
// Set the flag `doneEnteringRobots` based on the user's input

###### 8.Create the necessary data structures for storing robots, instructions, and surface size:
val robotInputs: MutableList<RobotInput> = mutableListOf()
val robots = mutableListOf<Robot>()
val instructions = mutableListOf<String>()
val surfaceSize = upperRightCoordinates
val upperRight = Position(surfaceSize[0], surfaceSize[1])

###### 9.Iterate over the robotInputs list to create the robots and instructions lists:
for (robotInput in robotInputs) {
val robotPosition = robotInput.position
val orientation = robotInput.orientation
val robotInstructions = robotInput.instructions
robots.add(Robot(Position(robotPosition[0].toInt(), robotPosition[1].toInt()), orientation, false))
instructions.add(robotInstructions)
}

###### 10.Create an instance of the MarsSurface class:
val surface = MarsSurface(upperRight)

###### 11.Move each robot on the surface and display their final positions and statuses:
for (i in robots.indices) {
surface.moveRobot(robots[i], instructions[i])
val status = if (robots[i].lost) "LOST" else ""
println("${robots[i].position.x}${robots[i].position.y}${robots[i].orientation


