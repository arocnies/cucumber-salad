package dev.nies

import dev.nies.salad.script.evalFile
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.script.experimental.api.valueOrThrow
import kotlin.test.Test


class GlueScriptTest {
    @Test
    fun `Feature file compiles class from script`(): Unit = runBlocking {
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