package codes.pedromanoel.domain

data class CellStatus(
    val mineStatus: MineStatus,
    val numberOfSurroundingMines: Int
) {

    fun surroundingsAreSafe() =
        mineStatus == MineStatus.REVEALED && numberOfSurroundingMines == 0

    fun isRevealed() = mineStatus == MineStatus.REVEALED
    fun isConcealed() = mineStatus == MineStatus.CONCEALED
    fun isExploded() = mineStatus == MineStatus.EXPLODED

    companion object Factory {
        fun concealed(): CellStatus =
            CellStatus(MineStatus.CONCEALED, 0)

        fun exploded(): CellStatus =
            CellStatus(MineStatus.EXPLODED, 0)

        fun revealed(numberOfSurroundingMines: Int): CellStatus =
            CellStatus(MineStatus.REVEALED, numberOfSurroundingMines)
    }
}

enum class MineStatus { CONCEALED, EXPLODED, REVEALED }