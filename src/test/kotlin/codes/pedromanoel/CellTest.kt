package codes.pedromanoel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CellTest {

    class Cell(
        private val mined: Boolean = false,
        val adjacentCells: List<Cell> = emptyList()
    ) {
        var exploded = false
        var adjacentMines = 0

        fun reveal() {
            exploded = mined
            adjacentMines = adjacentCells.count { it.mined }
        }
    }

    @Test
    fun reveal_empty_cell() {
        Cell()
            .also { assertThat(it.exploded).isFalse() }
            .also { it.reveal() }
            .also { assertThat(it.exploded).isFalse() }
    }

    @Test
    fun reveal_mined_cell() {
        Cell(mined = true)
            .also { assertThat(it.exploded).isFalse() }
            .also { it.reveal() }
            .also { assertThat(it.exploded).isTrue() }
    }

    @Test
    fun reveal_empty_cell_counts_adjacent_mines() {
        val adjacentCells = listOf(false, true, false, true)
            .map { mined -> Cell(mined) }

        Cell(adjacentCells = adjacentCells)
            .also { assertThat(it.adjacentMines).isEqualTo(0) }
            .also { it.reveal() }
            .also { assertThat(it.adjacentMines).isEqualTo(2) }
    }
}