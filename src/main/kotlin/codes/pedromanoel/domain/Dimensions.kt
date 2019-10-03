package codes.pedromanoel.domain

data class Dimensions(val height: Int, val width: Int) {

    val positions
        get() = (1..height).flatMap { row ->
            (1..width).map { column ->
                Position(row, column)
            }
        }
}