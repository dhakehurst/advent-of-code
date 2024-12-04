package day_04

import korlibs.datastructure.getCyclicOrNull

data class Size(
    val cols: Int,
    val rows: Int
)

class Grid<T>(
    /**
     * list of rows
     */
    val lines: List<List<T>>
) {
    val size get() = Size(lines[0].size, lines.size)

    fun getOrNull(x: Int, y: Int): T? = lines.getOrNull(y)?.getCyclicOrNull(x)

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
        for (i in 0 .. lines.size - rowSize) {
            for (j in 0 .. columns.size - colSize) {
                val gl = mutableListOf<List<T>>()
                for (r in 0 until rowSize) {
                    gl.add(lines[i+r].subList(j,j+colSize))
                }
                val g = Grid(gl)
                windows.add( g)
            }
        }
        return windows
    }

    override fun toString(): String {
        return lines.joinToString(separator = "\n") { it.joinToString(separator = "") }
    }
}

fun task1(lines: List<String>) {
    val XMAS = Regex("XMAS")
    val SAMX = Regex("SAMX")
    val grid = Grid<Char>(lines.map { it.toList() })
    var total = 0

    var c1 = 0
    println("rows ${grid.lines.size}")
    for (row in grid.lines) {
        c1 += XMAS.findAll(row.joinToString(separator = "")).count()
        c1 += SAMX.findAll(row.joinToString(separator = "")).count()
    }
    println("rows found $c1")

    var c2 = 0
    println("columns ${grid.columns.size}")
    for (col in grid.columns) {
        c2 += XMAS.findAll(col.joinToString(separator = "")).count()
        c2 += SAMX.findAll(col.joinToString(separator = "")).count()

    }
    println("columns found $c2")

    var c3 = 0
    println("diag up ${grid.diagonalsUp.size}")
    for (diag in grid.diagonalsUp) {
        println("diag: $diag")
        c3 += XMAS.findAll(diag.joinToString(separator = "")).count()
        c3 += SAMX.findAll(diag.joinToString(separator = "")).count()

    }
    println("diag up found $c3")

    var c4 = 0
    println("diag down ${grid.diagonalsDown.size}")
    for (diag in grid.diagonalsDown) {
        println("diag: $diag")
        c4 += XMAS.findAll(diag.joinToString(separator = "")).count()
        c4 += SAMX.findAll(diag.joinToString(separator = "")).count()

    }
    println("diag down found $c4")

    total = c1 + c2 + c3 + c4
    println(total)
}