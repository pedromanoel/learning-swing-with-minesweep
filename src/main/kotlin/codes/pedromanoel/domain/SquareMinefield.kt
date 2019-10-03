package codes.pedromanoel.domain

import kotlin.random.Random

class SquareMinefield(
    dimensions: Dimensions,
    random: Random = Random.Default,
    numberOfMines: Int = 0
) : Minefield {
    val cells: List<Cell>
        get() = cellsByPosition.values.toList()

    private val cellsByPosition: HashMap<Position, Cell> = HashMap()

    init {
        val minedPositions = dimensions.positions
            .shuffled(random)
            .take(numberOfMines)

        dimensions.positions.forEach { position ->
            cellsByPosition[position] =
                Cell(
                    position = position,
                    adjacentCells = cellsAdjacentTo(position),
                    mined = minedPositions.contains(position)
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