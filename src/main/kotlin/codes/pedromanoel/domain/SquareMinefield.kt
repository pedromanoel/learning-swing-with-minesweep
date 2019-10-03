package codes.pedromanoel.domain

class SquareMinefield(
    dimensions: Dimensions,
    val mineDeployment: MineDeployment = MineDeployment.empty()
) : Minefield {
    private val cellsByPosition: HashMap<Position, Cell> = HashMap()

    init {
        dimensions.positions.forEach { position ->
            cellsByPosition[position] =
                Cell(
                    position = position,
                    mined = mineDeployedAt(position),
                    adjacentCells = cellsAdjacentTo(position)
                )
        }
    }

    private fun mineDeployedAt(position: Position) =
        mineDeployment.contains(position)

    private fun cellsAdjacentTo(position: Position): List<Cell> {
        val positionAbove = position.stepUp()
        val positionLeft = position.stepLeft()

        return listOfNotNull(
            cellsByPosition[positionAbove.stepLeft()],
            cellsByPosition[positionAbove],
            cellsByPosition[positionAbove.stepRight()],
            cellsByPosition[positionLeft]
        )
    }

    override fun cellAt(position: Position) =
        cellsByPosition[position] ?: throw IndexOutOfBoundsException()

    override fun isCleared(): Boolean {
        return numberOfRevealedCells() == numberOfSafeCells()
    }

    private fun numberOfRevealedCells(): Int {
        return cellsByPosition
            .count { (_, cell) -> cell.status.mineStatus == MineStatus.REVEALED }
    }

    private fun numberOfSafeCells() =
        cellsByPosition.size - mineDeployment.mineCount
}