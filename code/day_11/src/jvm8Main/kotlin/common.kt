package day_11

data class Point(
    val x: Int,
    val y: Int
)

data class Path(
    val galaxyStart: Point,
    val galaxyFinish: Point
) {
    var distance: Long = -1
}


class SpaceMap(
    val galaxies: List<Point>,
    val colSizes: List<Int>,
    val rowSizes: List<Int>,
) {

    val paths: List<Path>
        get() {
            val paths = mutableListOf<Path>()
            for (i1 in 0 until galaxies.size) {
                val g1 = galaxies[i1]
                for (i2 in 0 until i1) {
                    val g2 = galaxies[i2]
                    if (g1 != g2) {
                        val p = Path(g1, g2)
                        p.distance = distance(g1, g2)
                        //println("${i1+1} -> ${i2+1} : ${p.distance}")
                        paths.add(p)
                    }
                }
            }
            return paths
        }

    private fun distance(p1: Point, p2: Point): Long {
        return colDist(p1.x, p2.x) + rowDist(p1.y, p2.y)
    }

    private fun rowDist(y1: Int, y2: Int): Long {
        return when {
            y1 == y2 -> 0
            y1 > y2 -> rowSizes[y2] + rowDist(y1, y2 + 1)
            y1 < y2 -> rowSizes[y1] + rowDist(y1 + 1, y2)
            else -> error("")
        }
    }

    private fun colDist(x1: Int, x2: Int): Long {
        return when {
            x1 == x2 -> 0
            x1 > x2 -> colSizes[x2] + colDist(x1, x2 + 1)
            x1 < x2 -> colSizes[x1] + colDist(x1 + 1, x2)
            else -> error("")
        }
    }
}