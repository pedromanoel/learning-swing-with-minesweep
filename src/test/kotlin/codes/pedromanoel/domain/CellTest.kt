package codes.pedromanoel.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CellTest {

    @Test
    fun cell_is_created_concealed() {
        assertThat(Cell(Position.origin()).status).isEqualTo(CellStatus.concealed())
    }

    @Test
    fun cells_are_adjacent_to_one_another() {
        val firstCell = Cell(Position.origin())
        val secondCell = Cell(Position.origin(), adjacentCells = listOf(firstCell))

        assertThat(secondCell.adjacentCells).containsExactly(firstCell)
        assertThat(firstCell.adjacentCells).containsExactly(secondCell)
    }

    @Test
    fun reveal_empty_Cell() {
        val cell = Cell(Position.origin())
            .also(Cell::reveal)

        assertThat(cell.status).isEqualTo(CellStatus.revealed(0))
    }

    @Test
    fun reveal_empty_cell_counts_adjacent_mines() {
        val adjacentCells = listOf(
            Cell(Position.origin(), mined = false),
            Cell(Position.origin(), mined = true),
            Cell(Position.origin(), mined = false),
            Cell(Position.origin(), mined = true)
        )

        val cell = Cell(Position.origin(), adjacentCells = adjacentCells)
            .also(Cell::reveal)

        assertThat(cell.status).isEqualTo(CellStatus.revealed(2))
    }

    @Test
    fun reveals_adjacent_cells_when_surroundings_are_clear() {
        val cell = Cell(
            position = Position(1, 1),
            adjacentCells = listOf(
                Cell(Position(1, 2)),
                Cell(Position(2, 1))
            )
        )

        cell.reveal()

        val cellStatus = cell
            .adjacentCells
            .map { it.status.mineStatus }

        assertThat(cellStatus).containsExactly(
            MineStatus.REVEALED,
            MineStatus.REVEALED
        )
    }

    @Test
    fun do_not_reveal_adjacent_cells_when_surroundings_are_not_clear() {
        val cell = Cell(
            position = Position(1, 1),
            adjacentCells = listOf(
                Cell(Position(1, 2)),
                Cell(Position(2, 1), mined = true)
            )
        )

        cell.reveal()

        val cellStatus = cell
            .adjacentCells
            .map { it.status.mineStatus }

        assertThat(cellStatus).containsExactly(
            MineStatus.CONCEALED,
            MineStatus.CONCEALED
        )

    }

    @Test
    fun reveal_mined_Cell() {
        val cell = Cell(Position.origin(), mined = true)
            .also(Cell::reveal)

        assertThat(cell.status).isEqualTo(CellStatus.exploded())
    }
}