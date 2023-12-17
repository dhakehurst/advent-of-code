package day_17


fun task2(lines: List<String>): ULong {

    val grid = GridAsString(lines.map { it.toMutableList() })

    val s1 = PathEndPoint(0, 0, 0u, Dir.R, 0, 0UL)
    val s2 = PathEndPoint(0, 0, 0u, Dir.D, 0, 0UL)
    val v1 = computePathLoss(grid, Pair(grid.size.cols - 1, grid.size.rows - 1), listOf(s2, s1), 3, 9)

    return v1
}