package dev.nies.salad.cli.config

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.source.yaml
import com.uchuhimo.konf.toValue
import dev.nies.salad.core.config.FileConfigProvider
import dev.nies.salad.core.config.SaladConfig

class CliConfigProvider(private val appConfig: ApplicationConfig, private val pathArg: String) :
    FileConfigProvider(pathArg) {
    override val saladConfig: SaladConfig
        get() = Config()
            .optionallyFromYml(appConfig.cucumberSaladHome)
            .from.yaml.file(findSaladConfigFile(pathArg))
            .toValue()
}