package codes.pedromanoel.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CellTest {

    @Test
    fun a_cell_is_created_concealed() {
        assertThat(Cell(Position.origin()).status).isEqualTo(CellStatus.concealed())
    }

    @Test
    fun a_cell_surrounds_one_another() {
        val firstCell = Cell(Position.origin())
        val secondCell =
            Cell(Position.origin(), surroundingCells = listOf(firstCell))

        assertThat(secondCell.surroundingCells).containsExactly(firstCell)
        assertThat(firstCell.surroundingCells).containsExactly(secondCell)
    }

    @Test
    fun revealing_an_empty_cell_returns_the_revealed_status() {
        val cell = Cell(Position.origin())
            .also(Cell::reveal)

        assertThat(cell.status).isEqualTo(CellStatus.revealed(0))
    }

    @Test
    fun revealing_an_empty_cell_counts_surrounding_mines() {
        val surroundingCells = listOf(
            Cell(Position.origin(), mined = false),
            Cell(Position.origin(), mined = true),
            Cell(Position.origin(), mined = false),
            Cell(Position.origin(), mined = true)
        )

        val cell = Cell(Position.origin(), surroundingCells = surroundingCells)
            .also(Cell::reveal)

        assertThat(cell.status.numberOfSurroundingMines).isEqualTo(2)
    }

    @Test
    fun revealing_an_empty_cell_reveals_surroundings_when_safe() {
        val cell = Cell(
            position = Position(1, 1),
            surroundingCells = listOf(
                Cell(Position(1, 2)),
                Cell(Position(2, 1))
            )
        )

        cell.reveal()

        val cellStatus = cell
            .surroundingCells
            .map { it.status.isRevealed() }

        assertThat(cellStatus).containsOnly(true)
    }

    @Test
    fun revealing_a_cell_with_a_mine_does_not_reveal_surroundings() {
        val cell = Cell(
            mined = true,
            position = Position(1, 1),
            surroundingCells = listOf(
                Cell(Position(1, 2)),
                Cell(Position(2, 1))
            )
        )

        cell.reveal()

        val cellStatus = cell
            .surroundingCells
            .map { it.status.isConcealed() }

        assertThat(cellStatus).containsOnly(true)
    }

    @Test
    fun revealing_empty_cell_does_not_clear_surroundings_if_there_is_a_mine() {
        val cell = Cell(
            position = Position(1, 1),
            surroundingCells = listOf(
                Cell(Position(1, 2)),
                Cell(Position(2, 1), mined = true)
            )
        )

        cell.reveal()

        val cellStatus = cell
            .surroundingCells
            .map { it.status.isConcealed() }

        assertThat(cellStatus).containsOnly(true)

    }

    @Test
    fun revealing_a_cell_with_a_mine_returns_status_exploded() {
        val cell = Cell(Position.origin(), mined = true)
            .also(Cell::reveal)

        assertThat(cell.status).isEqualTo(CellStatus.exploded())
    }
}