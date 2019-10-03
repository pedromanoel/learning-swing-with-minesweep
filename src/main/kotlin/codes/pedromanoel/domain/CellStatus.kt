package codes.pedromanoel.domain

data class CellStatus(val mineStatus: MineStatus, val numberOfSurroundingMines: Int) {

    companion object Factory {
        fun concealed(): CellStatus =
            CellStatus(MineStatus.CONCEALED, 0)

        fun exploded(): CellStatus =
            CellStatus(MineStatus.EXPLODED, 0)

        fun revealed(numberOfSurroundingMines: Int): CellStatus =
            CellStatus(MineStatus.REVEALED, numberOfSurroundingMines)
    }
}