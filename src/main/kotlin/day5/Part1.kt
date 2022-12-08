package day5

//Runtime: O(N)
//Space: O(26) ~ O(1)
fun main() {
    val input = generateSequence(::readLine)
    val list = input.toList()

    val signal = list[0]

    // O(26)
    val array = Array(26) { 0 }

    // O(14)
    for (i in 0 until 14) {
        val c = signal[i].code - 'a'.code

        array[c]++
    }

    // O(N - 4) * 26 ~= O(N)
    for (i in 14 until signal.length) {

        val current = signal[i].code - 'a'.code
        val last = signal[i - 14].code - 'a'.code

        array[last]--
        array[current]++

        if (array.filter { it == 1 }.sum() == 14) {

            println("Found ${i + 1}")
            return
        }
    }
}