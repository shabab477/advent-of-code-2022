package day15

import kotlin.math.max

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val map = parseInput(list)
    val ans = findMaxWithElephant("AA", map, 26, mutableSetOf(), mutableMapOf(), true)

    println(ans)
}

// Need to increase memory
fun findMaxWithElephant(
    current: String,
    map: Map<String, Valve>,
    minutesLeft: Int,
    open: MutableSet<String>,
    cache: MutableMap<String, Int>,
    tryAgain: Boolean
): Int {

    if (minutesLeft == 0) {

        if (tryAgain) {

            return findMaxWithElephant("AA", map, 26, open, mutableMapOf(), false)
        }
        return 0
    }

    val hash = "$current-$minutesLeft-${open.joinToString(",")}"
    if (cache.containsKey(hash)) {

        return cache[hash]!!
    }

    val notOpening = map[current]!!.leadsTo.maxOf {
        findMaxWithElephant(it, map, minutesLeft - 1, open, cache, tryAgain)
    }

    val opening = if (!open.contains(current) && map[current]!!.flowRate != 0) {
        open.add(current)
        val x = (minutesLeft - 1) * map[current]!!.flowRate + findMaxWithElephant(current, map, minutesLeft - 1, open, cache, tryAgain)
        open.remove(current)

        x
    } else 0

    val ans = max(notOpening, opening)
    cache[hash] = ans

    return ans
}