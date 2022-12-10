package day8

import kotlin.math.pow


private val directions = mapOf(
    "R" to Pair(0, 1),
    "L" to Pair(0, -1),
    "U" to Pair(-1, 0),
    "D" to Pair(1, 0)
)

fun main() {
    val input = generateSequence(::readLine)
    val list = input.toList()

    val uniquePos = mutableSetOf<Pair<Int, Int>>()
    var currentPos = Pair(Pair(4, 0), Pair(4, 0))

    uniquePos.add(currentPos.second)

    for (command in list) {

        val parts = command.split("\\s+".toRegex())
        val dir = parts[0]
        val steps = parts[1].toInt()

        for (i in 0 until steps) {
            val stepDir = directions[dir]!!

            val head = currentPos.first
            val tail = currentPos.second

            val nextHeadX = (head.first + (1 * stepDir.first))
            val nextHeadY = (head.second + (1 * stepDir.second))

            val newHead = Pair(nextHeadX, nextHeadY)

            val distance = kotlin.math.sqrt(
                (tail.first - nextHeadX.toDouble()).pow(2.0) + (tail.second - nextHeadY.toDouble()).pow(2.0)
            )

            currentPos = if (distance >= 2) {
                Pair(newHead, head)
            } else Pair(newHead, tail)

            uniquePos.add(currentPos.second)
        }
    }

    println(uniquePos.size)
}