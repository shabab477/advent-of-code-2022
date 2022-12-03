package day0

import kotlin.math.max

fun main(args: Array<String>) {

    var maxCalorie = 0
    var totalCalorie = 0

    val input = generateSequence(::readLine)
    val list = input.toList()
    for (v in list) {

        if (v.isEmpty()) {

            maxCalorie = max(maxCalorie, totalCalorie)
            totalCalorie = 0
        } else {

            totalCalorie += v.toInt()
        }
    }

    println(maxCalorie)
}