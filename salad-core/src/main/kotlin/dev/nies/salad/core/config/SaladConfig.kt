package dev.nies.salad.core.config

data class SaladConfig internal constructor(
    val features: List<String> = emptyList(),
    val properties: Map<String, Any> = emptyMap(),
    val plugins: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val cucumberArgs: List<String> = emptyList()
) {
    companion object {
        var configProvider: ConfigProvider = ConfigProvider()
    }
}

open class ConfigurationException(message: String = "Failed to load config", cause: Throwable? = null) :
    Exception(message, cause)

class PropertyConfigException(cause: Throwable?, path: String) :
    ConfigurationException("Failed to load property config from \"$path\"", cause)
