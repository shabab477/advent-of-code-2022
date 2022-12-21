package day12

import java.util.*

fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    println(list.chunked(3).withIndex().map { it ->
        val index = it.index
        val p1 = it.value[0]
        val p2 = it.value[1]

        val x1 = convert(p1)[0] as MutableList<Any>
        val x2 = convert(p2)[0] as MutableList<Any>

        assert(x1.toString().filter { it != ' ' } == p1)
        assert(x2.toString().filter { it != ' ' } == p2)

        val res = isValid(x1, x2)

        println(x1)
        println(x2)

        if (res == 1) {

            return@map index + 1
        }

        return@map 0
    }.sum())
}


fun isValid(x1: Any, x2: Any): Int {

    if (x1 is Int && x2 is Int) {

        if (x1 < x2) {

            return 1
        } else if (x1 == x2) {

            return 0
        }

        return -1
    }

    var a = x1
    var b = x2

    if (x1 is List<*> && x2 is Int) {
        b = listOf(x2)
    }

    if (x1 is Int && x2 is List<*>) {
        a = listOf(x1)
    }

    if (a is List<*> && b is List<*>) {

        var c = 0
        var i = 0

        while(c < a.size && i < b.size) {

            val res = isValid(a[c]!!, b[i]!!)
            if (res == 1) {

                return 1
            }

            if (res == -1) {

                return -1
            }

            c++
            i++
        }

        if (c == a.size && i == b.size) {

            return 0
        }

        if (c == a.size) {

            return 1
        }

        return -1
    }

    throw java.lang.IllegalStateException()
}

// [[[0],[[4,2],3],4,6,2],[]]
fun convert(str: String): List<Any> {

    var c = 0
    val list = mutableListOf<Any>()
    while(c < str.length) {
        if (str[c] == ',') {
          c++
        } else if (str[c].isDigit()) {

            var i = c
            var temp = ""
            while (str[i].isDigit()) {
                temp += str[i]
                i++
            }

            list.add(temp.toInt())
            c = i
        } else if(str[c] == '[') {
            val x = convert(str.substring(c + 1, str.length))
            list.add(x)

            c += x.toString().filter { it != ' ' }.length
        } else if (str[c] == ']'){

            return list
        }
    }

    return list
}
