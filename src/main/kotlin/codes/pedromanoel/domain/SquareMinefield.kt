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
        rows[Position(
            row = position.row - 1,
            column = position.column
        )]

    private fun cellLeft(position: Position): Cell? =
        rows[Position(
            row = position.row,
            column = position.column - 1
        )]

    fun cellAt(position: Position) =
        rows[position] ?: throw IndexOutOfBoundsException()
}