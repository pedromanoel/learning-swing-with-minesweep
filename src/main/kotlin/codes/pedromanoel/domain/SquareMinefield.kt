package codes.pedromanoel.domain

class SquareMinefield(
    override val dimensions: Dimensions,
    val mineDeployment: MineDeployment = MineDeployment.empty()
) : Minefield {
    private val cellsByPosition: HashMap<Position, Cell> = HashMap()

    init {
        dimensions.positions.forEach { position ->
            cellsByPosition[position] =
                Cell(
                    position = position,
                    mined = mineDeployedAt(position),
                    surroundingCells = cellsSurrounding(position)
                )
        }
    }

    private fun mineDeployedAt(position: Position) =
        mineDeployment.contains(position)

    private fun cellsSurrounding(position: Position): List<Cell> {
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
            .count { (_, cell) -> cell.status.isRevealed() }
    }

    private fun numberOfSafeCells() =
        cellsByPosition.size - mineDeployment.mineCount
}