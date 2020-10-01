package dev.nies.salad.script

import dev.nies.salad.script.kts.KtsGlueScriptCompilationConfiguration
import dev.nies.salad.script.kts.KtsGlueScriptEvaluationConfiguration
import java.io.File
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

fun evalFile(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {
    val compilationConfiguration = KtsGlueScriptCompilationConfiguration
    val evaluationConfiguration = KtsGlueScriptEvaluationConfiguration

    return BasicJvmScriptingHost().eval(scriptFile.toScriptSource(), compilationConfiguration, evaluationConfiguration)
}