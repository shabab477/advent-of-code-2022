package day12


fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    var res = mutableListOf<Any>()
    println(list.chunked(3).withIndex().forEach { it ->
        val index = it.index
        val p1 = it.value[0]
        val p2 = it.value[1]

        val x1 = convert(p1)[0] as MutableList<Any>
        val x2 = convert(p2)[0] as MutableList<Any>

        assert(x1.toString().filter { it != ' ' } == p1)
        assert(x2.toString().filter { it != ' ' } == p2)

        res.add(x1)
        res.add(x2)
    })

    val div1 = mutableListOf(mutableListOf(2))
    val div2 = mutableListOf(mutableListOf(6))

    res.add(div1)
    res.add(div2)

    res.sortWith { x, y ->

        return@sortWith isValid(x, y)
    }

//    println(res.reversed().forEach(::println))
    val ans = res.reversed()

    println((ans.indexOf(div1) + 1) * (ans.indexOf(div2) + 1))
}

