import dev.nies.salad.core.config.FileConfigProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SaladConfigTest {
    @Test
    fun `salad_yml is loaded from file`() {
        val ref = object {}
        val config = FileConfigProvider(ref::class.java.getResource("salad.yml").path).saladConfig

        assertEquals(listOf("noop.feature", "noop2.feature"), config.features)
        assertEquals("pretty", config.plugins.first())
        assertTrue { config.properties.get("num") == 5 }
    }
}