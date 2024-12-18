package advent.of.code.lib

import korlibs.datastructure.getCyclicOrNull
import net.akehurst.language.collections.binaryHeapMin

data class Size(
    val cols: Int,
    val rows: Int
)

class Grid<T>(
    /**
     * list of rows
     */
    initLines: List<List<T>>,
    val costFunction: (T) -> ULong?,
) {
    constructor(xSize: Int, ySize: Int, init: T, costFunction: (T) -> ULong?) : this(
        List(xSize) { List(ySize) { init } },
        costFunction
    )

    private val _lines = initLines.map { it.toMutableList() }.toMutableList()
    val lines: List<List<T>> = _lines

    val size get() = Size(lines[0].size, lines.size)

    fun getCyclicOrNull(x: Int, y: Int): T? = lines.getOrNull(y)?.getCyclicOrNull(x)

    val columns: List<List<T>> by lazy {
        val l = mutableListOf<List<T>>()
        for (i in 0 until size.cols) {
            l.add(lines.map { it[i] })
        }
        l
    }

    val diagonalsUp: List<List<T>> by lazy {
        val l = mutableListOf<List<T>>()
        var i = 0
        var j = 0
        while (j < columns.size) {
            val d = diagonalUpStartingAt(i, j)
            l.add(d)
            j++
        }
        j = columns.size - 1
        i = 1
        while (i < lines.size) {
            val d = diagonalUpStartingAt(i, j)
            l.add(d)
            i++
        }
        l
    }

    val diagonalsDown: List<List<T>> by lazy {
        val l = mutableListOf<List<T>>()
        var i = 0
        var j = columns.size - 1
        while (j >= 0) {
            val d = diagonalDownStartingAt(i, j)
            l.add(d)
            j--
        }
        j = 0
        i = 1
        while (i < lines.size) {
            val d = diagonalDownStartingAt(i, j)
            l.add(d)
            i++
        }
        l
    }

    fun getOrNull(x: Int, y: Int): T? = lines.getOrNull(y)?.getOrNull(x)

    operator fun get(x: Int, y: Int): T = _lines[y][x]
    operator fun set(x: Int, y: Int, value: T) {
        _lines[y][x] = value
    }

    fun diagonalUpStartingAt(ist: Int, jst: Int): List<T> {
        val l = mutableListOf<T>()
        var i = ist
        var j = jst
        while (j >= 0 && i < lines.size) {
            l.add(lines[i][j])
            i++
            j--
        }
        return l
    }

    fun diagonalDownStartingAt(ist: Int, jst: Int): List<T> {
        val l = mutableListOf<T>()
        var i = ist
        var j = jst
        while (j < columns.size && i < lines.size) {
            l.add(lines[i][j])
            i++
            j++
        }
        return l
    }

    fun windowsOfSize(rowSize: Int, colSize: Int): List<Grid<T>> {
        val windows = mutableListOf<Grid<T>>()
        for (i in 0..lines.size - rowSize) {
            for (j in 0..columns.size - colSize) {
                val gl = mutableListOf<List<T>>()
                for (r in 0 until rowSize) {
                    gl.add(lines[i + r].subList(j, j + colSize))
                }
                val g = Grid(gl, costFunction)
                windows.add(g)
            }
        }
        return windows
    }

    fun shortestPath(starts: List<PathEndPoint<T>>, goal: Pair<Int, Int>, pathValue: T,forwardMin:Int, forwardMax:Int): PathEndPoint<T>? {
        var shortest: PathEndPoint<T>? = null
        val stack = binaryHeapMin<ULong, PathEndPoint<T>>()
        starts.forEach { stack.set(it.cost, it) }
        val visited = mutableMapOf<PathEndPoint<T>, ULong>()
        visited[starts.first()] = 0UL
        while (stack.isNotEmpty) {
            val pp = stack.extractRoot()!!
            when {
                pp.x == goal.first && pp.y == goal.second -> {
                    shortest = when {
                        null == shortest -> {
                            pp
                        }

                        else -> listOf(shortest, pp).minBy { it.cost }
                    }
                }

                else -> {
                    val next = pp.next(this, forwardMin, forwardMax)//.filterNot { visited.contains(it) }
                    for (np in next) {
                        np.prev = pp
                        val vis = visited[np]
                        when {
                            vis == null -> {
                                visited[np] = np.cost
                                stack.set(np.cost, np)
                            }

                            vis > np.cost -> {
                                visited[np] = np.cost
                                stack.set(np.cost, np)
                            }

                            else -> Unit //drop it
                        }
                    }
                }
            }
        }
        return shortest
    }

    fun costAt(x: Int, y: Int): ULong? = getOrNull(x, y)?.let { costFunction.invoke(it) }

    fun leftCostAt(pp: PathEndPoint<T>) = when (pp.dir) {
        Dir.R -> costAt(pp.x, pp.y - 1)
        Dir.D -> costAt(pp.x + 1, pp.y)
        Dir.L -> costAt(pp.x, pp.y + 1)
        Dir.U -> costAt(pp.x - 1, pp.y)
    }

    fun rightCostAt(pp: PathEndPoint<T>) = when (pp.dir) {
        Dir.R -> costAt(pp.x, pp.y + 1)
        Dir.D -> costAt(pp.x - 1, pp.y)
        Dir.L -> costAt(pp.x, pp.y - 1)
        Dir.U -> costAt(pp.x + 1, pp.y)
    }

    fun forwardCostAt(n: Int, pp: PathEndPoint<T>) = when (pp.dir) {
        Dir.R -> costAt(pp.x + 1, pp.y)
        Dir.D -> costAt(pp.x, pp.y + 1)
        Dir.L -> costAt(pp.x - 1, pp.y)
        Dir.U -> costAt(pp.x, pp.y - 1)
    }

    override fun toString(): String {
        return lines.joinToString(separator = "\n") { it.joinToString(separator = "") }
    }
}

enum class Dir { R, U, L, D }

class PathEndPoint<T>(
    val x: Int,
    val y: Int,
    val v: ULong,
    val dir: Dir,
    val fMax: Int,
    val cost: ULong
) {

    var prev: PathEndPoint<T>? = null
    val path: String
        get() = when (prev) {
            null -> "$this"
            else -> "${prev!!.path}\n$this"
        }

    fun next(grid: Grid<T>, forMin: Int, forMax: Int): List<PathEndPoint<T>> {
        val l = if (fMax >= forMin) grid.leftCostAt(this)?.let { left(it) } else null
        val r = if (fMax >= forMin) grid.rightCostAt(this)?.let { right(it) } else null
        val f1Inc = grid.forwardCostAt(1, this)
        val f1 = if (fMax < forMax) f1Inc?.let { forward(1, it) } else null
        return listOf(l, r, f1).filterNotNull()
    }

    fun left(lossInc: ULong) = when (dir) {
        Dir.R -> PathEndPoint<T>(x, y - 1, lossInc, Dir.U, 0, cost + lossInc)
        Dir.D -> PathEndPoint<T>(x + 1, y, lossInc, Dir.R, 0, cost + lossInc)
        Dir.L -> PathEndPoint<T>(x, y + 1, lossInc, Dir.D, 0, cost + lossInc)
        Dir.U -> PathEndPoint<T>(x - 1, y, lossInc, Dir.L, 0, cost + lossInc)
    }

    fun right(lossInc: ULong) = when (dir) {
        Dir.R -> PathEndPoint<T>(x, y + 1, lossInc, Dir.D, 0, cost + lossInc)
        Dir.D -> PathEndPoint<T>(x - 1, y, lossInc, Dir.L, 0, cost + lossInc)
        Dir.L -> PathEndPoint<T>(x, y - 1, lossInc, Dir.U, 0, cost + lossInc)
        Dir.U -> PathEndPoint<T>(x + 1, y, lossInc, Dir.R, 0, cost + lossInc)
    }

    fun forward(n: Int, lossInc: ULong) = when (dir) {
        Dir.R -> PathEndPoint<T>(x + n, y, lossInc, Dir.R, fMax + n, cost + lossInc)
        Dir.D -> PathEndPoint<T>(x, y + n, lossInc, Dir.D, fMax + n, cost + lossInc)
        Dir.L -> PathEndPoint<T>(x - n, y, lossInc, Dir.L, fMax + n, cost + lossInc)
        Dir.U -> PathEndPoint<T>(x, y - n, lossInc, Dir.U, fMax + n, cost + lossInc)
    }

    override fun hashCode(): Int = arrayOf(x, y, dir, fMax).contentHashCode()
    override fun equals(other: Any?): Boolean = when {
        other !is PathEndPoint<*> -> false
        this.x != other.x -> false
        this.y != other.y -> false
        this.dir != other.dir -> false
        this.fMax != other.fMax -> false
        //this.loss != other.loss -> false
        else -> true
    }

    override fun toString(): String = "($x,$y,$dir,$fMax) +$v = $cost"
}