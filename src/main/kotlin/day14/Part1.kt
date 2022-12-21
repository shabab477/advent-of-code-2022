package day14

import kotlin.math.abs

const val sz = 4000000

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val data = (parseInput(list))


    val ans = data.map {

        val s = it.first
        val b = it.second
        val d = calcD(s, b)

        val right = s.copy(second = s.second + d + 1)
        val left = s.copy(second = s.second - d - 1)
        val top = s.copy(first = s.first + d + 1)
        val down = s.copy(first = s.first - d  - 1)

        listOf(left, right, top, down)
    }

    for (outRange in ans) {

        val left = outRange[0]
        val right = outRange[1]
        val top = outRange[2]
        val down = outRange[3]

        val set = mutableSetOf<Pair<Int, Int>>()

        set.addAll(genRange(top, right))
        set.addAll(genRange(right, down))
        set.addAll(genRange(down, left))
        set.addAll(genRange(left, top))

        val temp = set.toMutableSet()

        for (dt in data) {
            val s = dt.first
            val b = dt.second
            val d = calcD(s, b)

            for (p in set) {

                val td = calcD(s, p)
                if (td <= d) {

                    temp.remove(p)
                }
            }
        }

        if (temp.isNotEmpty()) {
            println(temp.first())
            break
        }
    }

}

fun genRange(from: Pair<Int, Int>, to: Pair<Int, Int>): MutableList<Pair<Int, Int>> {

    val dx = if (to.first - from.first > 0) 1 else -1
    val dy = if (to.second - from.second > 0) 1 else -1

    // Try to offset within range
    val fromWithinRange = run(fun(): Pair<Int, Int> {
        val stepsX = if (from.first < 0) {
            abs(from.first)
        } else if (from.first > sz) {
            from.first - sz
        } else 0

        val stepsY = if (from.second < 0) {
            abs(from.second)
        } else if (from.second > sz) {
            from.second - sz
        } else 0

        return Pair(from.first + dx * (stepsX + stepsY), from.second + dy * (stepsX + stepsY))
    })

    // If still not then nothing to see here
    if (fromWithinRange.first !in 0..sz || fromWithinRange.second !in 0..sz) {

        return mutableListOf()
    }

    val points = mutableListOf(fromWithinRange)

    while(points.last() != to) {

        val ls = points.last()
        val next = ls.copy(ls.first + dx, ls.second + dy)
        if (next.first !in 0..sz || next.second !in 0..sz) {

            break
        }

        points.add(next)
    }

    return points
}


