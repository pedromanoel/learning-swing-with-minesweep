package codes.pedromanoel.domain

class Cell(
    val position: Position,
    surroundingCells: List<Cell> = emptyList(),
    private val mined: Boolean = false
) {
    var status = CellStatus.concealed()

    val surroundingCells: List<Cell> get() = _surroundingCells

    private val _surroundingCells: ArrayList<Cell> = ArrayList()

    init {
        this._surroundingCells.addAll(surroundingCells)
        this._surroundingCells.forEach { it._surroundingCells.add(this) }
    }

    fun reveal() {
        status = when {
            mined -> CellStatus.exploded()
            else -> CellStatus.revealed(surroundingCells.count { it.mined })
        }

        if (surroundingsAreSafe()) {
            revealConcealedSurroundingCells()
        }
    }

    private fun surroundingsAreSafe() = status.numberOfSurroundingMines == 0

    private fun revealConcealedSurroundingCells() {
        surroundingCells
            .filter { it.status.mineStatus == MineStatus.CONCEALED }
            .forEach(Cell::reveal)
    }

    override fun toString() = "Cell($position, $status)"
}
