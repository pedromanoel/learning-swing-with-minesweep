/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package codes.pedromanoel

import org.assertj.swing.core.GenericTypeMatcher
import org.assertj.swing.finder.WindowFinder.findFrame
import org.assertj.swing.fixture.FrameFixture
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase
import org.junit.Test
import java.awt.Frame

class AppTest : AssertJSwingJUnitTestCase() {
    private val frame: FrameFixture by lazy {
        findFrame(AppFrameMatcher()).using(robot())
    }

    override fun onSetUp() {
        main(arrayOf())
    }

    @Test
    fun testAppHasAGreeting() {
        frame.requireVisible()
    }
}

private class AppFrameMatcher : GenericTypeMatcher<Frame>(Frame::class.java) {

    override fun isMatching(frame: Frame?) =
        frame?.title == "Kotlin Minesweep" && frame.isVisible

}