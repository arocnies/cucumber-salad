package dev.nies.salad.shell

import java.io.*
import kotlin.concurrent.thread

/**
 * A stateful /bin/sh shell process.
 * The shell is automatically closed on JVM shutdown but it is recommended to close manually once the caller to avoid
 * long running processes using up resources while the JVM runs.
 *
 * For single command use, see [sh]
 */
class Shell(
    private val printOutput: Boolean = true,
    workingDirectory: String? = null,
    configure: (ProcessBuilder.() -> Unit)? = null
) : Closeable {
    private val process: Process
    private val outToProcess: PrintWriter
    private val _stdout: StringBuilder = StringBuilder()
    private val _stderr = StringBuilder()
    private val collectorThreads = mutableListOf<Thread>()
    val stdout: String get() = synchronized(this) { _stdout.toString() }
    val stderr: String get() = synchronized(this) { _stderr.toString() }
    val pid: Long
    val isAlive: Boolean get() = process.isAlive

    init {
        val processBuilder = ProcessBuilder(listOf("/bin/sh", "-s"))
        if (!workingDirectory.isNullOrBlank()) processBuilder.directory(File(workingDirectory))
        if (configure != null) processBuilder.configure()

        process = processBuilder.start()
        pid = process.getUnixPid()

        addShutdownHook()
        outToProcess = PrintWriter(process.outputStream)
        startActiveOutputCollection(process.inputStream, _stdout)
        startActiveOutputCollection(process.errorStream, _stderr)
    }

    private fun startActiveOutputCollection(inputStream: InputStream, target: StringBuilder): Thread {
        val collector = thread(start = true) {
            val input = inputStream.bufferedReader()
            try {
                while (isAlive) {
                    val line = input.readLine() ?: break
                    if (printOutput) println(line)
                    synchronized(this) {
                        target.append(line)
                    }
                }
            } catch (e: IOException) {
                println("Closed Shell input: ${e.message}")
            }
        }
        collectorThreads += collector
        return collector
    }

    private fun addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                this@Shell.close()
            }
        })
    }

    fun sendLine(input: String) {
        check(process.isAlive) { "Cannot send input to dead Shell" }

        // We trim the end before adding the newline to deal with the caller adding it themselves
        outToProcess.write(input.trimEnd() + "\n")
        outToProcess.flush()
    }

    override fun close() {
        process.destroyForcibly()
        collectorThreads.forEach {
            runCatching { it.interrupt() }
        }
    }
}