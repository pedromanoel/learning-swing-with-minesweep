package codes.pedromanoel.domain

interface Minefield {
    fun cellAt(position: Position): Cell
}