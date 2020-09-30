![Cucumber Salad](https://www.iconspng.com/image/87515/salad-colour)

# Portable Cucumber Testing

## Quick Start

```shell
> cucumber-salad --init
> cucumber-salad
```

## Features

### Consistent & repeatable testing

Your test context, feature files, glue code, and configuration all in one file.
`salad.yml`

```
---
features:
  - my_test.feature
  - features/
properties:
  name: Alice
steps:
  - ../cucumber/steps.jar
  - glue/
  - scripts/myScript.kts
```

### Independent test runner

Only check in your feature files and any step definition scripts. Use the RPM commandline application to execute your
tests:

```shell
> cucumber-salad --help
Usage: cucumber-salad [OPTIONS]

Options:
  -c, --config TEXT  path to salad.yml config file. Default ./salad.yml
  --stacktrace       print stacktraces
  --init             Creates an initial test example in the current directory
  -v, --version      show version
  -h, --help         Show this message and exit
```

### Write Cucumber glue code as scripts

Scripting step definitions as `.step.kts` files.

```kotlin
val name: String? = salad.properties["name"].toString()
var output: String = ""

Given("I know a name") {
    assert(name != null)
}

When("I say hello") {
    output = "Hello $name!"
}

Then("the output should contain the name") {
    assert(output == "Hello $name!")
}
```

All `.step.kts` files have access to `Given/When/Then/And` as well as the Cucumber Hooks such
as `Before/BeforeStep/After/AfterStep`.

Properties are accessed via the `salad` member available to all `.step.kts` scripts.

All `.step.kts` scripts can import other scripts using `@file:Import("path/to/script")`.

Built in libraries available to scripts include; `salad-shell`
, [kotlin-shell](https://github.com/jakubriegel/kotlin-shell), as well as test assertions from JUnit and `kotlin-test`.
