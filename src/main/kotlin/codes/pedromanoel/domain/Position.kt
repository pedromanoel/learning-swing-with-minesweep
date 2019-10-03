package codes.pedromanoel.domain

data class Position(val row: Int, val column: Int) {
    fun stepLeft() = Position(row = row, column = column - 1)
    fun stepUp() = Position(row = row - 1, column = column)

    companion object Factory {
        fun origin() = Position(1, 1)
    }
}