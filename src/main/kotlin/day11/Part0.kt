package day11

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val matrix = buildMatrix(list)
    val startEnd = findStartAndEnd(matrix)

    val s = startEnd.first
    val e = startEnd.second

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

            break
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
}

fun getPossiblePositions(n: Node, matrix: Array<CharArray>): List<Pair<Int, Int>> {

    val x = n.pos.first
    val y = n.pos.second

    return listOf(Pair(x + 1, y), Pair(x - 1, y), Pair(x, y + 1), Pair(x, y - 1)).filter {
        it.first >= 0 && it.first < matrix.size && it.second >= 0 && it.second < matrix[0].size
    }.filter {

        val ch = when(matrix[it.first][it.second]) {
            'E' -> 'z'
            else -> matrix[it.first][it.second]
        }

        val next = when(matrix[n.pos.first][n.pos.second]) {
            'S' -> 'a'
            else -> matrix[n.pos.first][n.pos.second]
        }

        val nextCh = ch.code - next.code
        return@filter nextCh <= 1
    }
}

class Node(
    val v: Char,
    val pos: Pair<Int, Int>,
    val steps: Int,
    val parent: Node?
) {

    companion object {

        fun toNode(p: Pair<Int, Int>, matrix: Array<CharArray>, step: Int, parent: Node?): Node {
            return Node(
                matrix[p.first][p.second],
                p,
                step,
                parent
            )
        }
    }

    fun printPath() {
        parent?.printPath()
        println("${v}: ${pos.first}, ${pos.second}")

    }
}

fun findStartAndEnd(matrix: Array<CharArray>): Pair<Pair<Int, Int>, Pair<Int, Int>> {

    var s = Pair(0, 0)
    var e = Pair(0, 0)

    for (c in matrix.indices) {

        for (i in matrix[c].indices) {
            when(matrix[c][i]) {
                'S' -> s = Pair(c, i)
                'E' -> e = Pair(c, i)
            }

        }
    }

    return Pair(s, e)
}

fun buildMatrix(input: List<String>): Array<CharArray> {

    return Array(input.size) {
        input[it].toCharArray()
    }
}