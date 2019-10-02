package codes.pedromanoel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CellTest {

    class Cell(
        private val mined: Boolean = false,
        private val position: Position = Position(0, 0),
        adjacentCells: List<Cell> = emptyList()
    ) {
        var exploded = false
        var adjacentMines = 0
        val adjacentCells = ArrayList<Cell>()

        init {
            this.adjacentCells.addAll(adjacentCells)
            this.adjacentCells.forEach { it.adjacentCells.add(this) }
        }

        fun reveal() {
            exploded = mined
            adjacentMines = adjacentCells.count { it.mined }
        }

        override fun toString(): String {
            val (y, x) = position
            return "Cell($y, $x)"
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
    fun setup_adjacent_mines() {
        val firstCell = Cell()
        val secondCell = Cell(adjacentCells = listOf(firstCell))

        assertThat(secondCell.adjacentCells).containsExactly(firstCell)
        assertThat(firstCell.adjacentCells).containsExactly(secondCell)
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

    data class Dimensions(val height: Int, val width: Int) {
        val positions
            get() = (1..height).flatMap { row ->
                (1..width).map { column ->
                    Position(row, column)
                }
            }
    }

    data class Position(val row: Int, val column: Int)

    @Test
    fun iterate_over_dimensions() {
        val dimensions = Dimensions(3, 2)

        assertThat(dimensions.positions)
            .containsExactly(
                Position(1, 1), Position(1, 2),
                Position(2, 1), Position(2, 2),
                Position(3, 1), Position(3, 2)
            )

    }

    class SquareMinefield(dimensions: Dimensions) {
        private val rows: HashMap<Position, Cell> = HashMap()

        init {
            dimensions.positions.forEach { p ->
                val adjacentCells = ArrayList<Cell>()

                cellAbove(p)?.also { adjacentCells.add(it) }
                cellLeft(p)?.also { adjacentCells.add(it) }

                rows[p] = Cell(position = p, adjacentCells = adjacentCells)
            }
        }

        private fun cellAbove(position: Position): Cell? =
            rows[Position(row = position.row - 1, column = position.column)]

        private fun cellLeft(position: Position): Cell? =
            rows[Position(row = position.row, column = position.column - 1)]

        fun cellAt(position: Position) =
            rows[position] ?: throw IndexOutOfBoundsException()
    }

    @Test
    fun initialize_minimum_square_minefield() {
        val minefield = SquareMinefield(Dimensions(1, 1))

        assertThat(minefield.cellAt(Position(1, 1)).adjacentCells).isEmpty()
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
}