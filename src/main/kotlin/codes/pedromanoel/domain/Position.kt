package codes.pedromanoel.domain

data class Position(val row: Int, val column: Int) {
    fun stepLeft(): Position = Position(row = row, column = column - 1)
    fun stepUp(): Position = Position(row = row - 1, column = column)
}