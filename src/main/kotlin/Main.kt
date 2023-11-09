import automat.Automat
import AutomatVisualizer
import java.io.File
import java.util.Scanner

// Global variables, params for Celluar Automaton
var size: Int = 0
var delay: Long = 0
var generateRandom: Boolean = false
var steps: Int = 0
var automatFileName:String = ""
fun readParams() {
    // Reading params
    val scanner = Scanner(System.`in`)

    while (true) {
        println("Enter the file name for configuration (or press Enter to skip):")
        val userInput = scanner.nextLine().trim()

        if (userInput.isEmpty()) {
            // User pressed Enter to skip
            break
        }

        val file = File("src/main/resources/$userInput")
        if (file.exists() && file.isFile) {
            automatFileName = userInput
            break
        } else {
            println("File not found. Please enter a valid file name.")
        }
    }

    if(automatFileName.isEmpty()) {
        println("Enter width of universe NxN:")
        while (true) {
            if (scanner.hasNextInt()) {
                val userInput = scanner.nextInt()
                if (userInput >= 0) {
                    size = userInput
                    break
                } else {
                    println("Please, enter a non-negative integer. Integer should be in range (1,200)")
                }
            } else {
                println("Please, enter a valid integer.")
                scanner.next() // Discard invalid input
            }
        }
    }

    println("Enter delay between generations (milliseconds):")
    while (true) {
        if (scanner.hasNextInt()) {
            val userInput = scanner.nextInt()
            if (userInput >= 0) {
                delay = userInput.toLong()
                break
            } else {
                println("Please, enter a non-negative integer.")
            }
        } else {
            println("Please, enter a valid integer.")
            scanner.next() // Discard invalid input
        }
    }


    println("Enter count of generations (>=1):")
    while (true) {
        if (scanner.hasNextInt()) {
            val userInput = scanner.nextInt()
            if (userInput >= 0) {
                steps = userInput
                break
            } else {
                println("Please, enter a non-negative integer. Integer should be greater than 1")
            }
        } else {
            println("Please, enter a valid integer.")
            scanner.next() // Discard invalid input
        }
    }

    if(automatFileName.isNotEmpty()) return

    while (true) {
        println("Generate a random field? (y/n):")
        val input = scanner.next()
        if (input.equals("y", ignoreCase = true)) {
            generateRandom = true
            break
        } else if (input.equals("n", ignoreCase = true)) {
            generateRandom = false
            break
        } else {
            println("Invalid input. Please enter 'y' or 'n'.")
        }
    }



}

fun main(args: Array<String>) {
    // rule from 0 to 4294967296
    val variant: Int = 21
    val birthYear: Int = 2003
    val birthDay: Int = 26
    val birthMonth: Int = 10

    var rule: Long = (variant * birthYear * birthDay * birthMonth).toLong()

    /*
    30x30 -> markerSize 20, size 800 750
     */

    // Reading params
    readParams();

    val width = 50 // size(HEIGHT,WIDTH)
    val height = 50
    val fileName = ""
    "configure.txt"


    val automat = Automat(rule, size, size,generateRandom,automatFileName)

    val visualizer = AutomatVisualizer(automat,steps,delay)
}