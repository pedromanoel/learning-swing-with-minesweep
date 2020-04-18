package codes.pedromanoel.application

import codes.pedromanoel.domain.Minefield
import java.io.PrintStream

class MinefieldConsolePrinter(
    private val minefield: Minefield,
    private val out: PrintStream
) {
    fun print() {
        val (height, width) = minefield.dimensions
        val columnsHeader = (1..width).joinToString(" ")

        val rows = (1..height).joinToString("\n") { row ->
            " $row | ${Array(width) { "-" }.joinToString(" ")}"
        }

        out.println("""
            |
            |     $columnsHeader
            |   +-${"-".repeat(columnsHeader.length)}-
            |$rows
        """.trimMargin())
    }
}