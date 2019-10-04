package codes.pedromanoel.domain

import codes.pedromanoel.test.assertThat
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SquareMinefieldTest {

    @Test
    fun initialize_minimum_square_minefield() {
        val minefield = SquareMinefield(
            Dimensions(1, 1)
        )

        assertThat(minefield.cellAt(Position(1, 1)).surroundingCells).isEmpty()
    }

    @Test
    fun throw_error_when_position_outside_minefield() {
        val minefield = SquareMinefield(Dimensions(1, 1))

        Assertions
            .assertThatExceptionOfType(IndexOutOfBoundsException::class.java)
            .isThrownBy { minefield.cellAt(Position(2, 2)) }
    }

    @Test
    fun single_row_square_minefield_has_surrounding_cells_set() {
        val minefield = SquareMinefield(Dimensions(height = 1, width = 3))

        val left = minefield.cellAt(Position(1, 1))
        val middle = minefield.cellAt(Position(1, 2))
        val right = minefield.cellAt(Position(1, 3))

        assertThat(left)
            .isSurroundedByCellsAtPositions(Position(1, 2))

        assertThat(middle)
            .isSurroundedByCellsAtPositions(Position(1, 1), Position(1, 3))

        assertThat(right)
            .isSurroundedByCellsAtPositions(Position(1, 2))
    }

    @Test
    fun two_dimensions_minefield_has_surrounding_cells_set() {
        val minefield = SquareMinefield(Dimensions(height = 3, width = 3))

        val middleCell = minefield.cellAt(Position(2, 2))

        assertThat(middleCell)
            .isSurroundedByCellsAtPositions(
                Position(1, 1), Position(1, 2), Position(1, 3),
                Position(2, 1), /*   (2,2)   */ Position(2, 3),
                Position(3, 1), Position(3, 2), Position(3, 3)
            )
    }

    @Test
    fun deploy_mines_during_creation() {
        val dimensions = Dimensions(2, 2)
        val deployedPositions = listOf(Position(1, 1), Position(2, 2))
        val minefield = SquareMinefield(
            mineDeployment = MineDeployment(deployedPositions),
            dimensions = dimensions
        )

        assertThat(minefield)
            .hasMinesAtPositions(Position(1, 1), Position(2, 2))
            .hasEmptyCellsAtPositions(Position(1, 2), Position(2, 1))
    }

    @Test
    fun clear_minefield_when_all_empty_cells_are_found() {
        // this minefield clears in one move at (1, 1)
        //     1 2 3
        //   +-------
        // 1 | 0 0 0
        // 2 | 0 1 1
        // 3 | 0 1 *

        val dimensions = Dimensions(3, 3)
        val mineDeployment = MineDeployment(listOf(Position(3, 3)))

        val minefield = SquareMinefield(dimensions, mineDeployment)
        assertThat(minefield)
            .describedAs("starts not cleared")
            .isNotCleared()

        minefield.cellAt(Position(2, 2)).reveal()
        assertThat(minefield)
            .describedAs("not cleared after (2, 2) is revealed")
            .isNotCleared()

        minefield.cellAt(Position(1, 1)).reveal()
        assertThat(minefield)
            .describedAs("cleared after (1, 1) is revealed")
            .isCleared()
    }
}
