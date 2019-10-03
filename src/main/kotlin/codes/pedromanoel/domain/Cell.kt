package codes.pedromanoel.domain

class Cell(
    val position: Position,
    adjacentCells: List<Cell> = emptyList(),
    private val mined: Boolean = false
) {
    var status = CellStatus.concealed()

    val adjacentCells: List<Cell> get() = _adjacentCells

    private val _adjacentCells: ArrayList<Cell> = ArrayList()

    init {
        this._adjacentCells.addAll(adjacentCells)
        this._adjacentCells.forEach { it._adjacentCells.add(this) }
    }

    fun reveal() {
        status = when {
            mined -> CellStatus.exploded()
            else -> CellStatus.revealed(adjacentCells.count { it.mined })
        }
    }

    override fun toString() = "Cell($position, $status)"
}
