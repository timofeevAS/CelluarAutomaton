package automat

import kotlin.random.Random

class Universe(private val width: Int, private val height: Int, randomize:Boolean = false) {


    private val grid = Array(width) { i ->
        Array(height) { j ->
            if (randomize) {
                Cell(Random.nextBoolean())
            } else {
                Cell(false) // Default all cell are dead
            }
        }
    }

    constructor(gridArray: Array<Array<Boolean>>) : this(gridArray.size, gridArray.firstOrNull()?.size ?: 0) {
        gridArray.indices.forEach { i ->
            gridArray[i].indices.forEach { j ->
                grid[i][j] = Cell(gridArray[i][j])
            }
        }

    }


    private fun convert(i: Int, j: Int): Pair<Int,Int>{
        var mi = i % width
        if (mi < 0){
            mi += width
        }

        var mj = j % height
        if (mj < 0){
            mj += height
        }

        return Pair(mi, mj)
    }

    operator fun get(i: Int,j:Int): Cell {
        val converted = convert(i,j)
        return grid[converted.first][converted.second]
    }

    operator fun set(i:Int,j:Int, cell:Cell){
        val converted = convert(i,j)
        grid[converted.first][converted.second] = cell
    }

    fun getGrid():Array<Array<Boolean>>{
        return grid.map { row ->
            row.map { cell -> cell.isAlive }.toTypedArray()
        }.toTypedArray()
    }


    fun getWidth(): Int {
        return width
    }

    fun getHeigth():Int {
        return height
    }

    fun getNeighbors(i: Int, j: Int): MutableList<Cell> {
        val neighbors = arrayOf(
            Pair(0,0), // Middle
            Pair(-1, 0), // Top
            Pair(0, 1), // Right
            Pair(1, 0),  // Bottom
            Pair(0, -1)   // Left
        )

        val cells:MutableList<Cell> = mutableListOf()

        for ((dx, dy) in neighbors) {
            val ni = i + dx
            val nj = j + dy
            cells.add(this[ni,nj])
        }

        return cells
    }
}
