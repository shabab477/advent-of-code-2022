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

    println(root.searchAllWithinConstraint().sumOf { it.getTotalSum() })
}


class FileNode(val name: String, val parent: FileNode? = null) {


    private val dirs = mutableSetOf<FileNode>()
    private val files = mutableSetOf<File>()
    private var totalSum = 0

    fun getDir(name: String): FileNode {

        return dirs.first { it.name == name }
    }

    fun getTotalSum() = totalSum

    fun addDir(name: String) {

        dirs.add(FileNode(name, this))
    }

    fun addFile(name: String, size: Int) {

        val f = File(name, size)
        files.add(f)

        addSum(f.size)
    }

    private fun addSum(size: Int) {

        totalSum += size
        parent?.addSum(size)
    }

    fun searchAllWithinConstraint(): List<FileNode> {
        val nodes = if (totalSum <= 100000) mutableListOf(this) else mutableListOf()
        nodes.addAll(dirs.flatMap { it.searchAllWithinConstraint() })

        return nodes
    }

    fun getEachLevelSize(): List<FileNode> {
        val nodes = mutableListOf(this)
        nodes.addAll(dirs.flatMap { it.getEachLevelSize() })

        return nodes
    }

    override fun equals(other: Any?): Boolean {
        return when(other) {
            is FileNode -> {
                return other.name == name
            }
            else -> false
        }
    }

    override fun hashCode(): Int {

        return name.hashCode()
    }

}

data class File(
    val name: String,
    val size: Int
) {

    override fun equals(other: Any?): Boolean {
        return when(other) {
            is File -> {
                return other.name == name
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + size

        return result
    }
}