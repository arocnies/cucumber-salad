package dev.nies.salad.script

import java.io.File
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.jvm.util.isError
import kotlin.script.experimental.jvm.util.isIncomplete

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
        checkScriptEvalResult(res)
        val c = res.valueOrThrow().returnValue.scriptClass ?: error("No class loader for script at $filePath")
        multiSourceClassLoader.classLoaders.add(c.java.classLoader)
    }

    private fun checkScriptEvalResult(result: ResultWithDiagnostics<EvaluationResult>) {
        if (result.isError() || result.isIncomplete()) {
            throw CucumberScriptException(
                "Failed to compile script ${result.reports.joinToString { it.sourcePath.toString() }}",
                CucumberScriptException(
                    result.reports
                        .filter { it.exception != null }
                        .joinToString { "${it.exception} : ${it.message}" }
                )
            )
        } else {
            val scriptResult = result.valueOrThrow().returnValue
            if (scriptResult is ResultValue.Error) throw CucumberScriptException(
                "Script execution failed",
                scriptResult.error
            )
        }
    }
}

class CucumberScriptException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception()

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