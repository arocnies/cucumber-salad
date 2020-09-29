plugins {
    kotlin("jvm")
}

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    val cucumberVersion = properties["cucumberVersion"]

    api(project(":salad-core"))
    api(kotlin("script-runtime"))
    implementation("io.cucumber:cucumber-java8:$cucumberVersion")
    implementation("io.cucumber:cucumber-junit:$cucumberVersion")
    implementation("com.github.ajalt.clikt:clikt:3.0.1")
    implementation(kotlin("scripting-jvm-host"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))

    // Imported to be made available within scripts.
    api("eu.jrie.jetbrains:kotlin-shell-core:0.2.1")
    api(project(":salad-shell"))
    api(kotlin("test"))
    api(kotlin("test-junit"))
}