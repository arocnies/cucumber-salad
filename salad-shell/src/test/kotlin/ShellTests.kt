import dev.nies.salad.core.config.FileConfigProvider
import dev.nies.salad.core.config.SaladConfig
import dev.nies.salad.shell.ShellConfig
import dev.nies.salad.shell.ShellGroup
import dev.nies.salad.shell.loadShellConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShellTests {
    lateinit var shellConfig: ShellConfig

    @BeforeTest
    fun initSaladConfig() {
        val ref = object {}
        SaladConfig.configProvider = FileConfigProvider(ref::class.java.getResource("salad.yml").path)
        shellConfig = loadShellConfig()
    }

    @Test
    fun `ShellConfig loads workingDir and env`() {
        val shellConfig = loadShellConfig()
        assertTrue { shellConfig.workingDir == "./" }
        assertTrue { shellConfig.env["foo"] == "bar" }
    }

    @Test
    fun `ShellGroup starts all shells in the workingDir`() = runBlocking {
        val testFile = File("/tmp/shells/test.txt")
        testFile.mkdirs()
        testFile.createNewFile()

        val shells = ShellGroup(workingDirectory = "/tmp/shells/")
        val test = shells.getOrCreate("test")
        test.sendLine("ls")
        delay(100) // Making sure the shell has time to print

        assertEquals("test.txt", test.stdout)
    }
}