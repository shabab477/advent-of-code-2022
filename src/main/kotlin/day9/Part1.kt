package day9

import kotlin.math.abs

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val queue = ArrayDeque<Int>()
    var cycleNum = 1
    var x = 1
    var str = "#"

    list.forEach {
        when (it) {
            "noop" -> queue.addLast(0)
            else -> {
                val parts = it.split("\\s+".toRegex())

                queue.addLast(0)
                queue.addLast(parts[1].toInt())
            }
        }
    }

    while(queue.isNotEmpty()) {
        cycleNum++

        val v = queue.removeFirst()
        x += v

        val pos = (cycleNum - 1) % 40
        str += if (abs(x - pos) <= 1) '#' else '.'

        if (cycleNum == 40 ||
            cycleNum == 80 ||
            cycleNum == 120||
            cycleNum == 160||
            cycleNum == 200||
            cycleNum == 240
        ) {
            str += "\n"
        }

    }

    println(str)
}