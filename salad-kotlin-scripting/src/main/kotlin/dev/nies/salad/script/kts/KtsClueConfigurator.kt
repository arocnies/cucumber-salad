package dev.nies.salad.script.kts

import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.FileBasedScriptSource
import kotlin.script.experimental.host.FileScriptSource

class KtsClueConfigurator : RefineScriptCompilationConfigurationHandler {
    override fun invoke(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> {
        return processAnnotations(context)
    }

    private fun processAnnotations(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> {
        val annotations = context.collectedData?.get(ScriptCollectedData.foundAnnotations).orEmpty()
        if (annotations.isEmpty()) return context.compilationConfiguration.asSuccess()

        val scriptBaseDir = (context.script as? FileBasedScriptSource)?.file?.parentFile
        val importedSources = annotations
            .filterIsInstance<Import>()
            .flatMap { it.paths.asSequence() }
            .map { FileScriptSource(scriptBaseDir?.resolve(it) ?: File(it)) }

        return ScriptCompilationConfiguration(context.compilationConfiguration) {
            if (importedSources.isNotEmpty()) {
                importScripts.append(importedSources)
                importedSources
                    .mapNotNull { it.getStarImport() }
                    .forEach { defaultImports.append(it) }
            }
        }.asSuccess()
    }
}

private fun FileScriptSource.getStarImport(): String? {
    val packageDeclaration = this.text.lineSequence()
        .firstOrNull { it.trim().startsWith("package ") }
        ?.substringAfter("package ")

    val className = this.name?.capitalize()
        ?.removeSuffix(".kts")
        ?.replace('.', '_')
        ?.replace('-', '_')

    return when {
        className != null && packageDeclaration != null -> "$packageDeclaration.$className.*"
        className != null -> "$className.*"
        else -> null
    }
}
