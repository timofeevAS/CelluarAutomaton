package automat

import java.io.File


class Automat(private var rule:Long,var width: Int,var height: Int, randomize:Boolean = false, filename:String = "") {
    private var universes = arrayOf(Universe(width,height,randomize), Universe(width,height,randomize)) // two universes
    private var generations: Int = 0

    init {
        if ((randomize and filename.toBoolean())) {
            throw IllegalArgumentException("Randomize flag and filename can't use together!")
        }

        if (filename.isNotEmpty()){
            val file = File("src/main/resources/$filename")

            val lines = file.readLines()

            if (lines.isNotEmpty()) {
                val dimensions = lines.first().split(" ").map { it.toInt() }
                val rows = dimensions[0]
                val cols = dimensions[1]

                this.height = rows
                this.width = cols

                val grid = lines.subList(1, lines.size).map { it.toCharArray() }

                println("Reading from $filename with size:\n Rows: $rows, Columns: $cols")

                var newUniverseArray: Array<Array<Boolean>> = emptyArray();

                for (row in grid) {
                    println(row)
                    val charList = row.filter { !it.isWhitespace() }
                    val boolRow = charList.map { it == 'x' }.toTypedArray()

                    newUniverseArray = newUniverseArray.plusElement(boolRow)
                }
                newUniverseArray.reverse()


                // Fill universe from file
                universes[0] = Universe(newUniverseArray)
                universes[1] = Universe(newUniverseArray)

            }
            else {
                println("File is empty.")
            }


        }
    }
    fun printAutomatState() {
        val universe = universes.first() // Take first universe and show it
        val width = universe.getWidth()
        val height = universe.getHeigth()

        for(i in 0 until width){
            for(j in 0 until height){
                val cell = universe[i,j]
                val cellBody = if (cell.isAlive) 'X' else '.'
                print("$cellBody ")
            }
            println()
        }
    }

    private fun ruleResult(neighbors: MutableList<Cell>):Boolean {
        val binaryRule = rule.toString(2).padStart(32, '0')
        val index = neighbors[0].toInt() * 16 + neighbors[1].toInt() * 8 + neighbors[2].toInt() * 4 + neighbors[3].toInt() * 2 + neighbors[4].toInt()

        // Get state of cell on next iteration
        val nextState = binaryRule[31 - index].toString().toInt()
        return (nextState == 1)
    }

    fun currentState():Array<Array<Boolean>>{
        return universes[0].getGrid()
    }

    override fun toString():String{
        var res:String = "";
        val universe = universes.first() // Take first universe and show it
        val width = universe.getWidth()
        val height = universe.getHeigth()

        for(i in 0 until width){
            for(j in 0 until height){
                val cell = universe[i,j]
                val cellBody = if (cell.isAlive) 'X' else '.'
                res = "$res$cellBody "
            }
            res = "$res \n"
        }
        return res;
    }

    fun applyRule(){
        val universe = universes[0] // Take first universe as base
        val nextUniverse = universes[1] // Take second universe
        val width = universe.getWidth()
        val height = universe.getHeigth()

        for(i in 0 until width){
            for(j in 0 until height){
                var neighbors: MutableList<Cell> = universe.getNeighbors(i,j)
                var newCell: Cell = Cell(ruleResult(neighbors))
                nextUniverse[i,j] = newCell
            }
        }

        universes[0] = nextUniverse
        universes[1] = universe
    }



}