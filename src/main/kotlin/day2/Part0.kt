package day2


fun main(args: Array<String>) {

    val input = generateSequence(::readLine)
    val list = input.toList()

    println(list.map {
        val half = it.length / 2

        Pair(it.substring(0, half).toCharArray().toSet(), it.substring(half).toCharArray().toSet())
    }.flatMap { (first, last) ->
        first.intersect(last).toList()
    }.sumOf {
        when {
            it.isLowerCase() -> it.code - 'a'.code + 1
            else -> it.code - 'A'.code + 27
        }
    })
}
