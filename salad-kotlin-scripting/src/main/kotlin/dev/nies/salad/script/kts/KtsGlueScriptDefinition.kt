package dev.nies.salad.script.kts

import io.cucumber.java8.En
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.util.classpathFromClass

object KtsGlueScriptCompilationConfiguration : ScriptCompilationConfiguration({
    defaultImports(Import::class, CompilerOptions::class, KtsGlueScript::class)
    baseClass(KtsGlueScript::class)

    jvm {
        dependenciesFromClassContext(KtsGlueScript::class, wholeClasspath = true)
        dependenciesFromClassContext(En::class, wholeClasspath = true)
    }

    refineConfiguration {
        onAnnotations(Import::class, CompilerOptions::class, handler = KtsClueConfigurator())
    }

    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
})

object KtsGlueScriptEvaluationConfiguration : ScriptEvaluationConfiguration({
    // Instance sharing causes a stackOverflow.
    scriptsInstancesSharing(false)
    jvm {
        classpathFromClass<KtsGlueScript>()
    }
})