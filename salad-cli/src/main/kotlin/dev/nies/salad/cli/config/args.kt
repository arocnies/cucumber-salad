package dev.nies.salad.cli.config

import dev.nies.salad.cli.endingWith
import dev.nies.salad.core.config.SaladConfig
import java.nio.file.FileSystems


/**
 * Creates an arg array for the CucumberCLI from a given [SaladConfig].
 */
internal fun SaladConfig.buildArgs(rootPath: String): Array<String> {
    val args = mutableListOf<String>()

    args.addAll(pluginsToArgArray())
    args.addAll(cucumberArgsToArgArray())
    args.addAll(featuresToArgArray(rootPath))

    return args.toTypedArray()
}

private fun SaladConfig.pluginsToArgArray(): Array<String> {
    return plugins.flatMap { listOf("-p", it) }.toTypedArray()
}

private fun SaladConfig.cucumberArgsToArgArray(): Array<String> {
    return cucumberArgs.flatMap { it.split(' ') }.toTypedArray()
}

private fun SaladConfig.featuresToArgArray(rootPath: String): Array<String> {
    return features.map { featurePath ->
        println("Including feature: ${rootPath.endingWith(FileSystems.getDefault().separator) + featurePath}")
        rootPath.endingWith(FileSystems.getDefault().separator) + featurePath
    }.toTypedArray()
}