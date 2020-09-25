plugins {
    kotlin("jvm")
}

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    val cucumberVersion = properties["cucumberVersion"]

    api("com.github.uchuhimo.konf:konf:0.22.0")
    implementation("io.cucumber:cucumber-java8:$cucumberVersion")
    implementation("io.cucumber:cucumber-junit:$cucumberVersion")
    implementation("com.github.ajalt.clikt:clikt:3.0.1")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("com.github.stefanbirkner:system-lambda:1.1.0")
}