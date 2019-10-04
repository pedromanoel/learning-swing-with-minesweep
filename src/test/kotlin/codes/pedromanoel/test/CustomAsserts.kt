package codes.pedromanoel.test

import codes.pedromanoel.domain.Cell
import codes.pedromanoel.domain.Minefield


fun assertThat(actual: Cell) = CellAssert(actual)
fun assertThat(actual: Minefield) = MinefieldAssert(actual)
