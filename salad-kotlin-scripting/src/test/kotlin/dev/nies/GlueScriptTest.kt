package dev.nies

import dev.nies.salad.script.evalFile
import dev.nies.salad.script.kts.KtsGlueScriptCompilationConfiguration
import dev.nies.salad.script.kts.KtsGlueScriptEvaluationConfiguration
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class GlueScriptTest {
    @Test
    fun `Glue script file compiles class from script`(): Unit = runBlocking {
        val scriptFile = File(this::class.java.getResource("/dev.nies.script/steps/glueTest.step.kts").toURI())
        val res = evalFile(scriptFile)
        val c = res.valueOrThrow().returnValue.scriptClass ?: error("No class loader for script class?")
        println(res.toString())
        println(c)

        io.cucumber.core.cli.Main.run(
            arrayOf(
                "--glue",
                "dev.nies",
                "src/test/resources/dev.nies.script/features/hello_world.feature"
            ),
            c.java.classLoader
        )
    }

    @Test
    fun `Eval glue script with imports`() {
        val stepScript = File(this::class.java.getResource("/dev.nies.script/importer.step.kts").toURI())
        val res = BasicJvmScriptingHost().eval(
            stepScript.toScriptSource(),
            KtsGlueScriptCompilationConfiguration,
            KtsGlueScriptEvaluationConfiguration
        )

        val result = res.valueOrThrow().returnValue as? ResultValue.Value
        assertNotNull(result)
        assertEquals("Called thisFunctionIsImported", result.value)
    }
}

fun main(vararg args: String) {
    if (args.size != 1) {
        println("usage: <app> <script file>")
        println(File(".").absolutePath)
    } else {
        val scriptFile = File(args[0])
        println("Executing script $scriptFile")

        val res = evalFile(scriptFile)

        res.reports.forEach {
            println(" : ${it.message}" + if (it.exception == null) "" else ": ${it.exception}")
        }
    }
}