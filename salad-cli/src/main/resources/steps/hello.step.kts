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
