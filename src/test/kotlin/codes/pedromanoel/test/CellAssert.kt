package codes.pedromanoel.test

import codes.pedromanoel.domain.Cell
import codes.pedromanoel.domain.Position
import org.assertj.core.api.AbstractObjectAssert

class CellAssert(actual: Cell) :
    AbstractObjectAssert<CellAssert, Cell>(
        actual,
        CellAssert::class.java
    ) {

    fun isSurroundedByCellsAtPositions(vararg positionsArray: Position): CellAssert {
        isNotNull

        val positions = positionsArray.toList()

        val notFoundMessage = positions
            .subtract(surroundingPositions())
            .let {
                when {
                    it.isEmpty() -> ""
                    else -> """but cells at:
                    <$it> were not found"""
                }
            }

        val notExpectedMessage = surroundingPositions()
            .subtract(positions)
            .let {
                when {
                    it.isEmpty() -> ""
                    else -> """and cells at:
                    <$it> were not expected"""
                }

            }

        if (notFoundMessage.isNotEmpty() || notExpectedMessage.isNotEmpty()) {
            failWithMessage(
                """
                
                Expecting cell at ${actual.position}:
                    to be surrounded by <${positions.joinToString()}>
                $notFoundMessage
                $notExpectedMessage""".trimIndent()
            )
        }

        return this
    }

    private fun surroundingPositions() =
        actual.surroundingCells.map { it.position }

}