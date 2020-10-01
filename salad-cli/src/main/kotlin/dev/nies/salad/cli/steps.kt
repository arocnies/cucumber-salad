package dev.nies.salad.cli

import dev.nies.salad.core.config.SaladConfig
import dev.nies.salad.script.AggregateClassLoader
import dev.nies.salad.script.CucumberScriptEnvironment
import dev.nies.salad.script.kts.KTS_GLUE_EXTENSION
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.FileSystems
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile

fun getStepsClassLoader(rootPath: String): ClassLoader {
    val stepFiles = SaladConfig.configProvider.saladConfig.steps.map { stepPath ->
        val adjustedPath = rootPath.endingWith(FileSystems.getDefault().separator) + stepPath
        File(adjustedPath)
    }

    val rootClassLoader = Thread.currentThread().contextClassLoader
    return rootClassLoader.withStepsFrom(*stepFiles.toTypedArray())
}

fun ClassLoader.withStepsFrom(vararg files: File): ClassLoader {
    return files
        .map { file ->
            println("Loading steps from: ${file.path}")
            when {
                file.isDirectory -> withStepsFromDirectory(file)
                file.name.endsWith(KTS_GLUE_EXTENSION) -> withStepsFromKtsScript(file)
                file.name.endsWith(".jar") -> withStepsFromJar(file)
                else -> {
                    println("Steps at ${file.path} not supported")
                    EmptyClassLoader
                }
            }
        }
        .ifEmpty { listOf(EmptyClassLoader) }
        .reduce { acc, classLoader ->
            AggregateClassLoader(this).apply {
                classLoaders += acc
                classLoaders += classLoader
            }
        }
}

private fun ClassLoader.withStepsFromDirectory(dir: File): ClassLoader {
    require(dir.isDirectory)
    return dir.listFiles()
        ?.map { withStepsFrom(it) }
        ?.reduce { acc, classLoader ->
            AggregateClassLoader(this).apply {
                classLoaders += acc
                classLoaders += classLoader
            }
        } ?: EmptyClassLoader
}

private fun ClassLoader.withStepsFromKtsScript(ktsScript: File): ClassLoader {
    require(ktsScript.isFile)

    return with(CucumberScriptEnvironment(this)) {
        loadScript(ktsScript.absolutePath)
        classLoader
    }
}

private fun ClassLoader.withStepsFromJar(jar: File): ClassLoader {
    require(jar.isFile)

    val jarFile = JarFile(jar)
    val e: Enumeration<JarEntry> = jarFile.entries()

    val urls: Array<URL> = arrayOf(jar.toURI().toURL())
    val urlClassLoader = URLClassLoader(urls)

    while (e.hasMoreElements()) {
        val je = e.nextElement()
        if (je.isDirectory || !je.name.endsWith(".class")) {
            continue
        }
        // -6 because of .class
        var className = je.name.substring(0, je.name.length - 6)
        className = className.replace('/', '.')

        // We want to load the class for the side effects on the classloader.
        urlClassLoader.loadClass(className)
    }

    return AggregateClassLoader(this).apply {
        classLoaders += urlClassLoader
    }
}

private object EmptyClassLoader : ClassLoader()