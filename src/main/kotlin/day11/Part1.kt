package day11

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val matrix = buildMatrix(list)
    val startEnd = findStartAndEnd(matrix)

    val e = startEnd.second
    val results = mutableListOf<Int>()
    for (c in matrix.indices) {

        for (i in matrix[0].indices) {

            if (matrix[c][i] == 'a' || matrix[c][i] == 'S') {
                results.add(bfs(matrix, Pair(c, i), e))
            }
        }
    }

    println(results.minOf { it })
}

fun bfs(matrix: Array<CharArray>, s: Pair<Int, Int>, e: Pair<Int, Int>): Int {

    val q = ArrayDeque<Node>(matrix.size * matrix[0].size)
    q.addLast(Node.toNode(s, matrix, 0, null))

    val visited = mutableSetOf<Pair<Int, Int>>()

    while(q.isNotEmpty()) {
        val n = q.removeFirst()

        if (visited.contains(n.pos)) {

            continue
        }
        visited.add(n.pos)

        if (n.pos == e) {
//            n.printPath()
            println("FOUND: ${n.steps}")

            return n.steps
        }

        getPossiblePositions(n, matrix).map {
            Node.toNode(
                it,
                matrix,
                n.steps + 1,
                n
            )
        }.filter {
            return@filter !visited.contains(it.pos)
        }.forEach {
            q.addLast(it)
        }
    }

    return Integer.MAX_VALUE
}


