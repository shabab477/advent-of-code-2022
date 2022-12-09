package day7

import java.lang.Integer.max


fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val matrix = buildMatrix(list)
    var bestScore = 0

    for (c in 1 until matrix.size - 1) {

        for (i in 1 until matrix[0].size - 1) {

            val score = findScenicScore(matrix, matrix[c][i], 0, c + 1, i) { x, y ->
                Pair(x + 1, y)
            } * findScenicScore(matrix, matrix[c][i], 0, c - 1, i) { x, y ->
                Pair(x - 1, y)
            } * findScenicScore(matrix, matrix[c][i], 0, c, i + 1) { x, y ->
                Pair(x, y + 1)
            } * findScenicScore(matrix, matrix[c][i], 0, c, i - 1) { x, y ->
                Pair(x, y - 1)
            }

            bestScore = max(score, bestScore)
        }
    }

    println(bestScore)
}

fun findScenicScore(matrix: Array<IntArray>,
                v: Int,
                score: Int,
                c: Int,
                i: Int,
                nextIndex: (c: Int, i: Int) -> Pair<Int, Int>
): Int {
    if (c < 0 || c >= matrix.size) {

        return score
    } else if (i < 0 || i >= matrix[0].size) {

        return score
    }

    val current = matrix[c][i]
    if (current >= v) {

        return score + 1
    }

    val p = nextIndex(c, i)

    return findScenicScore(matrix, v, score + 1, p.first, p.second, nextIndex)
}

