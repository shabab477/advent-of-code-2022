package day0

import kotlin.math.max

// part - 1
fun main(args: Array<String>) {

    val results = mutableListOf<Int>()
    var totalCalorie = 0

    val input = generateSequence(::readLine)
    val list = input.toList()
    for (v in list) {

        if (v.isEmpty()) {

            results.add(totalCalorie)
            totalCalorie = 0
        } else {

            totalCalorie += v.toInt()
        }
    }

    results.sortDescending()
    println(results[0] + results[1] + results[2])
}