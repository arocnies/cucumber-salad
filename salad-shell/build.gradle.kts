plugins {
    kotlin("jvm")
}

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    val cucumberVersion = properties["cucumberVersion"]

    implementation(project(":salad-core"))
    implementation("io.cucumber:cucumber-java8:$cucumberVersion")
    implementation("io.cucumber:cucumber-testng:$cucumberVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

val testStepDefs by tasks.registering(JavaExec::class) {
    group = "verification"

    workingDir("${project.buildDir}/resources/test")
    classpath = sourceSets.test.get().runtimeClasspath
    main = "StepDefTestKt"
    args = listOf("--monochrome", "--glue", "${project.group}", "test.feature")
}

tasks.test.get().dependsOn(testStepDefs)