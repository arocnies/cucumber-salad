package dev.nies.salad.core.config

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.source.yaml
import com.uchuhimo.konf.toValue
import java.io.File

open class FileConfigProvider(private val path: String) : ConfigProvider() {
    override val saladConfig: SaladConfig
        get() = Config()
            .from.yaml.file(findSaladConfigFile(path))
            .toValue()

    protected fun findSaladConfigFile(saladConfigPath: String): File {
        val saladFile = File(saladConfigPath)
        // fixme: All uses of absolutePath fail to print path relative to current dir.
        if (!saladFile.exists()) throw ConfigurationException("Could not find config at ${saladFile.absolutePath}")
        if (!saladFile.canRead()) throw ConfigurationException("Could not read config at ${saladFile.absoluteFile}")
        return saladFile
    }

    protected fun Config.optionallyFromYml(ymlFile: String): Config {
        return if (File(ymlFile).canRead()) {
            println("Found default config at $ymlFile")
            this.from.yaml.file(ymlFile)
        } else this
    }
}