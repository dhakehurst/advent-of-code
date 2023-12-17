package day_17

import net.akehurst.language.collections.binaryHeap
import net.akehurst.language.collections.binaryHeapMin
import net.akehurst.language.collections.mutableStackOf

fun <T> ifOrNull(condition: Boolean, f: () -> T) = if (condition) f() else null

class PathEndPoint(
    val x: Int,
    val y: Int,
    val v: ULong,
    val dir: Dir,
    val fMax: Int,
    val loss: ULong
) {

    var prev: PathEndPoint? = null
    val path: String
        get() = when (prev) {
            null -> "$this"
            else -> "${prev!!.path}\n$this"
        }

    fun next(grid: GridAsString, forMin:Int, forMax:Int): List<PathEndPoint> {
        val l = if (fMax >= forMin) grid.leftLossAt(this)?.let { left(it) } else null
        val r = if (fMax >= forMin) grid.rightLossAt(this)?.let { right(it) } else null
        val f1Inc = grid.forwardLossAt(1, this)
        val f1 = if (fMax < forMax) f1Inc?.let { forward(1, it) } else null
       // val f2 = if (fMax > 1) f1Inc?.let { grid.forwardLossAt(2, this)?.let { forward(2, it + f1Inc) } } else null
//        val f3 = if (fMax > 2) f2?.let { grid.forwardLossAt(1, f2)?.let { forward(3, grid, it + f2.loss) } } else null

        return listOf(l, r, f1, ).filterNotNull()
    }

    fun left(lossInc: ULong) = when (dir) {
        Dir.R -> PathEndPoint(x, y - 1, lossInc, Dir.U, 0, loss + lossInc)
        Dir.D -> PathEndPoint(x + 1, y, lossInc, Dir.R, 0, loss + lossInc)
        Dir.L -> PathEndPoint(x, y + 1, lossInc, Dir.D, 0, loss + lossInc)
        Dir.U -> PathEndPoint(x - 1, y, lossInc, Dir.L, 0, loss + lossInc)
    }

    fun right(lossInc: ULong) = when (dir) {
        Dir.R -> PathEndPoint(x, y + 1, lossInc, Dir.D, 0, loss + lossInc)
        Dir.D -> PathEndPoint(x - 1, y, lossInc, Dir.L, 0, loss + lossInc)
        Dir.L -> PathEndPoint(x, y - 1, lossInc, Dir.U, 0, loss + lossInc)
        Dir.U -> PathEndPoint(x + 1, y, lossInc, Dir.R, 0, loss + lossInc)
    }

    fun forward(n: Int, lossInc: ULong) = when (dir) {
        Dir.R -> PathEndPoint(x + n, y, lossInc, Dir.R, fMax + n, loss + lossInc)
        Dir.D -> PathEndPoint(x, y + n, lossInc, Dir.D, fMax + n, loss + lossInc)
        Dir.L -> PathEndPoint(x - n, y, lossInc, Dir.L, fMax + n, loss + lossInc)
        Dir.U -> PathEndPoint(x, y - n, lossInc, Dir.U, fMax + n, loss + lossInc)
    }

    override fun hashCode(): Int = arrayOf(x, y,dir,fMax ).contentHashCode()
    override fun equals(other: Any?): Boolean = when {
        other !is PathEndPoint -> false
        this.x != other.x -> false
        this.y != other.y -> false
        this.dir != other.dir -> false
        this.fMax != other.fMax -> false
        //this.loss != other.loss -> false
        else -> true
    }

    override fun toString(): String = "($x,$y,$dir,$fMax) +$v = $loss"
}

fun computePathLoss(grid: GridAsString, goal: Pair<Int, Int>, starts: List<PathEndPoint>, forMin:Int, forMax:Int): ULong {
    var shortest: PathEndPoint? = null
    //val stack = mutableStackOf<PathEndPoint>()
    val stack = binaryHeapMin<ULong, PathEndPoint>()
    starts.forEach { stack.set(it.loss, it) }
    val visited = mutableMapOf<PathEndPoint, ULong>()
    visited[starts.first()] = 0UL
    while (stack.isNotEmpty) {
        val pp = stack.extractRoot()!!
        when {
            pp.x == goal.first && pp.y == goal.second -> {
                shortest = when {
                    null == shortest -> {
                        pp
                    }

                    else -> listOf(shortest, pp).minBy { it.loss }
                }
            }

            else -> {
                val next = pp.next(grid, forMin, forMax)//.filterNot { visited.contains(it) }
                for (np in next) {
                    np.prev = pp
                    val vis = visited[np]
                    when {
                        vis == null -> {
                            visited[np] = np.loss
                            stack.set(np.loss, np)
                        }

                        vis > np.loss -> {
                            visited[np] = np.loss
                            stack.set(np.loss, np)
                        }

                        else -> Unit //drop it
                    }
                }
            }
        }
    }
    //println(shortest!!.path)
    return shortest!!.loss
}


fun task1(lines: List<String>): ULong {
    val grid = GridAsString(lines.map { it.toMutableList() })

    val s1 = PathEndPoint(0, 0, 0u, Dir.R, 2, 0UL)
    val s2 = PathEndPoint(0, 0, 0u, Dir.D, 2, 0UL)
    val v1 = computePathLoss(grid, Pair(grid.size.cols - 1, grid.size.rows - 1), listOf(s2, s1), 0, 2)

    return v1
}