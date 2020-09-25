package dev.nies.salad.cli

import dev.nies.salad.cli.config.ApplicationConfig

fun main(args: Array<String>) {
    CucumberSaladCommand(ApplicationConfig.loadConfig()).main(args)
}