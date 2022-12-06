package day4

import java.util.*

//1 N B D T V G Z J
//2 S R M D W P F
//3 V C R S Z
//4 R T J Z P H G
//5 T C J N D Z Q F
//6 N V P W G S F M
//7 G C V B P Q
//9 W P J
fun main() {

    val totalInput = 9

    val input = generateSequence(::readLine)
    val list = input.toList()

    val listOfStacks = mutableListOf<Stack<Char>>()
    for (i in 0 until totalInput) {
        val parts = list[i].split("\\s+".toRegex())

        listOfStacks.add(Stack())
        parts.takeLast(parts.size - 1).forEach {
            listOfStacks[i].push(it.elementAt(0))
        }
    }

    for (i in totalInput + 1 until list.size) {

        val command = list[i]
        val parts = command.split("\\s+".toRegex())

        val quantity = parts[1].toInt()
        val from = parts[3].toInt() - 1
        val to = parts[5].toInt() - 1

        for (c in 0 until quantity) {

            listOfStacks[to].push(listOfStacks[from].pop())
        }
    }

    println(listOfStacks.map { st -> st.peek() }.joinToString(""))
}