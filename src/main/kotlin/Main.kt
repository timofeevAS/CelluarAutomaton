import automat.Automat
import AutomatVisualizer


fun main(args: Array<String>) {
    // rule from 0 to 4294967296
    val variant: Int = 21
    val birthYear: Int = 2003
    val birthDay: Int = 26
    val birthMonth: Int = 10

    val rule: Long = (variant * birthYear * birthDay * birthMonth).toLong()
    /*
    30x30 -> markerSize 20, size 800 750
     */

    val width = 50 // size(HEIGHT,WIDTH)
    val height = 50

    val automat = Automat(rule, width, height)

    val steps = 100 // Count of generations
    val delay = 200L // Delay between generations

    val visualizer = AutomatVisualizer(automat,steps,delay)
}