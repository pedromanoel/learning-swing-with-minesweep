package codes.pedromanoel.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random

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

        //      1,2
        // 2,1 (2,2) 2,3
        //      3,2
        assertThat(middleCell.adjacentCells)
            .containsExactly(
                minefield.cellAt(Position(1, 2)),
                minefield.cellAt(Position(2, 1)),
                minefield.cellAt(Position(2, 3)),
                minefield.cellAt(Position(3, 2))
            )
    }

    @Test
    fun deploy_mines_during_creation() {
        val minefield = SquareMinefield(
            random = Random(1),
            numberOfMines = 1,
            dimensions = Dimensions(2, 2)
        )

        val mineStatus = minefield.cells.map { cell ->
            cell.reveal()
            cell.status.mineStatus
        }

        assertThat(mineStatus).containsExactly(
            MineStatus.REVEALED,
            MineStatus.REVEALED,
            MineStatus.REVEALED,
            MineStatus.EXPLODED
        )
    }
}