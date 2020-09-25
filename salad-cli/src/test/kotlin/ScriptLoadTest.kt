import com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit
import com.uchuhimo.konf.Config
import com.uchuhimo.konf.toValue
import dev.nies.salad.cli.CucumberSaladCommand
import dev.nies.salad.cli.config.ApplicationConfig
import kotlin.test.Test
import kotlin.test.assertEquals

class ScriptLoadTest {
    private val testConfig = Config()
        .from.map.hierarchical(mapOf("cucumberSaladHome" to "src/main/resources/salad.yml"))
        .toValue<ApplicationConfig>()

    @Test
    fun `Loading default init works as expected`() {
        val exitStatus = catchSystemExit {
            CucumberSaladCommand(testConfig).main(listOf("-c", "src/main/resources/salad.yml", "--stacktrace"))
        }

        assertEquals(0, exitStatus)
    }

    @Test
    fun `Loading external steps from Jars in a directory`() {
        val exitStatus = catchSystemExit {
            CucumberSaladCommand(testConfig).main(
                listOf(
                    "-c",
                    "build/resources/testSteps/salad_jar_steps.yml",
                    "--stacktrace"
                )
            )
        }

        assertEquals(0, exitStatus)
    }
}