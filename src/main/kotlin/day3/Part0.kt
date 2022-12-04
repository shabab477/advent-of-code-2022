package day3


fun main(args: Array<String>) {

    val input = generateSequence(::readLine)
    val list = input.toList()

    println(list.map {
        val parts = it.split(",")

        Pair(Segment(parts[0]), Segment(parts[1]))
    }.filter {
        when {
            it.first.length() > it.second.length() -> it.first contains it.second
            else -> it.second contains it.first
        }
    }.size)
}

data class Segment(val x: Int, val y: Int) {

    companion object {
        operator fun invoke(name: String): Segment {
            val v = name.split("-")

            return Segment(v[0].toInt(), v[1].toInt())
        }
    }

    fun length(): Int = y - x + 1

    infix fun contains(other: Segment): Boolean {

        return other.x >= x && other.y <= y
    }

    infix fun overlaps(other: Segment): Boolean {

        return contains(Segment(other.x, other.x))
                || contains(Segment(other.y, other.y))
                || other.contains(Segment(x, x))
                || other.contains(Segment(y, y))
    }
}
