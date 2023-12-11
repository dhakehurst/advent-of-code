package day_11

fun task1(lines: List<String>): Long {
    var total = 0L
    val rowSizes = MutableList<Int>(lines.size) { 2 }
    val colSizes = MutableList<Int>(lines[0].length) { 2 }
    val galaxies = mutableListOf<Point>()
    for (y in lines.indices) {
        for (x in lines[y].indices) {
            when (lines[y][x]) {
                '#' -> {
                    galaxies.add(Point(x, y))
                    rowSizes[y] = 1
                    colSizes[x] = 1
                }
            }
        }
    }
    val space = SpaceMap(galaxies, colSizes, rowSizes)
    println("Num Galaxies: ${space.galaxies.size}")
    println("Num Pairs: ${space.paths.size}")

    total = space.paths.fold(0L) { acc, it -> acc+it.distance}

    return total
}