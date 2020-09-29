package dev.nies.salad.shell.cucumber

import dev.nies.salad.shell.*
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import java.io.File
import java.nio.file.Paths
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StepDefs : En {
    private val shells: ShellGroup = initShellGroup()
    private val scenarioShell = shells.getOrCreate("scenario-${this.hashCode()}")
    private val retry = RetryConfiguration(attempts = 5, interval = 200)
    private val waitFor = WaitFor(retryInterval = 500)

    init {
        After { _: Scenario ->
            shells.close()
        }

        ParameterType("polarity", "[\\s]?(?:not)?") { exists: String ->
            return@ParameterType "not" !in exists
        }

        ParameterType<(Shell) -> String>(
            "output",
            "(?:(?:error )?output)|(?:std[Oo]ut|std[Ee]rr)"
        ) { output: String ->
            if (listOf("error", "stderr", "stdErr").none { output.contains(it) }) {
                { sh: Shell -> sh.stdout }
            } else {
                { scenarioShell.stderr }
            }
        }

        // Given... dir
        Given("the folder/dir/directory {string}( ){polarity} exist(s)") { dir: String, polarity: Boolean ->
            val file = File(dir)
            if (polarity) {
                file.mkdirs()
            } else {
                !file.deleteRecursively()
            }
        }
        Given("the folder/dir/directory {string} is( ){polarity} empty") { dir: String, polarity: Boolean ->
            val file = File(dir)
            if (polarity) File("${file.absolutePath}/0").createNewFile() else file.deleteRecursively()
        }

        // Given... file
        Given("the file {string} is( ){polarity} empty/blank") { file: String, polarity: Boolean ->
            with(File(file)) {
                if (polarity) {
                    if (!exists()) createNewFile()
                    writeText("")
                } else {
                    if (!exists()) createNewFile()
                    if (length() == 0L) writeText("0")
                }
            }
        }

        Given("the file {string}( does)( ){polarity} exist(s)") { file: String, polarity: Boolean ->
            with(File(file)) {
                if (polarity) {
                    createNewFile()
                } else {
                    deleteRecursively()
                }
            }
        }
        Given("the file {string} contains {string}") { file: String, text: String ->
            File(file).appendText(text)
        }
        Given("the file {string} is a copy of {string}") { target: String, source: String ->
            val sourceFile = File(source)
            require(sourceFile.canRead()) { "Cannot read file $source" }
            File(target).writeText(sourceFile.readText())
        }

        // Given... environment
        Given("the variable {string} is( ){polarity} {string}") { name: String, polarity: Boolean, value: String ->
            if (polarity) {
                scenarioShell.sendLine("export $name=$value")
            } else {
                scenarioShell.sendLine("unset $name")
            }
        }
        Given("in (the )(shell ){string}(,) the variable {string} is( ){polarity} {string}") { shellName: String, name: String, polarity: Boolean, value: String ->
            val shell = shells.getOrCreate(shellName)
            if (polarity) {
                shell.sendLine("export $name=$value")
            } else {
                shell.sendLine("unset $name")
            }
        }
        Given("the variable {string} is( ){polarity} empty") { name: String, polarity: Boolean ->
            if (polarity) {
                scenarioShell.sendLine("unset $name")
            } else {
                scenarioShell.sendLine("[ -z \"$$name\" ] && export $name=")
            }
        }
        Given("in (the )(shell ){string}(,) the variable {string} is( ){polarity} empty") { shellName: String, name: String, polarity: Boolean ->
            val shell = shells.getOrCreate(shellName)
            if (polarity) {
                shell.sendLine("unset $name")
            } else {
                shell.sendLine("[ -z \"$$name\" ] && export $name=")
            }
        }

        // When... run
        When("I run: {}") { command: String ->
            scenarioShell.sendLine(command)
        }
        When("in (the )(shell ){string}(,) I run: {}") { shellName: String, command: String ->
            val shell = shells.getOrCreate(shellName)
            shell.sendLine(command)
        }
        When("I run {string}") { command: String ->
            scenarioShell.sendLine(command)
        }
        When("in (the )(shell ){string}(,) I run {string}") { shellName: String, command: String ->
            val shell = shells.getOrCreate(shellName)
            shell.sendLine(command)
        }

        // When... wait
        When("I wait (for ){int} second(s)") { seconds: Int ->
            Thread.sleep(seconds * 1000L)
        }
        When("I wait for (the ){output} to( ){polarity} contain/include/have/say/read {string}(;)(,)( or)( with| and)( a) timeout after/of {int} seconds") { output: (Shell) -> String, polarity: Boolean, pattern: String, seconds: Int ->
            waitFor(seconds * 1000L) {
                output(scenarioShell).contains(pattern.toRegex()) == polarity
            }
        }
        When("in (the )(shell ){string}(,) I wait for (the ){output} to( ){polarity} contain/include/have/say/read {string}(;|,)( or)( with| with a| and) timeout after/of {int} seconds") { shellName: String, output: (Shell) -> String, polarity: Boolean, pattern: String, seconds: Int ->
            val shell = shells.getOrCreate(shellName)
            waitFor(seconds * 1000L) {
                output(shell).contains(pattern.toRegex()) == polarity
            }
        }
        When("I wait for (the )command/cmd {string} to( ){polarity} contain/include/have/say/read {string}(;|,)( or)( with| with a| and) timeout after/of {int} seconds") { command: String, polarity: Boolean, pattern: String, seconds: Int ->
            waitFor(seconds * 1000L) {
                sh(command).contains(pattern.toRegex()) == polarity
            }
        }

        // When... save
        When("I save the {output} to {string}") { output: (Shell) -> String, file: String ->
            File(file).writeText(output(scenarioShell))
        }
        When("in (the )(shell ){string}(,) I save the {output} to {string}") { shellName: String, output: (Shell) -> String, file: String ->
            val shell = shells.getOrCreate(shellName)
            File(file).writeText(output(shell))
        }


        // Then... output
        Then("the {output} should( ){polarity} (be )equal (to )the file {string}") { output: (Shell) -> String, polarity: Boolean, file: String ->
            retry {
                with(File(file)) {
                    require(File(file).canRead()) { "Cannot read file $file" }
                    val equals = output(scenarioShell) == readText()
                    if (polarity) {
                        assertTrue(equals)
                    } else {
                        assertFalse(equals)
                    }
                }
            }
        }
        Then("in (the )(shell ){string}(,) the {output} should( ){polarity} (be )equal (to )the file {string}") { shellName: String, output: (Shell) -> String, polarity: Boolean, file: String ->
            val shell = shells.getOrCreate(shellName)
            retry {
                with(File(file)) {
                    require(File(file).canRead()) { "Cannot read file $file" }
                    val equals = output(shell) == readText()
                    if (polarity) {
                        assertTrue(equals)
                    } else {
                        assertFalse(equals)
                    }
                }
            }
        }
        Then("the {output} should( ){polarity} equal {string}") { output: (Shell) -> String, polarity: Boolean, text: String ->
            retry {
                if (polarity) {
                    assertTrue { output(scenarioShell) == text }
                } else {
                    assertFalse { output(scenarioShell) == text }
                }
            }
        }
        Then("in (the )(shell ){string}(,) the {output} should( ){polarity} equal {string}") { shellName: String, output: (Shell) -> String, polarity: Boolean, text: String ->
            val shell = shells.getOrCreate(shellName)
            retry {
                if (polarity) {
                    assertTrue { output(shell) == text }
                } else {
                    assertFalse { output(shell) == text }
                }
            }
        }
        //Then("^the (error )?output should (not )?resemble {string}(?: by \"(\\d{1,2})%?\")?$") {}
        Then("the {output} should( ){polarity} match {string}") { output: (Shell) -> String, polarity: Boolean, pattern: String ->
            retry {
                val matches = output(scenarioShell).matches(pattern.toRegex())
                if (polarity) {
                    assertTrue(matches)
                } else {
                    assertFalse(matches)
                }
            }
        }
        Then("in (the )(shell ){string}(,) the {output} should( ){polarity} match {string}") { shellName: String, output: (Shell) -> String, polarity: Boolean, pattern: String ->
            val shell = shells.getOrCreate(shellName)
            retry {
                val matches = output(shell).matches(pattern.toRegex())
                if (polarity) {
                    assertTrue(matches)
                } else {
                    assertFalse(matches)
                }
            }
        }
        Then("the {output} should( ){polarity} contain/include/have/say/read {string}") { output: (Shell) -> String, polarity: Boolean, pattern: String ->
            retry {
                val contains = output(scenarioShell).contains(pattern.toRegex())
                if (polarity) {
                    assertTrue(contains)
                } else {
                    assertFalse(contains)
                }
            }
        }
        Then("in (the )(shell ){string}(,) the {output} should( ){polarity} contain/include/have/say/read {string}") { shellName: String, output: (Shell) -> String, polarity: Boolean, pattern: String ->
            val shell = shells.getOrCreate(shellName)
            retry {
                val contains = output(shell).contains(pattern.toRegex())
                if (polarity) {
                    assertTrue(contains)
                } else {
                    assertFalse(contains)
                }
            }
        }
        Then("the {output} should( ){polarity} be/equal {string}") { output: (Shell) -> String, polarity: Boolean, text: String ->
            retry {
                val equals = output(scenarioShell) == text
                if (polarity) {
                    assertTrue(equals)
                } else {
                    assertFalse(equals)
                }
            }
        }
        Then("in (the )(shell ){string}(,) the {output} should( ){polarity} be/equal {string}") { shellName: String, output: (Shell) -> String, polarity: Boolean, text: String ->
            val shell = shells.getOrCreate(shellName)
            retry {
                val equals = output(shell) == text
                if (polarity) {
                    assertTrue(equals)
                } else {
                    assertFalse(equals)
                }
            }
        }

        // Then... file
        Then("the file {string} should( ){polarity} be empty/blank") { file: String, polarity: Boolean ->
            retry {
                with(File(file)) {
                    val text = readText()
                    if (polarity) {
                        assertTrue { text.isBlank() }
                    } else {
                        assertFalse { text.isBlank() }
                    }
                }
            }
        }
        Then("the file {string} should( ){polarity} exist") { file: String, polarity: Boolean ->
            retry {
                with(File(file)) {
                    if (polarity) {
                        assertTrue { exists() }
                    } else {
                        assertFalse { exists() }
                    }
                }
            }
        }
        Then("the file {string} should( ){polarity} match {string}") { file: String, polarity: Boolean, pattern: String ->
            retry {
                with(File(file)) {
                    val match = readText().matches(pattern.toRegex())
                    if (polarity) {
                        assertTrue { match }
                    } else {
                        assertFalse { match }
                    }
                }
            }
        }
        Then("the file {string} should( ){polarity} contain/include/have/say/read {string}") { file: String, polarity: Boolean, pattern: String ->
            retry {
                with(File(file)) {
                    val contains = readText().contains(pattern.toRegex())
                    if (polarity) {
                        assertTrue { contains }
                    } else {
                        assertFalse { contains }
                    }
                }
            }
        }
        Then("the file {string} should( ){polarity} be/equal {string}") { file: String, polarity: Boolean, text: String ->
            retry {
                with(File(file)) {
                    val equals = readText() == text
                    if (polarity) {
                        assertTrue { equals }
                    } else {
                        assertFalse { equals }
                    }
                }
            }
        }
        //Then("^the file {string} should (not )?be like {string}$") {}

        // Then... dir
        Then("the folder/dir/directory {string} should( ){polarity} exist") { dir: String, polarity: Boolean ->
            retry {
                with(File(dir)) {
                    if (polarity) {
                        assertTrue { isDirectory }
                    } else {
                        assertFalse { isDirectory }
                    }
                }
            }
        }
        Then("the folder/dir/directory {string} should( ){polarity} be empty") { dir: String, polarity: Boolean ->
            retry {
                with(File(dir)) {
                    val children = list() ?: emptyArray()
                    if (polarity) {
                        assertTrue { children.isEmpty() }
                    } else {
                        assertFalse { children.isNotEmpty() }
                    }
                }
            }
        }

        // Then... process
        Then("a process should( ){polarity} exist matching {string}") { polarity: Boolean, pattern: String ->
            retry {
                val exists = sh("ps -e")
                    .split("\n")
                    .map { it.splitTerminalColumns().last() }
                    .any { it.matches(pattern.toRegex()) }
                if (polarity) {
                    assertTrue { exists }
                } else {
                    assertFalse { exists }
                }
            }
        }

        // Then... running
        Then("running {string} should( ){polarity} match {string}") { command: String, polarity: Boolean, pattern: String ->
            val match = sh(command).matches(pattern.toRegex())
            if (polarity) {
                assertTrue(match)
            } else {
                assertFalse(match)
            }
        }
        Then("running {string} should( ){polarity} contain/include/have/say/read {string}") { command: String, polarity: Boolean, pattern: String ->
            val contains = sh(command).contains(pattern.toRegex())
            if (polarity) {
                assertTrue(contains)
            } else {
                assertFalse(contains)
            }
        }

        //Then("^A process matching {string} is (not )?a child of a process matching {string}") { child: String, polarity: Boolean, parent: String -> }

        // Then... debug
        //Then("^stop for debug$") {}

        // Then... exit code
        //Then("^the (?:status|exit) code should (not )?be {string}$") { polarity: Boolean, code: Int -> }
        //Then("^the (?:status|exit) code should mean (success|failure)$") {}
    }

    private fun initShellGroup(): ShellGroup {
        val config = loadShellConfig()
        return ShellGroup(workingDirectory = config.workingDir ?: Paths.get("").toAbsolutePath().toString()) {
            if (!config.env.isNullOrEmpty()) environment().putAll(config.env)
        }
    }
}
