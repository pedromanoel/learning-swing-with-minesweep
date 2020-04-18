package codes.pedromanoel.domain

interface Minefield {
    val dimensions: Dimensions
    fun cellAt(position: Position): Cell
    fun isCleared(): Boolean
}