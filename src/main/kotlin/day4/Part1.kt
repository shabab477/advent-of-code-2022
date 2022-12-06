package day4

import java.util.*
import kotlin.collections.ArrayDeque

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

        val stack = Stack<Char>()
        for (c in 0 until quantity) {

            stack.push(listOfStacks[from].pop())
        }

        while(stack.isNotEmpty()) {
            listOfStacks[to].push(stack.pop())
        }
    }

    println(listOfStacks.map { st -> st.peek() }.joinToString(""))
}