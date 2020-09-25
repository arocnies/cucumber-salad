package dev.nies.salad.shell

import dev.nies.salad.core.config.SaladConfig

data class ShellConfig(val workingDir: String, val env: Map<String, String>)

fun loadShellConfig(): ShellConfig {
    return SaladConfig.configProvider.getPropertyConfig("shell")
}