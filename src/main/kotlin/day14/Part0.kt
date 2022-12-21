package day14

import kotlin.math.abs

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val data = (parseInput(list))
    val set = mutableSetOf<Int>()

    val answerX = 2000000
    val ans = data.mapNotNull {

        val s = it.first
        val b = it.second
        val d = calcD(s, b)

        findEdges(s, d, answerX)
    }

    ans.forEach {
        val from = it.first.second
        val to = it.second.second

        for (v in from..to) {
            set.add(v)
        }
    }

    println(set.sorted().filter { !isBeaconOrSensorPresent(data, Pair(answerX, it)) }.size)
}

fun findEdges(sensor: Pair<Int, Int>, d: Int, answerX: Int): Pair<Pair<Int, Int>, Pair<Int, Int>>? {

    val totalBelow = sensor.first + d
    val totalUp = sensor.first - d

    if (within(sensor.first, totalBelow, answerX)) {

        val steps = answerX - sensor.first
        val leftEdge = Pair(answerX, sensor.second - d + steps)
        val rightEdge = Pair(answerX, sensor.second + d - steps)

        return Pair(leftEdge, rightEdge)
    } else if (within(totalUp, sensor.first, answerX)) {

        val steps = sensor.first - answerX
        val leftEdge = Pair(answerX, sensor.second - d + steps)
        val rightEdge = Pair(answerX, sensor.second + d - steps)

        return Pair(leftEdge, rightEdge)
    }

    return null
}

fun within(s: Int, p: Int, x: Int): Boolean {
    return (x in s..p)
}


fun isBeaconOrSensorPresent(data: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>, curr: Pair<Int, Int>): Boolean {

    return (data.firstOrNull {
        it.first == curr || it.second == curr
    }) != null
}

fun calcD(s: Pair<Int, Int>, b: Pair<Int, Int>): Int {

    return abs(s.first - b.first) + abs(s.second - b.second)
}

fun parseInput(list: List<String>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {

    return list.map {
        val sp = it.split(":")
        val fromPart = sp[0].split("[=,]".toRegex())

        val x1 = fromPart[3].toInt()
        val y1 = fromPart[1].toInt()

        val toPart = sp[1].split("[=,]".toRegex())

        val x2 = toPart[3].toInt()
        val y2 = toPart[1].toInt()

        Pair(Pair(x1, y1), Pair(x2, y2))
    }
}
