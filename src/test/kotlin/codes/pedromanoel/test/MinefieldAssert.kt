package codes.pedromanoel.test

import codes.pedromanoel.domain.Cell
import codes.pedromanoel.domain.Minefield
import codes.pedromanoel.domain.Position
import org.assertj.core.api.AbstractObjectAssert

class MinefieldAssert(actual: Minefield) :
    AbstractObjectAssert<MinefieldAssert, Minefield>(
        actual,
        MinefieldAssert::class.java
    ) {

    fun isCleared(): MinefieldAssert {
        isNotNull


        if (!actual.isCleared()) {
            failWithMessage("Expecting minefield to be cleared, but it was not")
        }

        return this
    }

    fun isNotCleared(): MinefieldAssert {
        isNotNull

        if (actual.isCleared()) {
            failWithMessage("Expecting minefield to not be cleared, but it was")
        }

        return this
    }

    fun hasMinesAtPositions(vararg positions: Position): MinefieldAssert {
        isNotNull

        val notExploded = positions.filterNot(this::isCellExploded)

        if (notExploded.isNotEmpty()) {
            failWithMessage(
                """
                
                Expecting positions:
                    <${positions.joinToString()}> to have mines
                but positions:
                    <${notExploded.joinToString()}> are safe""".trimIndent()
            )
        }

        return this
    }

    fun hasEmptyCellsAtPositions(vararg positions: Position): MinefieldAssert {
        isNotNull

        val exploded = positions.filter(this::isCellExploded)

        if (exploded.isNotEmpty()) {
            failWithMessage(
                """
                
                Expecting positions:
                    <${positions.joinToString()}> to be safe
                but positions:
                    <${exploded.joinToString()}> have mines""".trimIndent()
            )
        }

        return this
    }

    private fun isCellExploded(position: Position): Boolean {
        return actual
            .cellAt(position)
            .also(Cell::reveal)
            .status.isExploded()
    }
}