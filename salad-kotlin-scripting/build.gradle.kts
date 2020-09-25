plugins {
    kotlin("jvm")
}

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    val cucumberVersion = properties["cucumberVersion"]

    api(project(":salad-core"))
    implementation("io.cucumber:cucumber-java8:$cucumberVersion")
    implementation("io.cucumber:cucumber-junit:$cucumberVersion")
    implementation("com.github.ajalt.clikt:clikt:3.0.1")
    implementation(kotlin("scripting-jvm-host"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))
}