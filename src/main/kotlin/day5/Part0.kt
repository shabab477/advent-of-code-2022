package day5

//Runtime: O(N)
//Space: θ(26) ~ θ(1)
fun main() {
    val input = generateSequence(::readLine)
    val list = input.toList()

    val signal = list[0]

    // θ(26)
    val array = Array(26) { 0 }

    // O(4) + O(N - 4) * 26 ~= O(N)
    for (i in signal.indices) {

        if (i < 4) {
            val c = signal[i].code - 'a'.code
            array[c]++

            continue
        }

        val current = signal[i].code - 'a'.code
        val last = signal[i - 4].code - 'a'.code

        array[last]--
        array[current]++

        // O(26)
        if (array.filter { it == 1 }.sum() == 4) {

            println("Found ${i + 1}")
            return
        }
    }

}