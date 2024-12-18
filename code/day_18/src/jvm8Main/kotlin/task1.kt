package day_18

import advent.of.code.lib.Dir
import advent.of.code.lib.Grid
import advent.of.code.lib.PathEndPoint


fun task1(lines: List<String>): ULong {
    val size = 71
    val after = 1024
    val taken = lines.take(after)
    val maze = Grid(size,size,'.', { v -> if (v=='.') 1UL else null })
    for (line in taken) {
        val splt = line.split(",")
        val x = splt[0].toInt()
        val y = splt[1].toInt()
        maze[x,y] = 'X'
    }

    val s1 = PathEndPoint<Char>(0, 0, 0u, Dir.R, 2, 0UL)
    val s2 = PathEndPoint<Char>(0, 0, 0u, Dir.D, 2, 0UL)
    val r = maze.shortestPath(listOf(s1,s2), Pair(size-1,size-1), '.', 0, size)

    return r!!.cost
}