package codes.pedromanoel.domain

class Cell(
    val position: Position = Position(0, 0),
    adjacentCells: List<Cell> = emptyList(),
    var exploded: Boolean = false,
    var adjacentMines: Int = 0,
    private val mined: Boolean = false
) {
    val adjacentCells = ArrayList<Cell>()

    init {
        this.adjacentCells.addAll(adjacentCells)
        this.adjacentCells.forEach { it.adjacentCells.add(this) }
    }

    fun reveal() {
        exploded = mined
        adjacentMines = adjacentCells.count { it.mined }
    }

    override fun toString(): String {
        val (y, x) = position
        return "Cell($y, $x)"
    }
}

