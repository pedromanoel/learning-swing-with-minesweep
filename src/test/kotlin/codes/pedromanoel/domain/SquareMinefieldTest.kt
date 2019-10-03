package codes.pedromanoel.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SquareMinefieldTest {

    @Test
    fun initialize_minimum_square_minefield() {
        val minefield = SquareMinefield(
            Dimensions(1, 1)
        )

        assertThat(minefield.cellAt(Position(1, 1)).adjacentCells).isEmpty()
    }

    @Test
    fun throw_error_when_position_outside_minefield() {
        val minefield = SquareMinefield(Dimensions(1, 1))

        Assertions
            .assertThatExceptionOfType(IndexOutOfBoundsException::class.java)
            .isThrownBy { minefield.cellAt(Position(2, 2)) }
    }

    @Test
    fun single_row_square_minefield_has_adjacent_cells_set() {
        val minefield = SquareMinefield(Dimensions(height = 1, width = 3))

        val left = minefield.cellAt(Position(1, 1))
        val middle = minefield.cellAt(Position(1, 2))
        val right = minefield.cellAt(Position(1, 3))

        assertThat(left.adjacentCells).containsExactly(middle)
        assertThat(middle.adjacentCells).containsExactly(left, right)
        assertThat(right.adjacentCells).containsExactly(middle)
    }

    @Test
    fun two_dimensions_minefield_has_adjacent_cells_set() {
        val minefield = SquareMinefield(Dimensions(height = 3, width = 3))

        val middleCell = minefield.cellAt(Position(2, 2))

        // 1,1  1,2  1,3
        // 2,1 (2,2) 2,3
        // 3,1  3,2  3,3
        assertThat(middleCell.adjacentCells)
            .containsExactly(
                minefield.cellAt(Position(1, 1)),
                minefield.cellAt(Position(1, 2)),
                minefield.cellAt(Position(1, 3)),
                minefield.cellAt(Position(2, 1)),
                minefield.cellAt(Position(2, 3)),
                minefield.cellAt(Position(3, 1)),
                minefield.cellAt(Position(3, 2)),
                minefield.cellAt(Position(3, 3))
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

        val mineStatus = listOf(
            minefield.cellAt(Position(1, 1)),
            minefield.cellAt(Position(1, 2)),
            minefield.cellAt(Position(2, 1)),
            minefield.cellAt(Position(2, 2))
        )
            .onEach(Cell::reveal)
            .map { it.status.mineStatus }

        assertThat(mineStatus).containsExactly(
            MineStatus.EXPLODED,
            MineStatus.REVEALED,
            MineStatus.REVEALED,
            MineStatus.EXPLODED
        )
    }

    @Test
    fun clear_minefield_when_all_empty_cells_are_found() {
        // this minefield clears in one move at (1, 1)
        //     1 2 3
        //   +-------
        // 1 | 0 0 0
        // 2 | 0 1 1
        // 3 | 0 1 *

        val minefield = SquareMinefield(
            dimensions = Dimensions(3, 3),
            mineDeployment = MineDeployment(listOf(Position(3, 3)))
        )

        assertThat(minefield.isCleared())
            .describedAs("minefield starts uncleared")
            .isFalse()

        minefield.cellAt(Position(2, 2)).reveal()
        assertThat(minefield.isCleared())
            .describedAs("minefield uncleared after one cell revealed")
            .isFalse()

        minefield.cellAt(Position(1, 1)).reveal()
        assertThat(minefield.isCleared())
            .describedAs("minefield cleared after all empty cells revealed")
            .isTrue()
    }
}