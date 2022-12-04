package day2

fun main(args: Array<String>) {

    val input = generateSequence(::readLine)
    val list = input.toList()

    println(list.chunked(3).sumOf {

        it.map { it ->
            it.toCharArray().toSet()
        }.reduceRight { left, right ->
            left.intersect(right)
        }.sumOf { it ->
            when {
                it.isLowerCase() -> it.code - 'a'.code + 1
                else -> it.code - 'A'.code + 27
            }
        }
    })
}
