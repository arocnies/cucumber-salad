import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10" apply false
}

allprojects {
    group = "dev.nies"
    version = "0.0.1"

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    repositories {
        mavenCentral()
        // fixme: This is here due to the salad-kotlin-scripting not resolving it. Why? Move it there.
        maven(url = "https://dl.bintray.com/jakubriegel/kotlin-shell")
    }
}
