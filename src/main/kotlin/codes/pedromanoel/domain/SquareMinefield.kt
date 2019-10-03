package codes.pedromanoel.domain

class SquareMinefield(dimensions: Dimensions) {
    private val rows: HashMap<Position, Cell> = HashMap()

    init {
        dimensions.positions.forEach { position ->
            rows[position] = Cell(position, cellsAdjacentTo(position))
        }
    }

    private fun cellsAdjacentTo(position: Position): List<Cell> {
        val adjacentCells = ArrayList<Cell>()

        cellAbove(position)?.also { adjacentCells.add(it) }
        cellLeft(position)?.also { adjacentCells.add(it) }
        return adjacentCells
    }

    private fun cellAbove(position: Position) =
        rows[position.stepUp()]

    private fun cellLeft(position: Position) =
        rows[position.stepLeft()]

    fun cellAt(position: Position) =
        rows[position] ?: throw IndexOutOfBoundsException()
}