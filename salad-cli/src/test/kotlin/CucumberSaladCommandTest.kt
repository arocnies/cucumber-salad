import com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit
import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import com.uchuhimo.konf.Config
import com.uchuhimo.konf.toValue
import dev.nies.salad.cli.CucumberSaladCommand
import dev.nies.salad.cli.config.ApplicationConfig
import kotlin.test.*

class CucumberSaladCommandTest {
    private val testConfig = Config()
        .from.map.hierarchical(mapOf("cucumberSaladHome" to ""))
        .toValue<ApplicationConfig>()

    @Test
    fun `print usage on --help`() {
        val exitCode = catchSystemExit {
            val output = tapSystemOut {
                CucumberSaladCommand(testConfig).main(arrayOf("--help"))
            }
            assertTrue { "Usage:" in output }
        }
        assertEquals(0, exitCode)
    }

    @Test
    fun `salad_yml is loaded from local dir`() {
        var exitCode: Int? = null
        val output = tapSystemOut {
            exitCode = catchSystemExit {
                CucumberSaladCommand(testConfig).main(arrayOf("-c", "src/test/resources/salad.yml", "--stacktrace"))
            }
        }
        println(output)
        assertTrue { "Scenario: Test Scenario #1" in output }
        assertTrue { "Scenario: Test Scenario #2" in output }
        assertTrue { "Hello from FIRST" in output }
        assertTrue { "Hello from SECOND" in output }
        assertTrue { "simple=foobar" in output }
        assertEquals(0, exitCode)
    }

    @Test
    fun `cucumber tests failing returns a non-zero exit code`() {
        var exitCode: Int? = null
        val output = tapSystemOut {
            exitCode = catchSystemExit {
                CucumberSaladCommand(testConfig).main(
                    arrayOf(
                        "-c",
                        "src/test/resources/salad-failing.yml",
                        "--stacktrace"
                    )
                )
            }
        }
        assertNotEquals(0, exitCode)
        assertTrue { "Scenario: Steps not defined" in output }
        assertTrue { "Scenario: Test Scenario #1" in output }
        assertFalse { "Scenario: Test Scenario #2" in output }
    }

    @Test
    fun `Version is printed`() {
        val exitCode: Int? = null
        val output = tapSystemOut {
            CucumberSaladCommand(testConfig).main(arrayOf("-v", "--stacktrace"))
        }
        println(output)
        assertNotEquals(0, exitCode)
        assertFalse { output.isNullOrBlank() }
    }
}