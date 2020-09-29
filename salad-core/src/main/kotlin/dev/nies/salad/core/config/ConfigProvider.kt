package dev.nies.salad.core.config

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.toValue

open class ConfigProvider {
    open val saladConfig: SaladConfig
        get() = getRawConfig().toValue()

    open fun getRawConfig(): Config = Config()

    /**
     * Returns a subconfig of properties at [path] mapped to the type [T].
     * Common usage is to map properties to a data class.
     */
    inline fun <reified T> getPropertyConfig(path: String): T? {
        return try {
            getRawConfig()
                .from.map.hierarchical(saladConfig.properties)
                .at(path)
                .toValue()
        } catch (e: Exception) {
            return null
        }
    }
}