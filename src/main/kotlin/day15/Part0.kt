package day15

import kotlin.math.max

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val map = parseInput(list)
    val ans = findMax("AA", map, 30, mutableSetOf(), mutableMapOf())

    println(ans)
}

fun findMax(
            current: String,
            map: Map<String, Valve>,
            minutesLeft: Int,
            open: MutableSet<String>,
            cache: MutableMap<String, Int>
): Int {

    if (minutesLeft == 0) {

        return 0
    }

    val hash = "$current-$minutesLeft-${open.joinToString(",")}"
    if (cache.containsKey(hash)) {

        return cache[hash]!!
    }

    val notOpening = map[current]!!.leadsTo.maxOf {
        findMax(it, map, minutesLeft - 1, open, cache)
    }

    val opening = if (!open.contains(current) && map[current]!!.flowRate != 0) {
        open.add(current)
        val x = (minutesLeft - 1) * map[current]!!.flowRate + findMax(current, map, minutesLeft - 1, open, cache)
        open.remove(current)

        x
    } else 0

    val ans = max(notOpening, opening)
    cache[hash] = ans

    return ans
}


fun parseInput(list: List<String>): Map<String, Valve> {

    val map = mutableMapOf<String, Valve>()
    list.forEach {
        val parts = it.split("has")
        val name = parts[0].removePrefix("Valve ").trim()
        val flow = parts[1].substring(parts[1].indexOf("=") + 1, parts[1].indexOf(";")).toInt()
        val fromIndex = if (parts[1].contains("valves")) {

            parts[1].indexOf("valves") + "valves".length
        } else {

            parts[1].indexOf("valve") + "valve".length
        }

        val leadsTo = parts[1]
            .substring(fromIndex)
            .split(",")
            .map { n -> n.trim() }

        map[name] = Valve(name, flow, leadsTo)
    }

    return map
}

data class Valve(val name: String, val flowRate: Int, val leadsTo: List<String>)