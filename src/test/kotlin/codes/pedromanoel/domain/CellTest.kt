package codes.pedromanoel.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CellTest {

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
    fun setup_adjacent_mines_transitively() {
        val firstCell = Cell()
        val secondCell = Cell(adjacentCells = listOf(firstCell))

        assertThat(secondCell.adjacentCells).containsExactly(firstCell)
        assertThat(firstCell.adjacentCells).containsExactly(secondCell)
    }

    @Test
    fun reveal_empty_cell_counts_adjacent_mines() {
        val adjacentCells = listOf(
            Cell(mined = false),
            Cell(mined = true),
            Cell(mined = false),
            Cell(mined = true)
        )

        Cell(adjacentCells = adjacentCells)
            .also { assertThat(it.adjacentMines).isEqualTo(0) }
            .also { it.reveal() }
            .also { assertThat(it.adjacentMines).isEqualTo(2) }
    }
}