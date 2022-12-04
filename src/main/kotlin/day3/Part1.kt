package day3


fun main(args: Array<String>) {

    val input = generateSequence(::readLine)
    val list = input.toList()

    println(list.map {
        val parts = it.split(",")

        Pair(Segment(parts[0]), Segment(parts[1]))
    }.filter {
        it.first overlaps it.second
    }.size)
}

