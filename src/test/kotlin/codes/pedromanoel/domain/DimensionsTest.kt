package codes.pedromanoel.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DimensionsTest {

    @Test
    fun iterate_over_positions() {
        val dimensions = Dimensions(3, 2)

        Assertions.assertThat(dimensions.positions)
            .containsExactly(
                Position(1, 1),
                Position(1, 2),
                Position(2, 1),
                Position(2, 2),
                Position(3, 1),
                Position(3, 2)
            )
    }
}