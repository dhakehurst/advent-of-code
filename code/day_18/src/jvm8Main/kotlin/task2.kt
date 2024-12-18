package day_18

import advent.of.code.lib.Dir
import advent.of.code.lib.Grid
import advent.of.code.lib.PathEndPoint

fun pathFor(size: Int, lines: List<String>) : PathEndPoint<Char>? {
    val maze = Grid(size, size, '.', { v -> if (v == '.') 1UL else null })
    for (line in lines) {
        val splt = line.split(",")
        val x = splt[0].toInt()
        val y = splt[1].toInt()
        maze[x, y] = 'X'
    }

    val s1 = PathEndPoint<Char>(0, 0, 0u, Dir.R, 2, 0UL)
    val s2 = PathEndPoint<Char>(0, 0, 0u, Dir.D, 2, 0UL)
    val r = maze.shortestPath(listOf(s1, s2), Pair(size - 1, size - 1), '.', 0, size)
    return r
}

fun task2(lines: List<String>): String {
    val size = 71
    val after = 2870
    var lastA:Int? = null
    for( a in after until lines.size) {
        println("Doing: $a")
        val path = pathFor(size, lines.take(a))
        if (path == null) {
            break
        } else {
            lastA = a
        }
    }

    return lines[lastA!!]
}