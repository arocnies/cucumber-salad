package io.cucumber.java8

import dev.nies.salad.script.AggregateClassLoader
import dev.nies.salad.script.kts.KtsGlueScript
import io.cucumber.core.backend.*
import io.cucumber.core.resource.ClasspathScanner
import io.cucumber.core.resource.ClasspathSupport
import java.lang.reflect.Field
import java.net.URI
import java.util.*
import java.util.function.Supplier

internal class KtsBackend(
    private val lookup: Lookup,
    private val container: Container,
    private val classLoaderProvider: Supplier<ClassLoader?>?
) : Backend {
    private val classFinder: ClasspathScanner = ClasspathScanner(classLoaderProvider)
    private val lambdaGlueClasses: MutableList<Class<out LambdaGlue?>> = ArrayList()
    private lateinit var glue: Glue

    override fun loadGlue(glue: Glue, gluePaths: List<URI>) {
        this.glue = glue
        // Scan for Java8 style glue (lambdas)
        gluePaths.asSequence()
            .filter { gluePath: URI ->
                ClasspathSupport.CLASSPATH_SCHEME == gluePath.scheme
            }.map { classpathResourceUri: URI? ->
                ClasspathSupport.packageName(
                    classpathResourceUri
                )
            }.map { basePackageName: String? ->
                classFinder.scanForSubClassesInPackage(
                    basePackageName,
                    LambdaGlue::class.java
                )
            }.flatMap { it }
            .filter { !it.isInterface }
            .filter { it.constructors.isNotEmpty() }
//            .onEach { classFinder.javaClass.classLoader }
            .forEach {
                container.addClass(it)
                lambdaGlueClasses.add(it)
            }

        fun extractClasses(cl: ClassLoader): List<Class<*>> {
            // Do the magic
            fun ClassLoader.classes(): Vector<Class<*>> {
                val f: Field = ClassLoader::class.java.getDeclaredField("classes")
                f.isAccessible = true
                @Suppress("UNCHECKED_CAST")
                return f.get(this) as Vector<Class<*>>
            }

            val outputClasses = mutableSetOf<Class<*>>()
            outputClasses.addAll(cl.classes())
            if (cl.parent != null) outputClasses.addAll(extractClasses(cl.parent))
            if (cl is AggregateClassLoader) cl.classLoaders.forEach {
                outputClasses.addAll(extractClasses(it))
            }

            return outputClasses.toList()
        }

        // Must run
        val candidates = extractClasses(classLoaderProvider!!.get()!!)
        candidates
            .asSequence()
            .filterNotNull()
            .filter { !it.isInterface }
            .filter { it.constructors.isNotEmpty() }
            .filter { it != KtsGlueScript::class.java }
            .filter { LambdaGlue::class.java.isAssignableFrom(it) }
            .map {
                @Suppress("UNCHECKED_CAST")
                it as Class<LambdaGlue>
            }
            .toCollection(lambdaGlueClasses)
    }

    override fun buildWorld() {
        // Instantiate all the stepdef classes for java8 - the stepdef will be
        // initialised
        // in the constructor.
        LambdaGlueRegistry.INSTANCE.set(GlueAdaptor(glue))
        for (lambdaGlueClass in lambdaGlueClasses) {
            lookup.getInstance(lambdaGlueClass)
        }
    }

    override fun disposeWorld() {
        LambdaGlueRegistry.INSTANCE.remove()
    }

    override fun getSnippet(): Snippet {
        return Java8Snippet()
    }

    private class GlueAdaptor constructor(private val glue: Glue) : LambdaGlueRegistry {
        override fun addStepDefinition(stepDefinition: StepDefinition) {
            glue.addStepDefinition(stepDefinition)
        }

        override fun addBeforeStepHookDefinition(beforeStepHook: HookDefinition) {
            glue.addBeforeStepHook(beforeStepHook)
        }

        override fun addAfterStepHookDefinition(afterStepHook: HookDefinition) {
            glue.addAfterStepHook(afterStepHook)
        }

        override fun addBeforeHookDefinition(beforeHook: HookDefinition) {
            glue.addBeforeHook(beforeHook)
        }

        override fun addAfterHookDefinition(afterHook: HookDefinition) {
            glue.addAfterHook(afterHook)
        }

        override fun addDocStringType(docStringType: DocStringTypeDefinition) {
            glue.addDocStringType(docStringType)
        }

        override fun addDataTableType(dataTableType: DataTableTypeDefinition) {
            glue.addDataTableType(dataTableType)
        }

        override fun addParameterType(parameterType: ParameterTypeDefinition) {
            glue.addParameterType(parameterType)
        }

        override fun addDefaultParameterTransformer(defaultParameterTransformer: DefaultParameterTransformerDefinition) {
            glue.addDefaultParameterTransformer(defaultParameterTransformer)
        }

        override fun addDefaultDataTableCellTransformer(
            defaultDataTableCellTransformer: DefaultDataTableCellTransformerDefinition
        ) {
            glue.addDefaultDataTableCellTransformer(defaultDataTableCellTransformer)
        }

        override fun addDefaultDataTableEntryTransformer(
            defaultDataTableEntryTransformer: DefaultDataTableEntryTransformerDefinition
        ) {
            glue.addDefaultDataTableEntryTransformer(defaultDataTableEntryTransformer)
        }
    }

}