import java.util.Scanner

fun main(args: Array<String>) {

    //For entering input through command line and reading it
    val scanner = Scanner(System.`in`)


    //initialising the right-handed coordinates mentioning the rectangular surface
    var upperRightCoordinates: List<Int>? = null

    //validating the user input for upperRightCoordinates  and the format
    while (upperRightCoordinates == null) {
        println("Please enter the upper-right coordinates of the rectangular world (format: X Y):")
        val input = scanner.nextLine()
        val coordinates = input.split(" ")

        if (coordinates.size == 2) {
            try {
                val x = coordinates[0].toInt()
                val y = coordinates[1].toInt()
                upperRightCoordinates = listOf(x, y)
            } catch (e: NumberFormatException) {
                println("Invalid format. Please enter the coordinates in integer format.")
            }
        } else {
            println("Invalid format. Please enter the coordinates as 'X Y'.")
        }
    }


    //initialising the inputs for robots
    val robotInputs: MutableList<RobotInput> = mutableListOf()
    //initialing a flag for checking if the user want to enter more robots
    var doneEnteringRobots = false

    //validations for robots initial position in the specified format
    while (!doneEnteringRobots) {
        println("Please enter the robot's initial position (format: X Y Orientation):")
        val positionInput = scanner.nextLine()
        val position = positionInput.split(" ")

        if (position.size == 3) {
            try {
                val x = position[0].toInt()
                val y = position[1].toInt()
                val orientation = position[2].toCharArray()
                if (orientation[0] in listOf('N', 'S', 'E', 'W','n','s','e','w')) {
                    var instructions: String? = null

                    //validations for robots instructions in the specified format
                    while (instructions == null) {
                        println("Please enter the robot's instructions:")
                        val instructionsInput = scanner.nextLine()
                       /* if (instructionsInput.matches(Regex("[LRF]+"))) {*/
                        if (instructionsInput.matches(Regex("[LRFlrf]+"))) {
                            instructions = instructionsInput.uppercase()
                        } else {
                            println("Invalid format. Please enter the instructions as a string of 'L', 'R', and 'F' only.")
                            continue
                        }
                    }

                    //initialising robot inputs with a list
                    val robotInput = RobotInput(listOf(x, y), orientation[0], instructions)
                    robotInputs.add(robotInput)
                } else {
                    println("Invalid format. Please enter a valid orientation (N, S, E, or W).")
                    continue
                }
            } catch (e: NumberFormatException) {
                println("Invalid format. Please enter the position coordinates in integer format.")
                continue
            }
        } else {
            println("Invalid format. Please enter the position as 'X Y Orientation'.")
            continue
        }
//checking if the user want to enter additional robots or not
        println("Do you want to enter another robot? (Y/N)")
        val continueInput = scanner.nextLine()
        doneEnteringRobots = continueInput.equals("N", ignoreCase = true)
    }

    var input = robotInputs



    //getting the surface size and upper right position from the lines list
    val surfaceSize = upperRightCoordinates

    val upperRight = Position(surfaceSize[0], surfaceSize[1])


    //initialising robots and instructions
    val robots = mutableListOf<Robot>()
    val instructions = mutableListOf<String>()

    /* iterating over the robotInput list to create robotPositions list and instructions list*/
    for (robotInput in robotInputs) {
        val robotPosition = robotInput.position
        val orientation = robotInput.orientation
        val robotInstructions = robotInput.instructions
        robots.add(Robot(Position(robotPosition[0].toInt(), robotPosition[1].toInt()), orientation, false))
        instructions.add(robotInstructions)

    }

//Initialising object for MarsSurface class
    val surface = MarsSurface(upperRight)

    for (i in robots.indices) {
        surface.moveRobot(robots[i], instructions[i])
        val status = if (robots[i].lost) "LOST" else ""
        println("${robots[i].position.x}${robots[i].position.y}${robots[i].orientation}$status")
    }

}

//data classes for robotonput , position and robot
data class RobotInput(val position: List<Int>, val orientation: Char, val instructions: String)

data class Position(val x: Int, val y: Int)
data class Robot(var position: Position, var orientation: Char, var lost: Boolean)


//class for the entire logic having  all the methods and functionality
class MarsSurface(private val upperRight: Position) {
    //initialising the scent
    private val scents = mutableSetOf<Position>()

    //function to move robot according to the instruction
    fun moveRobot(robot: Robot, instructions: String) {
        for (instruction in instructions) {
            if (!robot.lost) {
                when (instruction.uppercaseChar()) {
                    'L' -> rotateLeft(robot)
                    'R' -> rotateRight(robot)
                    'F' -> moveForward(robot)
                }
            }
        }
    }
//function to rotate robot left
    private fun rotateLeft(robot: Robot) {
        robot.orientation = when (robot.orientation) {
            'N' -> 'W'
            'S' -> 'E'
            'E' -> 'N'
            'W' -> 'S'
            else -> robot.orientation
        }
    }

    //function to rotate robot right
    private fun rotateRight(robot: Robot) {
        robot.orientation = when (robot.orientation.uppercaseChar()) {
            'N' -> 'E'
            'S' -> 'W'
            'E' -> 'S'
            'W' -> 'N'
            else -> robot.orientation
        }
    }
    //function to move robot forward
    private fun moveForward(robot: Robot) {
        val newPosition = when (robot.orientation.uppercaseChar()) {
            'N' -> Position(robot.position.x, robot.position.y + 1)
            'S' -> Position(robot.position.x, robot.position.y - 1)
            'E' -> Position(robot.position.x + 1, robot.position.y)
            'W' -> Position(robot.position.x - 1, robot.position.y)
            else -> robot.position
        }
        //checking if the reposition is within the surface and remembering the scent
        if (isWithinSurface(newPosition)) {
            robot.position = newPosition
        } else {
            if (!scents.contains(robot.position)) {
                robot.lost = true
                scents.add(robot.position)
            }
        }
    }
    //function to check if robot is within the rectangular surface with initial upper right coordinates
    private fun isWithinSurface(position: Position): Boolean {
        return position.x >= 0 && position.x <= upperRight.x &&
                position.y >= 0 && position.y <= upperRight.y
    }
}

