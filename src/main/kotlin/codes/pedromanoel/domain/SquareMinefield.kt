package codes.pedromanoel.domain

class SquareMinefield(
    dimensions: Dimensions,
    mineDeployment: MineDeployment = MineDeployment.empty()
) : Minefield {
    private val cellsByPosition: HashMap<Position, Cell> = HashMap()

    init {
        dimensions.positions.forEach { position ->
            cellsByPosition[position] =
                Cell(
                    position = position,
                    adjacentCells = cellsAdjacentTo(position),
                    mined = mineDeployment.contains(position)
                )
        }
    }

    private fun cellsAdjacentTo(position: Position): List<Cell> {
        val adjacentCells = ArrayList<Cell>()

        cellAbove(position)?.also { adjacentCells.add(it) }
        cellLeft(position)?.also { adjacentCells.add(it) }
        return adjacentCells
    }

    private fun cellAbove(position: Position) =
        cellsByPosition[position.stepUp()]

    private fun cellLeft(position: Position) =
        cellsByPosition[position.stepLeft()]

    override fun cellAt(position: Position) =
        cellsByPosition[position] ?: throw IndexOutOfBoundsException()
}