package dev.nies.salad.cli

import com.github.ajalt.clikt.core.CliktCommand
import java.io.File

class InitCommand : CliktCommand(help = helpText) {
    companion object {
        val helpText = "Creates an initial test example in the current directory"
    }

    override fun run() {
        createSaladConfig()
        createFeaturesDirectory()
        createStepDefsDirectory()
    }

    private fun createSaladConfig() {
        createFileIfNone("salad.yml", this::class.java.getResource("/salad.yml").readText())
    }

    private fun createFeaturesDirectory() {
        File("features").mkdir()
        createFileIfNone(
            "features/hello_world.feature",
            this::class.java.getResource("/features/hello_world.feature").readText()
        )
    }

    private fun createStepDefsDirectory() {
        File("steps").mkdir()
        createFileIfNone("steps/hello.step.kts", this::class.java.getResource("/steps/hello.step.kts").readText())
    }

    private fun createFileIfNone(fileName: String, content: String? = null) {
        val file = File(fileName)
        if (file.createNewFile()) content?.let {
            file.writeText(it)
        }
    }
}