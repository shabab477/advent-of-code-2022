package day1


//Your total score is the sum of your scores for each round.
// The score for a single round is the score for the shape you selected
// (1 for Rock, 2 for Paper, and 3 for Scissors) plus the score for the outcome of the round
// (0 if you lost, 3 if the round was a draw, and 6 if you won).
fun main(args: Array<String>) {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val lookup = hashMapOf(
        "A-X" to (1 + 3),
        "A-Y" to (2 + 6),
        "A-Z" to (3 + 0),
        "B-X" to (1 + 0),
        "B-Y" to (2 + 3),
        "B-Z" to (3 + 6),
        "C-X" to (1 + 6),
        "C-Y" to (2 + 0),
        "C-Z" to (3 + 3),
    )

    println(list.sumOf {
        val x = it.split("\\s+".toRegex())
        val l = "${x[0]}-${x[1]}"

        lookup[l]!!
    })
}