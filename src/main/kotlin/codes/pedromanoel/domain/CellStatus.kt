package codes.pedromanoel.domain

data class CellStatus(val mineStatus: MineStatus, val adjacentMines: Int) {

    companion object Factory {
        fun concealed(): CellStatus =
            CellStatus(MineStatus.CONCEALED, 0)

        fun exploded(): CellStatus =
            CellStatus(MineStatus.EXPLODED, 0)

        fun revealed(adjacentMines: Int): CellStatus =
            CellStatus(MineStatus.REVEALED, adjacentMines)
    }
}