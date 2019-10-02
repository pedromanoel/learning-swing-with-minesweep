package codes.pedromanoel.domain

class SquareMinefield(dimensions: Dimensions) {
    private val rows: HashMap<Position, Cell> = HashMap()

    init {
        dimensions.positions.forEach { p ->
            val adjacentCells = ArrayList<Cell>()

            cellAbove(p)?.also { adjacentCells.add(it) }
            cellLeft(p)?.also { adjacentCells.add(it) }

            rows[p] = Cell(
                position = p,
                adjacentCells = adjacentCells
            )
        }
    }

    private fun cellAbove(position: Position): Cell? =
        rows[position.stepUp()]

    private fun cellLeft(position: Position): Cell? =
        rows[position.stepLeft()]

    fun cellAt(position: Position) =
        rows[position] ?: throw IndexOutOfBoundsException()
}