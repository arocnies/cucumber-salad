package dev.nies.salad.shell

import java.io.Closeable

class ShellGroup(
    private val workingDirectory: String = System.getProperty("user.dir"),
    private val configure: (ProcessBuilder.() -> Unit)? = null
) : Closeable {
    // Get shell by name
    // All shells start in workingDirectory
    // Create a shell with lambda changes for processbuilder edits
    private val shells = mutableMapOf<String, Shell>()

    fun getOrCreate(name: String): Shell {
        var targetShell = shells[name]
        if (targetShell == null) {
            targetShell = Shell(workingDirectory = workingDirectory, configure = configure)
            shells += name to targetShell
        }
        return targetShell
    }

    override fun close() {
        shells.values.map {
            runCatching { it.close() }
        }.firstOrNull { it.isFailure }?.let {
            throw ShellCloseException(it.exceptionOrNull())
        }
    }
}

class ShellCloseException(cause: Throwable?) : Exception("Failed to close a shell. Check for zombie processes", cause)