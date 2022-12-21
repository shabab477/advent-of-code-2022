package day10

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList().filter { it.isNotEmpty() }

    val total = 8
    val monkeys = Array(total) { MonkeyLogic(logic = {
            return@MonkeyLogic Pair(-1, -1)
        })
    }

    val monkeyBusinessCount = Array(8) {0}

    var monkeyCount = 0

    list.chunked(6).map {
        val items = it[1].split(":")[1].split(",").map { it.trim() }.map { it.toInt() }
        val monkey = monkeys[monkeyCount++]
        monkey.holding.addAll(items)

        val operationPart = it[2].split("=")[1]

        if (operationPart.contains("*")) {

            val rightSideOp = operationPart.split("*").last().trim()

            val divisor = it[3].split(":")[1].split("\\s+".toRegex()).last().toInt()
            val trueIf = it[4].split(":")[1].split("\\s+".toRegex()).last().toInt()
            val falseIf = it[5].split(":")[1].split("\\s+".toRegex()).last().toInt()

            monkey.logic = fun(x: Int): Pair<Int, Int> {
                val r = (x * when(rightSideOp) {
                    "old" -> x
                    else -> rightSideOp.toInt()
                }) / 3
                if (r % divisor == 0) {

                    return Pair(r, trueIf)
                }

                return Pair(r, falseIf)
            }
        } else {

            val rightSideOp = operationPart.split("+").last().trim()
            val divisor = it[3].split(":")[1].split("\\s+".toRegex()).last().toInt()
            val trueIf = it[4].split(":")[1].split("\\s+".toRegex()).last().toInt()
            val falseIf = it[5].split(":")[1].split("\\s+".toRegex()).last().toInt()

            monkey.logic = fun(x: Int): Pair<Int, Int> {
                val r = (x + when(rightSideOp) {
                    "old" -> x
                    else -> rightSideOp.toInt()
                }) / 3

                if (r % divisor == 0) {

                    return Pair(r, trueIf)
                }

                return Pair(r, falseIf)
            }

        }
    }

    for (i in 0 until 20) {

        for (c in 0 until total) {

            val monkey = monkeys[c]
            monkeyBusinessCount[c] += monkey.holding.size

            for (x in 0 until monkey.holding.size) {
                val num = monkey.holding[x]
                val to = monkey.logic(num)

                monkeys[to.second].holding.add(to.first)
            }

            monkey.holding.clear()
        }
    }

    monkeyBusinessCount.sortDescending()
    println(monkeyBusinessCount[0] * monkeyBusinessCount[1])
}


class MonkeyLogic(
    val holding: MutableList<Int> = mutableListOf(),
    var logic: (x: Int) -> Pair<Int, Int>
)