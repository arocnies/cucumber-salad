package dev.nies.salad.script

import dev.nies.salad.core.config.SaladConfig
import io.cucumber.java8.*
import kotlin.script.experimental.annotations.KotlinScript

const val CucumberScriptExtension = "step.kts"

@KotlinScript(fileExtension = CucumberScriptExtension)
open class CucumberScript : En {
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
        checkCucumberState()
        super.Before(body)
    }

    override fun Before(tagExpression: String?, body: HookBody?) {
        checkCucumberState()
        super.Before(tagExpression, body)
    }

    override fun Before(order: Int, body: HookBody?) {
        checkCucumberState()
        super.Before(order, body)
    }

    override fun Before(tagExpression: String?, order: Int, body: HookBody?) {
        checkCucumberState()
        super.Before(tagExpression, order, body)
    }

    override fun Before(body: HookNoArgsBody?) {
        checkCucumberState()
        super.Before(body)
    }

    override fun Before(tagExpression: String?, body: HookNoArgsBody?) {
        checkCucumberState()
        super.Before(tagExpression, body)
    }

    override fun Before(order: Int, body: HookNoArgsBody?) {
        checkCucumberState()
        super.Before(order, body)
    }

    override fun Before(tagExpression: String?, order: Int, body: HookNoArgsBody?) {
        checkCucumberState()
        super.Before(tagExpression, order, body)
    }

    override fun BeforeStep(body: HookBody?) {
        checkCucumberState()
        super.BeforeStep(body)
    }

    override fun BeforeStep(tagExpression: String?, body: HookBody?) {
        checkCucumberState()
        super.BeforeStep(tagExpression, body)
    }

    override fun BeforeStep(order: Int, body: HookBody?) {
        checkCucumberState()
        super.BeforeStep(order, body)
    }

    override fun BeforeStep(tagExpression: String?, order: Int, body: HookBody?) {
        checkCucumberState()
        super.BeforeStep(tagExpression, order, body)
    }

    override fun BeforeStep(body: HookNoArgsBody?) {
        checkCucumberState()
        super.BeforeStep(body)
    }

    override fun BeforeStep(tagExpression: String?, body: HookNoArgsBody?) {
        checkCucumberState()
        super.BeforeStep(tagExpression, body)
    }

    override fun BeforeStep(order: Int, body: HookNoArgsBody?) {
        checkCucumberState()
        super.BeforeStep(order, body)
    }

    override fun BeforeStep(tagExpression: String?, order: Int, body: HookNoArgsBody?) {
        checkCucumberState()
        super.BeforeStep(tagExpression, order, body)
    }

    override fun After(body: HookBody?) {
        checkCucumberState()
        super.After(body)
    }

    override fun After(tagExpression: String?, body: HookBody?) {
        checkCucumberState()
        super.After(tagExpression, body)
    }

    override fun After(order: Int, body: HookBody?) {
        checkCucumberState()
        super.After(order, body)
    }

    override fun After(tagExpression: String?, order: Int, body: HookBody?) {
        checkCucumberState()
        super.After(tagExpression, order, body)
    }

    override fun After(body: HookNoArgsBody?) {
        checkCucumberState()
        super.After(body)
    }

    override fun After(tagExpression: String?, body: HookNoArgsBody?) {
        checkCucumberState()
        super.After(tagExpression, body)
    }

    override fun After(order: Int, body: HookNoArgsBody?) {
        checkCucumberState()
        super.After(order, body)
    }

    override fun After(tagExpression: String?, order: Int, body: HookNoArgsBody?) {
        checkCucumberState()
        super.After(tagExpression, order, body)
    }

    override fun AfterStep(body: HookBody?) {
        checkCucumberState()
        super.AfterStep(body)
    }

    override fun AfterStep(tagExpression: String?, body: HookBody?) {
        checkCucumberState()
        super.AfterStep(tagExpression, body)
    }

    override fun AfterStep(order: Int, body: HookBody?) {
        checkCucumberState()
        super.AfterStep(order, body)
    }

    override fun AfterStep(tagExpression: String?, order: Int, body: HookBody?) {
        checkCucumberState()
        super.AfterStep(tagExpression, order, body)
    }

    override fun AfterStep(body: HookNoArgsBody?) {
        checkCucumberState()
        super.AfterStep(body)
    }

    override fun AfterStep(tagExpression: String?, body: HookNoArgsBody?) {
        checkCucumberState()
        super.AfterStep(tagExpression, body)
    }

    override fun AfterStep(order: Int, body: HookNoArgsBody?) {
        checkCucumberState()
        super.AfterStep(order, body)
    }

    override fun AfterStep(tagExpression: String?, order: Int, body: HookNoArgsBody?) {
        checkCucumberState()
        super.AfterStep(tagExpression, order, body)
    }

    override fun DocStringType(contentType: String?, body: DocStringDefinitionBody<*>?) {
        checkCucumberState()
        super.DocStringType(contentType, body)
    }

    override fun <T : Any?> DataTableType(body: DataTableEntryDefinitionBody<T>?) {
        checkCucumberState()
        super.DataTableType(body)
    }

    override fun <T : Any?> DataTableType(replaceWithEmptyString: String?, body: DataTableEntryDefinitionBody<T>?) {
        checkCucumberState()
        super.DataTableType(replaceWithEmptyString, body)
    }

    override fun <T : Any?> DataTableType(body: DataTableRowDefinitionBody<T>?) {
        checkCucumberState()
        super.DataTableType(body)
    }

    override fun <T : Any?> DataTableType(replaceWithEmptyString: String?, body: DataTableRowDefinitionBody<T>?) {
        checkCucumberState()
        super.DataTableType(replaceWithEmptyString, body)
    }

    override fun <T : Any?> DataTableType(body: DataTableCellDefinitionBody<T>?) {
        checkCucumberState()
        super.DataTableType(body)
    }

    override fun <T : Any?> DataTableType(replaceWithEmptyString: String?, body: DataTableCellDefinitionBody<T>?) {
        checkCucumberState()
        super.DataTableType(replaceWithEmptyString, body)
    }

    override fun <T : Any?> DataTableType(body: DataTableDefinitionBody<T>?) {
        checkCucumberState()
        super.DataTableType(body)
    }

    override fun <T : Any?> DataTableType(replaceWithEmptyString: String?, body: DataTableDefinitionBody<T>?) {
        checkCucumberState()
        super.DataTableType(replaceWithEmptyString, body)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A1<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A2<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A3<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A4<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A5<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A6<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A7<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A8<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun <R : Any?> ParameterType(
        name: String?,
        regex: String?,
        definitionBody: ParameterDefinitionBody.A9<R>?
    ) {
        checkCucumberState()
        super.ParameterType(name, regex, definitionBody)
    }

    override fun DefaultParameterTransformer(definitionBody: DefaultParameterTransformerBody?) {
        checkCucumberState()
        super.DefaultParameterTransformer(definitionBody)
    }

    override fun DefaultDataTableCellTransformer(definitionBody: DefaultDataTableCellTransformerBody?) {
        checkCucumberState()
        super.DefaultDataTableCellTransformer(definitionBody)
    }

    override fun <T : Any?> DefaultDataTableCellTransformer(
        replaceWithEmptyString: String?,
        definitionBody: DefaultDataTableCellTransformerBody?
    ) {
        checkCucumberState()
        super.DefaultDataTableCellTransformer<T>(replaceWithEmptyString, definitionBody)
    }

    override fun DefaultDataTableEntryTransformer(definitionBody: DefaultDataTableEntryTransformerBody?) {
        checkCucumberState()
        super.DefaultDataTableEntryTransformer(definitionBody)
    }

    override fun <T : Any?> DefaultDataTableEntryTransformer(
        replaceWithEmptyString: String?,
        definitionBody: DefaultDataTableEntryTransformerBody?
    ) {
        checkCucumberState()
        super.DefaultDataTableEntryTransformer<T>(replaceWithEmptyString, definitionBody)
    }

    override fun And(expression: String?, body: StepDefinitionBody.A0?) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?> And(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> And(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> And(expression: String?, body: StepDefinitionBody.A3<T1, T2, T3>?) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> And(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        checkCucumberState()
        super.And(expression, body)
    }

    override fun But(expression: String?, body: StepDefinitionBody.A0?) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?> But(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> But(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> But(expression: String?, body: StepDefinitionBody.A3<T1, T2, T3>?) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> But(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        checkCucumberState()
        super.But(expression, body)
    }

    override fun Given(expression: String?, body: StepDefinitionBody.A0?) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?> Given(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> Given(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A3<T1, T2, T3>?
    ) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> Given(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        checkCucumberState()
        super.Given(expression, body)
    }

    override fun Then(expression: String?, body: StepDefinitionBody.A0?) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?> Then(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> Then(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> Then(expression: String?, body: StepDefinitionBody.A3<T1, T2, T3>?) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> Then(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        checkCucumberState()
        super.Then(expression, body)
    }

    override fun When(expression: String?, body: StepDefinitionBody.A0?) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?> When(expression: String?, body: StepDefinitionBody.A1<T1>?) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?> When(expression: String?, body: StepDefinitionBody.A2<T1, T2>?) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?> When(expression: String?, body: StepDefinitionBody.A3<T1, T2, T3>?) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A4<T1, T2, T3, T4>?
    ) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A5<T1, T2, T3, T4, T5>?
    ) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A6<T1, T2, T3, T4, T5, T6>?
    ) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A7<T1, T2, T3, T4, T5, T6, T7>?
    ) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A8<T1, T2, T3, T4, T5, T6, T7, T8>?
    ) {
        checkCucumberState()
        super.When(expression, body)
    }

    override fun <T1 : Any?, T2 : Any?, T3 : Any?, T4 : Any?, T5 : Any?, T6 : Any?, T7 : Any?, T8 : Any?, T9 : Any?> When(
        expression: String?,
        body: StepDefinitionBody.A9<T1, T2, T3, T4, T5, T6, T7, T8, T9>?
    ) {
        checkCucumberState()
        super.When(expression, body)
    }
}

