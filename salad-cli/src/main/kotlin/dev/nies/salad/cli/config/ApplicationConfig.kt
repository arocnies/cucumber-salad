package dev.nies.salad.cli.config

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.source.yaml
import com.uchuhimo.konf.toValue

data class ApplicationConfig(val cucumberSaladHome: String) {
    companion object {
        fun loadConfig(): ApplicationConfig = Config()
            .from.yaml.file("${System.getenv("CUCUMBER_SALAD_HOME")}/config.yml")
            .toValue()
    }
}