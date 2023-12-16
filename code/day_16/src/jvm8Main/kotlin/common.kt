package day_16

import net.akehurst.language.collections.mutableStackOf

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

    fun getOrNull(x: Int, y: Int): Char? = lines.getOrNull(y)?.getOrNull(x)

    fun set(x: Int, y: Int, value:Char) {
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
}

enum class Dir { R, U, L, D }

data class Beam(
    val x: Int,
    val y: Int,
    val direction: Dir
) {
    fun hit(c: Char): List<Beam> =when (direction) {
        Dir.R -> when (c) {
            '.' -> listOf(Beam(x+1,y, Dir.R))
            '/' -> listOf(Beam(x,y-1, Dir.U))
            '\\' -> listOf(Beam(x,y+1, Dir.D))
            '|' -> listOf(Beam(x,y-1, Dir.U),Beam(x,y+1, Dir.D))
            '-' -> listOf(Beam(x+1,y, Dir.R))
            else -> error("")
        }

        Dir.U -> when (c) {
            '.' -> listOf(Beam(x,y-1, Dir.U))
            '/' ->listOf(Beam(x+1,y, Dir.R))
            '\\' -> listOf(Beam(x-1,y, Dir.L))
            '|' -> listOf(Beam(x,y-1, Dir.U))
            '-' ->listOf(Beam(x-1,y, Dir.L),Beam(x+1,y, Dir.R))
            else -> error("")
        }

        Dir.L -> when (c) {
            '.' -> listOf(Beam(x-1,y, Dir.L))
            '/' -> listOf(Beam(x,y+1, Dir.D))
            '\\' -> listOf(Beam(x,y-1, Dir.U))
            '|' -> listOf(Beam(x,y-1, Dir.U), Beam(x,y+1, Dir.D))
            '-' -> listOf(Beam(x-1,y, Dir.L))
            else -> error("")
        }

        Dir.D -> when (c) {
            '.' -> listOf(Beam(x,y+1, Dir.D))
            '/' -> listOf(Beam(x-1,y, Dir.L))
            '\\' -> listOf(Beam(x+1,y, Dir.R))
            '|' -> listOf(Beam(x,y+1, Dir.D))
            '-' -> listOf(Beam(x-1,y, Dir.L),Beam(x+1,y, Dir.R))
            else -> error("")
        }
    }
}

fun energise(grid:GridAsString, start:Beam): Int {
    val beams = mutableStackOf(start)
    val energised = mutableSetOf<Beam>()

    while (beams.isNotEmpty) {
        val bm = beams.pop()
        val c = grid.getOrNull(bm.x,bm.y)
        when(c) {
            null -> Unit // beam left grid
            else -> {
                energised.add(bm)
                bm.hit(c).forEach {
                    if(energised.contains(it)) {
                        //stop
                    } else {
                        beams.push(it)
                    }
                }
            }
        }

    }

    return energised.map{Pair(it.x,it.y)}.toSet().size
}