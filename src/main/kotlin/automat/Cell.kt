package automat

data class Cell(
    // val i: Int,
    // val j: Int,
    var isAlive: Boolean) {
    fun toggle() {
        isAlive = !isAlive
    }

    fun toInt(): Int{
        return if (isAlive) 1 else 0
    }
}