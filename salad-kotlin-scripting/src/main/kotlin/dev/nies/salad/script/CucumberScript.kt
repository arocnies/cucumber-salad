package dev.nies.salad.script

import dev.nies.salad.core.config.SaladConfig
import io.cucumber.java8.En
import kotlin.script.experimental.annotations.KotlinScript

const val CucumberScriptExtension = "step.kts"

@KotlinScript(fileExtension = CucumberScriptExtension)
open class CucumberScript : En {
    val salad: SaladConfig get() = SaladConfig.configProvider.saladConfig
}

