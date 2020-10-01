fun thisFunctionIsImported(): String = "Called thisFunctionIsImported"

class OtherScriptClass(val name: String) {
    fun sayHi() = println("Hello $name!")
}