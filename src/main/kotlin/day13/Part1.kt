package day13

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val matrix = Array(200) { Array(1200) { '.' } }
    populateMatrixForP2(matrix, list)
//    printMatrix(matrix)

    var dropCount = 0
    while(!isBlocked(matrix, Pair(0, 500))) {

        dropSandForP2(matrix, Pair(0, 500))
        dropCount++

//        printMatrix(matrix)
    }

    println(dropCount)
}

fun populateMatrixForP2(matrix: Array<Array<Char>>, list: List<String>) {
    populateMatrix(matrix, list)
    val m = findMax(list) + 2

    for (i in matrix[m].indices) {
        matrix[m][i] = '#'
    }
}

fun findMax(list: List<String>): Int {

    return list
        .flatMap { it.split("->") }.maxOf {
            it.split(",")[1].trim().toInt()
        }
}

fun dropSandForP2(matrix: Array<Array<Char>>, pos: Pair<Int, Int>) {

    val downPos = pos.copy(first = pos.first + 1)
    val leftDownPos = downPos.copy(second = pos.second - 1)
    val rightDownPos = downPos.copy(second = pos.second + 1)

    val allPos = listOf(downPos, leftDownPos, rightDownPos)
    if (allPos.all { isBlocked(matrix, it)  }) {

        matrix[pos.first][pos.second] = 'O'
    } else if (!isBlocked(matrix, downPos)) {

        dropSandForP2(matrix, downPos)
    } else if (!isBlocked(matrix, leftDownPos)) {

        dropSandForP2(matrix, leftDownPos)
    } else {

        dropSandForP2(matrix, rightDownPos)
    }
}

