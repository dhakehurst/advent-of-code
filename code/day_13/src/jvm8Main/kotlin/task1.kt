package day_13

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

    override fun toString(): String {
        return lines.joinToString(separator = "\n") {it.joinToString(separator = "")}
    }
}

fun Grid<Char>.flip(x: Int, y: Int):Grid<Char> {
    val flines = lines.map {
        it.toMutableList() //clone it
    }
    val c = flines[y][x]
    when(c) {
        '.' -> flines[y][x] = '#'
        '#' -> flines[y][x] = '.'
    }
    return Grid(flines)
}

fun List<String>.toGrids(): List<Grid<Char>> {
    val grids = mutableListOf<Grid<Char>>()

    var l = mutableListOf<String>()
    for (line in this) {
        when {
            line.isBlank() -> {
                grids.add(Grid(l.map { it.toList() }))
                l = mutableListOf<String>()
            }

            else -> l.add(line)
        }
    }
    grids.add(Grid(l.map { it.toList() }))
    return grids
}
// adapted from [https://www.geeksforgeeks.org/longest-palindromic-substring/]
fun palindromesIn(str: String): List<Pair<Int,Int>> {
    val n = str.length
    val table = Array(n) { BooleanArray(n) }
    var maxLength = 1
    val at = mutableListOf<Pair<Int,Int>>()

    for (i in 0..<n) table[i][i] = true
    var start = 0
    for (i in 0..<n - 1) {
        if (str[i] == str[i + 1]) {
            table[i][i + 1] = true
            start = i
            maxLength = 2
            at.add(Pair(start,maxLength))
        }
    }
    for (k in 3..n) {
        for (i in 0..<n - k + 1) {
            val j = i + k - 1
            if (table[i + 1][j - 1]
                && str[i] == str[j]
            ) {
                table[i][j] = true
                at.add(Pair(i,k))
            }
        }
    }
    return at
}

// (point, length)
fun reflectPoint(l: List<Char>): List<Pair<Int,Int>> {
    val lp = palindromesIn(String(l.toCharArray()))
    val l2= lp.mapNotNull {
        when {
            it.first!=0 && (it.first + it.second)!=l.size -> null
            it.second%2 != 0 -> null
            it.second > 0 -> Pair(it.first + it.second / 2,it.second)
            else -> null
        }
    }
    return l2
}

fun reflectVert(g: Grid<Char>, orig:Int): Int {
    val points = g.lines.map {
        reflectPoint(it).toSet()
    }
    val ps = points.reduce { acc, it -> acc.intersect(it) }.filter { it.first!=orig }
    return when {
        ps.size==0 -> 0
        else -> ps.maxBy { it.second }.first
    }
}

fun reflectHoriz(g: Grid<Char>, orig:Int): Int {
    val points = g.columns.map {
        reflectPoint(it).toSet()
    }
    val ps = points.reduce { acc, it -> acc.intersect(it) }.filter { it.first!=orig }
    return when {
        ps.size==0 -> 0
        else -> ps.maxBy { it.second }.first
    }
}

fun task1(lines: List<String>): Int {
    var total = 0
    val grids = lines.toGrids()

    for (g in grids.indices) {
        val grid=grids[g]
        println(g)
        println(grid)
        val rv = reflectVert(grid,-1)
        println("rv: $rv")
        total += if (0 == rv) {
            val rh = reflectHoriz(grid,-1)
            println("rh: $rh")
            if (0==rh) {
                0
            } else {
                (100 * (rh))
            }
        } else {
            rv
        }
    }

    return total
}