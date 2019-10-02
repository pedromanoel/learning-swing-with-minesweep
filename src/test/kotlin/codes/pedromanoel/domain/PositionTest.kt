package codes.pedromanoel.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionTest {

    @Test
    fun return_left_of_current_position() {
        assertThat(Position(4, 3).stepLeft()).isEqualTo(Position(4, 2))
    }

    @Test
    fun return_up_of_current_position() {
        assertThat(Position(4, 3).stepUp()).isEqualTo(Position(3, 3))
    }
}