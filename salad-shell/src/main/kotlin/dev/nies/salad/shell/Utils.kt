package dev.nies.salad.shell

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Field
import java.util.concurrent.TimeUnit

/**
 * Runs the given [command] in a /bin/sh shell and returns the output.
 * Blocks for the process to exit or until the specified [timeMillis] before returning.
 */
fun sh(command: String, timeMillis: Long = 10_000): String {
    val process = Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", command))
    process.waitFor(timeMillis, TimeUnit.MILLISECONDS)
    return process.inputStream.bufferedReader().readText().removeSuffix("\n")
}

fun String.splitTerminalColumns() = split(" ").filter { it.isNotBlank() }

/**
 * A hacky way of retrieving the PID on a Unix/Linux system in Java <= v8.
 * Java 9 introduces a pid field on the process object.
 */
fun Process.getUnixPid(): Long {
    var pid: Long = -1
    try {
        if (javaClass.name == "java.lang.UNIXProcess") {
            val f: Field = javaClass.getDeclaredField("pid")
            f.isAccessible = true
            pid = f.getLong(this)
            f.isAccessible = false
        }
    } catch (e: Exception) {
        pid = -1
    }
    return pid
}

class RetryConfiguration(private val attempts: Int, private val interval: Long) {
    operator fun <T> invoke(block: () -> T) {
        retry(attempts, interval, block)
    }

    private fun <T> retry(attempts: Int, interval: Long = 0L, block: () -> T): T = runBlocking {
        var throwable: Throwable? = null
        for (attempt in 1..attempts) {
            try {
                return@runBlocking block()
            } catch (e: Throwable) {
                throwable = e
                delay(interval)
            }
        }
        throw throwable!!
    }
}

class WaitFor(private val retryInterval: Long) {
    operator fun invoke(timeMillis: Long, condition: () -> Boolean) {
        withTimeout(timeMillis) {
            while (!condition()) {
                delay(retryInterval)
            }
        }
    }
}

fun withTimeout(timeMillis: Long, block: suspend () -> Unit) = runBlocking {
    kotlinx.coroutines.withTimeout(timeMillis) {
        launch {
            block()
        }
    }
}
