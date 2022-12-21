package day13

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val matrix = Array(200) { Array(550) { '.' } }
    populateMatrix(matrix, list)
//    printMatrix(matrix)

    var dropCount = 0
    while(!dropSand(matrix, Pair(0, 500))) {

        dropCount++

//        printMatrix(matrix)
    }

    println(dropCount)
}

fun dropSand(matrix: Array<Array<Char>>, pos: Pair<Int, Int>): Boolean {

    if (pos.first == matrix.size - 1) {

        return true
    }

    val downPos = pos.copy(first = pos.first + 1)
    val leftDownPos = downPos.copy(second = pos.second - 1)
    val rightDownPos = downPos.copy(second = pos.second + 1)

    val allPos = listOf(downPos, leftDownPos, rightDownPos)
    if (allPos.all { isBlocked(matrix, it)  }) {

        matrix[pos.first][pos.second] = 'O'
        return false
    }

    if (!isBlocked(matrix, downPos)) {

        return dropSand(matrix, downPos)
    } else if (!isBlocked(matrix, leftDownPos)) {

        return dropSand(matrix, leftDownPos)
    }

    return dropSand(matrix, rightDownPos)
}

fun isBlocked(matrix: Array<Array<Char>>, pos: Pair<Int, Int>): Boolean {

    val v = matrix[pos.first][pos.second]
    return (v == '#' || v == 'O')
}


fun printMatrix(matrix: Array<Array<Char>>) {

    for (line in matrix) {
        println(line.copyOfRange(490, 505).map { it }.joinToString(" "))
    }
}

fun populateMatrix(matrix: Array<Array<Char>>, list: List<String>) {

    for (line in list) {

        val parts = line.split("->").map { it.trim() }.map {
            val p = it.split(",")

            Pair(p[1].toInt(), p[0].toInt())
        }

        parts.windowed(2).map {
            val from = it[0]
            val to = it[1]

            val dx = if (to.first - from.first < 0) -1 else if (to.first - from.first == 0) 0 else 1
            val dy = if (to.second - from.second < 0) -1 else if (to.second - from.second == 0) 0  else 1

            var current = from.copy()
            matrix[current.first][current.second] = '#'
            matrix[to.first][to.second] = '#'

            while(current != to) {

                matrix[current.first][current.second] = '#'
                current = current.copy(first = current.first + dx, second = current.second + dy)
            }
        }
    }
}
