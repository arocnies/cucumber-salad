package dev.nies

println(salad)
val name: String? = salad.properties.getOrElse("name") { "Unnamed" }.toString()
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
