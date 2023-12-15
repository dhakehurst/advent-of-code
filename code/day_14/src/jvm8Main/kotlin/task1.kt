package day_14

import kotlin.math.pow

class GridAsLongs(
    /**
     * list of rows
     */
    val lines: List<Long>,
    val size:Size
) {
    val columns = IntRange(0, size.cols - 1).map { col ->
        val colMask = 2L.pow(size.cols - col)
        lines.foldIndexed(0L) { x, a, i ->
            when (i and colMask) {
                0L -> a
                else -> a + x * (2L.pow(x + 1))
            }
        }
    }

    override fun toString(): String {
        return lines.joinToString(separator = "\n") {
            val s = it.toString(2)
            s.padStart(size.cols,'0')
        }
    }
}

val Int.numBits get() = this.countOneBits()

fun List<String>.toGrids(): List<GridAsLongs> {
    val grids = mutableListOf<GridAsLongs>()
    var lastLineLength = -1
    var l = mutableListOf<Long>()
    for (line in this) {
        when {
            line.isBlank() -> {
                val size = Size(lastLineLength, l.size)
                grids.add(GridAsLongs(l, size))
                l = mutableListOf<Long>()
            }

            else -> {

                l.add(line.asBinaryLong)
                lastLineLength = line.length
            }
        }
    }
    grids.add(GridAsLongs(l,Size(lastLineLength, l.size)))
    return grids
}

fun Long.pow(n: Int): Long = this.toDouble().pow(n).toLong()
val String.asBinaryLong: Long
    get() = this.foldIndexed(0L) { i, a, c ->
        when (c) {
            'O' -> a + (2L.pow((this.length-1)-i ))
            else -> a
        }
    }

fun progressionSum(numElements: Int, length: Int) = (length + (length - numElements)) * (numElements / 2)

fun String.tiltAndSumWeight(): Long {
    var sum = 0L
    var slideToRow = this.length
    for (i in 0 until this.length) {
        val c = this[i]
        when (c) {
            '.' -> {}
            '#' -> {
                slideToRow = this.length - (i + 1)
            }

            'O' -> {
                sum += slideToRow
                slideToRow--
            }
        }
    }
    return sum
}



fun task1(lines: List<String>): Long {
    var total = 0L
    val grid = GridAsString(lines)

    for (col in grid.columns) {
        val t = col.tilt()
        val v = t.sumWeight()
        total += v//col.tiltAndSumWeight()
    }

    return total
}