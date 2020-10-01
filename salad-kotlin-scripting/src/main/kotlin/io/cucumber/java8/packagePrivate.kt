package io.cucumber.java8

val isCucumberInitialized: Boolean get() = LambdaGlueRegistry.INSTANCE.get() != null
