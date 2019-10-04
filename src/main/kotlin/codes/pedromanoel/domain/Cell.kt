package codes.pedromanoel.domain

class Cell(
    val position: Position,
    surroundingCells: List<Cell> = emptyList(),
    private val mined: Boolean = false
) {
    var status = CellStatus.concealed()
        private set

    val surroundingCells: List<Cell>
        get() = _surroundingCells.toList()

    private val _surroundingCells: MutableList<Cell> =
        surroundingCells.toMutableList()

    init {
        addSelfToSurroundingCells()
    }

    private fun addSelfToSurroundingCells() {
        this._surroundingCells
            .forEach { other -> other._surroundingCells.add(this) }
    }

    fun reveal() {
        status = when {
            mined -> CellStatus.exploded()
            else -> CellStatus.revealed(surroundingCells.count { it.mined })
        }

        if (status.surroundingsAreSafe()) {
            revealConcealedSurroundingCells()
        }
    }

    private fun revealConcealedSurroundingCells() {
        surroundingCells
            .filter { it.status.isConcealed() }
            .forEach(Cell::reveal)
    }

    override fun toString() = "Cell($position, $status)"
}
