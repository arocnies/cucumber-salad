package dev.nies.salad.script

import java.io.File
import kotlin.script.experimental.api.CompiledScript
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.util.classpathFromClass
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

suspend fun loadClass(scriptFile: File): ResultWithDiagnostics<CompiledScript> {
    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<CucumberScript> {
        jvm {
//            dependenciesFromClassContext(io.cucumber.core.cli.Main::class, wholeClasspath = true)
            dependenciesFromClassContext(wholeClasspath = true, contextClass = this::class)
//            data[baseClass] = Thread.currentThread().contextClassLoader
//            dependenciesFromCurrentContext(wholeClasspath = true)
        }
    }
    return BasicJvmScriptingHost().compiler(scriptFile.toScriptSource(), compilationConfiguration)
}

fun evalFile(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {
    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<CucumberScript> {
        jvm {
            // configure dependencies for compilation, they should contain at least the script base class and
            // its dependencies
            // variant 1: try to extract current classpath and take only a path to the specified "script.jar"
//            dependenciesFromCurrentContext(
//                "script" /* script library jar name (exact or without a version) */
//            )
//            dependenciesFromCurrentContext(
//                "salad-kotlin-scripting",
//                "salad-core",
//                "cucumber-java8", /* script library jar name (exact or without a version) */
//                "kotlin-test",
//                "kotlin-test-junit"
//            )

            // variant 2: try to extract current classpath and use it for the compilation without filtering
            dependenciesFromCurrentContext(wholeClasspath = true)
            // variant 3: try to extract a classpath from a particular classloader (or Thread.contextClassLoader by default)
            // filtering as in the variat 1 is supported too
//            dependenciesFromClassloader(classLoader = io.cucumber.core.cli.Main::class.java.classLoader, wholeClasspath = true)
            // variant 4: explicit classpath
//            updateClasspath(listOf(File("/path/to/jar")))

//            dependenciesFromClassloader(wholeClasspath = true) failed
//            dependenciesFromClassContext(io.cucumber.core.cli.Main::class, wholeClasspath = true)
        }
    }
    val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<CucumberScript> {
        jvm {
            classpathFromClass<CucumberScript>()
        }
    }

    return BasicJvmScriptingHost().eval(scriptFile.toScriptSource(), compilationConfiguration, evaluationConfiguration)
}