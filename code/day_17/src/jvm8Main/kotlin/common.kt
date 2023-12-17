package day_17

data class Size(
    val cols: Int,
    val rows: Int
)

val List<Char>.join get() = this.joinToString(separator = "")

class GridAsString(
    /**
     * list of rows
     */
    val lines: List<MutableList<Char>>
) {
    val size get() = Size(lines[0].size, lines.size)

    fun getOrNull(x: Int, y: Int): Char? =
        lines.getOrNull(y)?.getOrNull(x)

    fun set(x: Int, y: Int, value: Char) {
        lines.get(y)[x] = value
    }

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

    fun lossAt(x: Int, y: Int): ULong? =
        getOrNull(x, y)?.digitToInt()?.toULong()

    fun leftLossAt(pp: PathEndPoint) = when (pp.dir) {
        Dir.R -> getOrNull(pp.x, pp.y - 1)?.digitToInt()?.toULong()
        Dir.D -> getOrNull(pp.x + 1, pp.y)?.digitToInt()?.toULong()
        Dir.L -> getOrNull(pp.x, pp.y + 1)?.digitToInt()?.toULong()
        Dir.U -> getOrNull(pp.x - 1, pp.y)?.digitToInt()?.toULong()
    }

    fun rightLossAt(pp: PathEndPoint) = when (pp.dir) {
        Dir.R -> getOrNull(pp.x, pp.y + 1)?.digitToInt()?.toULong()
        Dir.D -> getOrNull(pp.x - 1, pp.y)?.digitToInt()?.toULong()
        Dir.L -> getOrNull(pp.x, pp.y - 1)?.digitToInt()?.toULong()
        Dir.U -> getOrNull(pp.x + 1, pp.y)?.digitToInt()?.toULong()
    }

    fun forwardLossAt(n: Int, pp: PathEndPoint) = when (pp.dir) {
        Dir.R -> getOrNull(pp.x + 1, pp.y)?.digitToInt()?.toULong()
        Dir.D -> getOrNull(pp.x, pp.y + 1)?.digitToInt()?.toULong()
        Dir.L -> getOrNull(pp.x - 1, pp.y)?.digitToInt()?.toULong()
        Dir.U -> getOrNull(pp.x, pp.y - 1)?.digitToInt()?.toULong()
    }
}

enum class Dir { R, U, L, D }

