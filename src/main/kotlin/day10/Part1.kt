package day10

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList().filter { it.isNotEmpty() }

    val total = 8
    val monkeys = Array(total) { LongMonkeyLogic(logic = {
        return@LongMonkeyLogic Pair(-1, -1)
    })
    }

    val m = 9699690L
    val monkeyBusinessCount = Array(8) {0}

    var monkeyCount = 0

    list.chunked(6).map {
        val items = it[1].split(":")[1].split(",").map { it.trim() }.map { it.toLong() }
        val monkey = monkeys[monkeyCount++]
        monkey.holding.addAll(items)

        val operationPart = it[2].split("=")[1]

        if (operationPart.contains("*")) {

            val rightSideOp = operationPart.split("*").last().trim()

            val divisor = it[3].split(":")[1].split("\\s+".toRegex()).last().toLong()
            val trueIf = it[4].split(":")[1].split("\\s+".toRegex()).last().toInt()
            val falseIf = it[5].split(":")[1].split("\\s+".toRegex()).last().toInt()

            monkey.logic = fun(x: Long): Pair<Long, Int> {

                val r = moduloMultiplication(x, when(rightSideOp) {
                    "old" -> x
                    else -> rightSideOp.toLong()
                }, m)
                
                if (r % divisor == 0L) {

                    return Pair(r, trueIf)
                }

                return Pair(r, falseIf)
            }
        } else {

            val rightSideOp = operationPart.split("+").last().trim()
            val divisor = it[3].split(":")[1].split("\\s+".toRegex()).last().toLong()
            val trueIf = it[4].split(":")[1].split("\\s+".toRegex()).last().toInt()
            val falseIf = it[5].split(":")[1].split("\\s+".toRegex()).last().toInt()

            monkey.logic = fun(x: Long): Pair<Long, Int> {
                val r = (x + when(rightSideOp) {
                    "old" -> x
                    else -> rightSideOp.toLong()
                })

                if (r % divisor == 0L) {

                    return Pair(r, trueIf)
                }

                return Pair(r, falseIf)
            }

        }
    }

    for (i in 0 until 10_000) {

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

    println(monkeyBusinessCount.joinToString(", "))
    monkeyBusinessCount.sortDescending()
    println(monkeyBusinessCount[0].toBigInteger().multiply(monkeyBusinessCount[1].toBigInteger()))
}

// Modulo multiplication for geeksforgeeks
fun moduloMultiplication(
    a: Long,
    b: Long, mod: Long
): Long {

    var x = a
    var y = b
    var res: Long = 0

    x %= mod
    while (y > 0) {

        if (y and 1 > 0) {
            res = (res + x) % mod
        }

        x = 2 * x % mod
        y = y shr 1
    }
    return res
}


class LongMonkeyLogic(
    val holding: MutableList<Long> = mutableListOf(),
    var logic: (x: Long) -> Pair<Long, Int>
)