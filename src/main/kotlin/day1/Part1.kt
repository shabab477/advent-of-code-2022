package day1


//Your total score is the sum of your scores for each round.
// The score for a single round is the score for the shape you selected
// (1 for Rock, 2 for Paper, and 3 for Scissors) plus the score for the outcome of the round
// (0 if you lost, 3 if the round was a draw, and 6 if you won).

//"Anyway, the second column says how the round needs to end: X means you need to lose,
// Y means you need to end the round in a draw, and Z means you need to win. Good luck!"
fun main(args: Array<String>) {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val lookup = hashMapOf(
        "A-3" to 1,
        "A-6" to 2,
        "A-0" to 3,
        "B-0" to 1,
        "B-3" to 2,
        "B-6" to 3,
        "C-6" to 1,
        "C-0" to 2,
        "C-3" to 3,
    )

    println(list.sumOf {
        val x = it.split("\\s+".toRegex())
        val expected = strToOutcomeMapper(x[1])
        val l = "${x[0]}-${expected}"

        lookup[l]!! + expected
    })
}

private fun strToOutcomeMapper(v: String): Int = when(v) {
    "X" -> 0
    "Y" -> 3
    else -> 6
}