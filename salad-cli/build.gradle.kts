plugins {
    kotlin("jvm")
    application
    id("nebula.ospackage") version "8.4.1"
}

repositories {
    maven(url = "https://jitpack.io")
}

val cucumberVersion = properties["cucumberVersion"]
dependencies {

    api(project(":salad-core"))
    api(project(":salad-kotlin-scripting"))
    implementation("com.github.uchuhimo.konf:konf:0.22.0")
    implementation("io.cucumber:cucumber-java8:$cucumberVersion")
    implementation("io.cucumber:cucumber-junit:$cucumberVersion")
    implementation("com.github.ajalt.clikt:clikt:3.0.1")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("com.github.stefanbirkner:system-lambda:1.1.0")
}

application {
    mainClassName = "dev.nies.salad.cli.AppKt"
    applicationName = "cucumber-salad"
}

ospackage {
    packageName = "cucumber-salad"
    version = project.version.toString()
    setArch("X86_64")
    os = org.redline_rpm.header.Os.LINUX
    type = org.redline_rpm.header.RpmType.BINARY
    fileMode = 744
    requires("java")
    user = "root"
    into("/usr/share/cucumber-salad")
    directory("/etc/cucumber-salad")
    from(tasks.getByName("installDist").outputs, closureOf<CopySpec> {
        fileMode = 655
    })
    from("${sourceSets.main.get().resources.asPath}/salad.yml")
    link("/usr/bin/cucumber-salad", "/usr/share/cucumber-salad/bin/cucumber-salad")
    postInstall(
        """
        echo "export CUCUMBER_SALAD_HOME=/usr/share/cucumber-salad" > /etc/profile.d/cucumber-salad.sh
        chmod a+x /etc/profile.d/cucumber-salad.sh
    """.trimIndent()
    )
}

tasks.getByName("buildRpm").dependsOn(tasks.getByName("installDist"))
/*
To test in shell, run Gradle task "buildRpm"
Run: vagrant up; vagrant ssh
In vagrant, run: cd /tmp/
 */

sourceSets.register("testSteps") {
    compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
}

val testStepsImplementation by configurations.getting {
    // We don't want to extend the main configurations since we want this to resemble an independent library.
}
dependencies {
    testStepsImplementation("io.cucumber:cucumber-java8:$cucumberVersion")
}

//val buildTestSteps by tasks.registering(JavaCompile::class) {
//    group = "build"
//    classpath = sourceSets["testSteps"].runtimeClasspath
//}

val externalStepsJar by tasks.registering(Jar::class) {
    group = "build"
    dependsOn("testStepsClasses")
    archiveBaseName.set("external-steps")
    from(
        sourceSets["testSteps"].output
            .map {
                provider {
                    if (it.isDirectory) it else zipTree(it)
                }
            }
    )
    with(tasks["jar"] as CopySpec)
}

tasks.test {
    dependsOn(externalStepsJar)
    doFirst {
        copy {
            mkdir("$buildDir/resources/testSteps/jars")
            from(externalStepsJar.get().outputs)
            into("$buildDir/resources/testSteps/jars")
        }
    }
}

tasks.processResources {
    val versionFile = "$buildDir/tmp/version.txt"
    doFirst {
        mkdir(versionFile.substringBeforeLast('/'))
        File(versionFile).writeText(version.toString())
    }
    from("$buildDir/tmp/version.txt")
}
