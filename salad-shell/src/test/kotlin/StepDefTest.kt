import dev.nies.salad.core.config.FileConfigProvider
import dev.nies.salad.core.config.SaladConfig

fun main(args: Array<String>) {
    val ref = object {}
    SaladConfig.configProvider = FileConfigProvider(ref::class.java.getResource("salad.yml").path)
    io.cucumber.core.cli.Main.main(*args)
}