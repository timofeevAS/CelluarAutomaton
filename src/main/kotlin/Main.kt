import automat.Automat
import AutomatVisualizer


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

    val width = 10 // size(HEIGHT,WIDTH)
    val height = 10
    val fileName = "configure.txt"

    val automat = Automat(rule, width, height,false,fileName)

    val steps = 100 // Count of generations
    val delay = 500L // Delay between generations

    val visualizer = AutomatVisualizer(automat,steps,delay)
}