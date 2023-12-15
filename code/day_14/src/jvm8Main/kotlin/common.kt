package day_14

data class Size(
    val cols: Int,
    val rows: Int
)

class GridAsString(
    /**
     * list of rows
     */
    val lines: List<String>
) {
    val size get() = Size(lines[0].length, lines.size)

    fun getOrNull(x: Int, y: Int): Char? = lines.getOrNull(y)?.getOrNull(x)

    val columns: List<String> by lazy {
        val l = mutableListOf<String>()
        for (i in 0 until size.cols) {
            l.add(lines.map { it[i] }.join)
        }
        l
    }

    override fun toString(): String {
        return lines.joinToString(separator = "\n")
    }
}