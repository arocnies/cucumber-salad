package dev.nies.salad.script.kts

import dev.nies.salad.core.config.SaladConfig
import dev.nies.salad.script.CucumberScriptException
import io.cucumber.java8.*
import kotlin.script.experimental.annotations.KotlinScript

/**
 * Import other script(s)
 */
@Target(AnnotationTarget.FILE)
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class Import(vararg val paths: String)

/**
 * Compiler options that will be applied on script compilation
 *
 * @see [kotlin.script.experimental.api.compilerOptions]
 */
@Target(AnnotationTarget.FILE)
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class CompilerOptions(vararg val options: String)

const val KTS_GLUE_EXTENSION = "step.kts"

@KotlinScript(
    fileExtension = KTS_GLUE_EXTENSION,
    compilationConfiguration = KtsGlueScriptCompilationConfiguration::class,
    evaluationConfiguration = KtsGlueScriptEvaluationConfiguration::class
)
open class KtsGlueScript : En {
    val salad: SaladConfig get() = SaladConfig.configProvider.saladConfig

    private fun checkCucumberState() {
        if (!isCucumberInitialized) {
            throw CucumberScriptException(
                "Attempting to register glue code (Given/When/Then... etc) before Cucumber is " +
                        "initialized. Check that Cucumber is started either via runner or CLI."
            )
        }
    }

    override fun Before(body: HookBody?) {
        if (isCucumberInitialized) super.Before(body)
    }

    override fun Before(tagExpression: String?, body: HookBody?) {
        if (isCucumberInitialized) super.Before(tagExpression, body)
    }

    override fun Before(order: Int, body: HookBody?) {
        if (isCucumberInitialized) super.Before(order, body)
    }

    override fun Before(tagExpression: String?, order: Int, body: HookBody?) {
        if (isCucumberInitialized) super.Before(tagExpression, order, body)
    }

    override fun Before(body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.Before(body)
    }

    override fun Before(tagExpression: String?, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.Before(tagExpression, body)
    }

    override fun Before(order: Int, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.Before(order, body)
    }

    override fun Before(tagExpression: String?, order: Int, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.Before(tagExpression, order, body)
    }

    override fun BeforeStep(body: HookBody?) {
        if (isCucumberInitialized) super.BeforeStep(body)
    }

    override fun BeforeStep(tagExpression: String?, body: HookBody?) {
        if (isCucumberInitialized) super.BeforeStep(tagExpression, body)
    }

    override fun BeforeStep(order: Int, body: HookBody?) {
        if (isCucumberInitialized) super.BeforeStep(order, body)
    }

    override fun BeforeStep(tagExpression: String?, order: Int, body: HookBody?) {
        if (isCucumberInitialized) super.BeforeStep(tagExpression, order, body)
    }

    override fun BeforeStep(body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.BeforeStep(body)
    }

    override fun BeforeStep(tagExpression: String?, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.BeforeStep(tagExpression, body)
    }

    override fun BeforeStep(order: Int, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.BeforeStep(order, body)
    }

    override fun BeforeStep(tagExpression: String?, order: Int, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.BeforeStep(tagExpression, order, body)
    }

    override fun After(body: HookBody?) {
        if (isCucumberInitialized) super.After(body)
    }

    override fun After(tagExpression: String?, body: HookBody?) {
        if (isCucumberInitialized) super.After(tagExpression, body)
    }

    override fun After(order: Int, body: HookBody?) {
        if (isCucumberInitialized) super.After(order, body)
    }

    override fun After(tagExpression: String?, order: Int, body: HookBody?) {
        if (isCucumberInitialized) super.After(tagExpression, order, body)
    }

    override fun After(body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.After(body)
    }

    override fun After(tagExpression: String?, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.After(tagExpression, body)
    }

    override fun After(order: Int, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.After(order, body)
    }

    override fun After(tagExpression: String?, order: Int, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.After(tagExpression, order, body)
    }

    override fun AfterStep(body: HookBody?) {
        if (isCucumberInitialized) super.AfterStep(body)
    }

    override fun AfterStep(tagExpression: String?, body: HookBody?) {
        if (isCucumberInitialized) super.AfterStep(tagExpression, body)
    }

    override fun AfterStep(order: Int, body: HookBody?) {
        if (isCucumberInitialized) super.AfterStep(order, body)
    }

    override fun AfterStep(tagExpression: String?, order: Int, body: HookBody?) {
        if (isCucumberInitialized) super.AfterStep(tagExpression, order, body)
    }

    override fun AfterStep(body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.AfterStep(body)
    }

    override fun AfterStep(tagExpression: String?, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.AfterStep(tagExpression, body)
    }

    override fun AfterStep(order: Int, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.AfterStep(order, body)
    }

    override fun AfterStep(tagExpression: String?, order: Int, body: HookNoArgsBody?) {
        if (isCucumberInitialized) super.AfterStep(tagExpression, order, body)
    }

    override fun DocStringType(contentType: String?, body: DocStringDefinitionBody<*>?) {
        if (isCucumberInitialized) super.DocStringType(contentType, body)
    }

    override fun <T : Any?> DataTableType(body: DataTableEntryDefinitionBody<T>?) {
        if (isCucumberInitialized) super.DataTableType(body)
    }

    override fun <T : Any?> DataTableType(replaceWithEmptyString: String?, body: DataTableEntryDefinitionBody<T>?) {
        if (isCucumberInitialized) super.DataTableType(replaceWithEmptyString, body)
    }

    override fun <T : Any?> DataTableType(body: DataTableRowDefinitionBody<T>?) {
        if (isCucumberInitialized) super.DataTableType(body)
    }

    override fun <T : Any?> DataTableType(replaceWithEmptyString: String?, body: DataTableRowDefinitionBody<T>?) {
        if (isCucumberInitialized) super.DataTableType(replaceWithEmptyString, body)
    }

    override fun <T : Any?> DataTableType(body: DataTableCellDefinitionBody<T>?) {
        if (isCucumberInitialized) super.DataTableType(body)
    }

    override fun <T : Any?> DataTableType(replaceWithEmptyString: String?, body: DataTableCellDefinitionBody<T>?) {
        if (isCucumberInitialized) super.DataTableType(replaceWithEmptyString, body)
    }

    override fun <T : Any?> DataTableType(body: DataTableDefinitionBody<T>?) {
        if (isCucumberInitialized) super.DataTableType(body)
    }

    override fun <T : Any?> DataTableType(replaceWithEmptyString: String?, body: DataTableDefinitionBody<T>?) {
        if (isCucumberInitialized) super.DataTableType(replaceWithEmptyString, body)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A1<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A2<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A3<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A4<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A5<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A6<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A7<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A8<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A9<R>?
    ) {
        if (isCucumberInitialized) super.ParameterType(name, regex, definitionBody)
    }

    override fun DefaultParameterTransformer(definitionBody: DefaultParameterTransformerBody?) {
        if (isCucumberInitialized) super.DefaultParameterTransformer(definitionBody)
    }

    override fun DefaultDataTableCellTransformer(definitionBody: DefaultDataTableCellTransformerBody?) {
        if (isCucumberInitialized) super.DefaultDataTableCellTransformer(definitionBody)
    }

    override fun <T : Any?> DefaultDataTableCellTransformer(
        replaceWithEmptyString: String?,
        definitionBody: DefaultDataTableCellTransformerBody?
    ) {
        if (isCucumberInitialized) super.DefaultDataTableCellTransformer<T>(replaceWithEmptyString, definitionBody)
    }

    override fun DefaultDataTableEntryTransformer(definitionBody: DefaultDataTableEntryTransformerBody?) {
        if (isCucumberInitialized) super.DefaultDataTableEntryTransformer(definitionBody)
    }

    override fun <T : Any?> DefaultDataTableEntryTransformer(
        replaceWithEmptyString: String?,
        definitionBody: DefaultDataTableEntryTransformerBody?
    ) {
        if (isCucumberInitialized) super.DefaultDataTableEntryTransformer<T>(replaceWithEmptyString, definitionBody)
    }

    override fun And(expression: String?, body: StepDefinitionBody.A0?) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?> And(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> And(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> And(expression: String?, body: StepDefinitionBody.A3<T1, T2, T3>?) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        if (isCucumberInitialized) super.And(expression, body)
    }

    override fun But(expression: String?, body: StepDefinitionBody.A0?) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?> But(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> But(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> But(expression: String?, body: StepDefinitionBody.A3<T1, T2, T3>?) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        if (isCucumberInitialized) super.But(expression, body)
    }

    override fun Given(expression: String?, body: StepDefinitionBody.A0?) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?> Given(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> Given(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A3<T1, T2, T3>?
    ) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        if (isCucumberInitialized) super.Given(expression, body)
    }

    override fun Then(expression: String?, body: StepDefinitionBody.A0?) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?> Then(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> Then(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> Then(expression: String?, body: StepDefinitionBody.A3<T1, T2, T3>?) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        if (isCucumberInitialized) super.Then(expression, body)
    }

    override fun When(expression: String?, body: StepDefinitionBody.A0?) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?> When(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> When(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> When(expression: String?, body: StepDefinitionBody.A3<T1, T2, T3>?) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        if (isCucumberInitialized) super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        if (isCucumberInitialized) super.When(expression, body)
    }
}