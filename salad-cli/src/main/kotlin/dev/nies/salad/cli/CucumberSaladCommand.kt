package dev.nies.salad.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import dev.nies.salad.cli.config.ApplicationConfig
import dev.nies.salad.cli.config.CliConfigProvider
import dev.nies.salad.cli.config.buildArgs
import dev.nies.salad.core.config.SaladConfig
import java.io.File
import kotlin.system.exitProcess

class CucumberSaladCommand(private val appConfig: ApplicationConfig) : CliktCommand() {
    private val configPath by option("-c", "--config", help = "path to salad.yml config file. Default ./salad.yml")
        .default("salad.yml")
    private val stacktrace: Boolean by option("--stacktrace", help = "print stacktraces").flag()
    private val init: Boolean by option("--init", help = InitCommand.helpText).flag()
    private val version: Boolean by option("-v", "--version", help = "show version").flag()

    override fun run() {
        try {
            when {
                version -> println("Version: $versionText")
                init -> InitCommand().run()
                else -> {
                    loadSaladConfig()
                    runTests()
                }
            }
        } catch (e: Exception) {
            printError(e)
            exitProcess(1)
        }
    }

    private val versionText: String = this::class.java.getResource("version.txt")?.readText() ?: "UNSPECIFIED"

    private fun runTests() {
        val saladRootDir = File(configPath).absoluteFile.parent
        val args = SaladConfig.configProvider.saladConfig.buildArgs(saladRootDir)

        val exitStatus = io.cucumber.core.cli.Main.run(args, getStepsClassLoader(saladRootDir))
        exitProcess(exitStatus.toInt())
    }

    private fun loadSaladConfig() {
        SaladConfig.configProvider = CliConfigProvider(appConfig, pathArg = configPath)
    }

    private fun printError(e: Exception) {
        if (stacktrace) throw e
        else {
            println("Error: ${e.message}")
            if (e.cause != null) println("Cause: ${e.message}")
            println("Use --stacktrace to print errors")
        }
    }
}

