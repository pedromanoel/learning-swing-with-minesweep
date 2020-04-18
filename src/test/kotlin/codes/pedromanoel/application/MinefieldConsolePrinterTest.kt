package codes.pedromanoel.application

import codes.pedromanoel.domain.Dimensions
import codes.pedromanoel.domain.MineDeployment
import codes.pedromanoel.domain.SquareMinefield
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MinefieldConsolePrinterTest {

    @Test
    fun `print minefield`() {
        val outputStream = ByteArrayOutputStream()

        printerTo(outputStream).print()

        assertThat(outputStream.toString())
            .isEqualTo("""
                |
                |     1 2 3
                |   +-------
                | 1 | - - -
                | 2 | - - -
                | 3 | - - -
                |
            """.trimMargin())
    }

    private fun printerTo(outputStream: ByteArrayOutputStream): MinefieldConsolePrinter {
        val dimensions = Dimensions(3, 3)
        val mineDeployment = MineDeployment.empty()
        val minefield = SquareMinefield(dimensions, mineDeployment)


        return MinefieldConsolePrinter(
            minefield,
            PrintStream(outputStream)
        )
    }
}