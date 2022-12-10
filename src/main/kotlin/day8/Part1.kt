package day8

import kotlin.math.pow

//R 5
//U 8
//L 8
//D 3
//R 17
//D 10
//L 25
//U 20

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
    val rope = Array(10) { Pair(15, 11) }

    uniquePos.add(rope.last())

    for (command in list) {

        val parts = command.split("\\s+".toRegex())
        val dir = parts[0]
        val steps = parts[1].toInt()

        for (i in 0 until steps) {
            val stepDir = directions[dir]!!

            val head = rope[0]

            val nextHeadX = (head.first + (1 * stepDir.first))
            val nextHeadY = (head.second + (1 * stepDir.second))
            val newHead = Pair(nextHeadX, nextHeadY)

            rope[0] = newHead

            for (c in 1 until rope.size) {

                rope[c] = follow(rope[c], rope[c - 1])
            }

            uniquePos.add(rope.last())
        }
    }

    println(uniquePos.size)
}

fun follow(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {

    val distance = kotlin.math.sqrt(
        (tail.first - head.first.toDouble()).pow(2.0) + (tail.second - head.second.toDouble()).pow(2.0)
    )

    if (distance < 2) {

        return tail
    }

    val dX = if (head.first < tail.first) -1 else 1
    val dY = if (head.second < tail.second) -1 else 1

    return when {
        head.first == tail.first -> Pair(tail.first, tail.second + dY)
        head.second == tail.second -> Pair(tail.first + dX, tail.second)
        else -> Pair(tail.first + dX, tail.second + dY)
    }
}