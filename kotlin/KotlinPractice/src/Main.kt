//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val numbers = mutableListOf(1, 2, 3).also {
        println("리스트의 크기: ${it.size}")
        it.add(4)
    }
    println(numbers)
}