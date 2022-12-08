package day6


fun main() {

    val input = generateSequence(::readLine)
    val list = input.toList()

    val root = FileNode("/")
    var current = root

    for (i in 1 until list.size) {
        val line = list[i]

        when {
            line.startsWith("$") -> {
                val parts = line.split("\\s+".toRegex())

                current = run curr@ {
                    when(parts[1]) {
                        "cd" -> {
                            val newNode: FileNode = when(val goTo = parts[2]) {
                                ".." -> current.parent!!
                                else -> current.getDir(goTo)
                            }

                            return@curr newNode
                        }
                        else -> return@curr current
                    }
                }
            }
            else -> {

                val parts = line.split("\\s+".toRegex())
                when(parts[0]) {
                    "dir" -> {

                        current.addDir(parts[1])
                    }
                    else -> {
                        val size = parts[0].toInt()
                        val name = parts[1]

                        current.addFile(name, size)
                    }
                }
            }
        }
    }

    val totalUnused = 70000000 - root.getTotalSum()
    println(
        root.getEachLevelSize()
        .sortedBy { it.getTotalSum() }
        .first { totalUnused + it.getTotalSum() > 30000000 }
        .getTotalSum()
    )
}