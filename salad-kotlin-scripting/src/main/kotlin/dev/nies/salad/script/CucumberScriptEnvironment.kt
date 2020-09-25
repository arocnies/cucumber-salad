package dev.nies.salad.script

import java.io.File
import kotlin.script.experimental.api.valueOrThrow

class CucumberScriptEnvironment(rootClassLoader: ClassLoader = Thread.currentThread().contextClassLoader) {
    private val multiSourceClassLoader = AggregateClassLoader(rootClassLoader)
    val classLoader: ClassLoader = multiSourceClassLoader

    /**
     * Loads a kts script and adds it to the environment's classloader
     */
    fun loadScript(filePath: String) {
        val scriptFile = File(filePath)
        require(scriptFile.canRead())
        require(filePath.endsWith(".kts"))

        val res = evalFile(scriptFile)
        val c = res.valueOrThrow().returnValue.scriptClass ?: error("No class loader for script at $filePath")
        res.reports.forEach {
            println(" : ${it.message}" + if (it.exception == null) "" else ": ${it.exception}")
        }
        multiSourceClassLoader.classLoaders.add(c.java.classLoader)
    }
}

class AggregateClassLoader(parent: ClassLoader) : ClassLoader(parent) {
    val classLoaders = mutableListOf<ClassLoader>()
    override fun findClass(name: String?): Class<*> {
        classLoaders.forEach { cl ->
            runCatching {
                return cl.loadClass(name)
            }
        }
        throw ClassNotFoundException("Class $name not found!")
    }
}