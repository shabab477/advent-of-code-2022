package day9

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val queue = ArrayDeque<Int>()
    var cycleNum = 1
    var x = 1
    var sum = 0

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

        if (cycleNum == 20 ||
            cycleNum == 60 ||
            cycleNum == 100||
            cycleNum == 140||
            cycleNum == 180||
            cycleNum == 220
        ) {

            sum += (x * cycleNum)
        }

    }

    println(sum)
}