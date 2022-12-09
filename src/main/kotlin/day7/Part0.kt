package day7

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val matrix = buildMatrix(list)
    var counter = (matrix.size * 2) + (matrix[0].size * 2) - 4

    for (c in 1 until matrix.size - 1) {

        for (i in 1 until matrix[0].size - 1) {

            val check = isVisible(matrix, matrix[c][i], c + 1, i) { x, y ->
                Pair(x + 1, y)
            } || isVisible(matrix, matrix[c][i], c - 1, i) { x, y ->
                Pair(x - 1, y)
            } || isVisible(matrix, matrix[c][i], c, i + 1) { x, y ->
                Pair(x, y + 1)
            } || isVisible(matrix, matrix[c][i], c, i - 1) { x, y ->
                Pair(x, y - 1)
            }

            if (check) {
                counter++
            }
        }
    }

    println(counter)
}


fun isVisible(matrix: Array<IntArray>,
              v: Int,
              c: Int,
              i: Int,
              nextPosition: (c: Int, i: Int) -> Pair<Int, Int>
): Boolean {
    if (c < 0 || c >= matrix.size) {

        return true
    } else if (i < 0 || i >= matrix[0].size) {

        return true
    }

    val current = matrix[c][i]
    if (current >= v) {

        return false
    }

    val p = nextPosition(c, i)

    return isVisible(matrix, v, p.first, p.second, nextPosition)
}


fun buildMatrix(input: List<String>): Array<IntArray> {

    return Array(input.size) {
        input[it].toCharArray().map { ch -> ch.code - '0'.code }.toIntArray()
    }
}