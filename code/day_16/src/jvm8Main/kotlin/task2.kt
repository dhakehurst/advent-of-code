package day_16


fun task2(lines: List<String>): Int {
    var total = 0L

    val grid = GridAsString(lines.map { it.toMutableList() })

    val maxRow = grid.size.rows-1
    val maxCol = grid.size.cols-1

    val configs = (0 until grid.size.cols).flatMap {col ->
        listOf(Beam(col,0,Dir.D),
        Beam(col,maxRow,Dir.U))
    } + (0 until grid.size.rows).flatMap { row->
        listOf(Beam(0,row,Dir.R),
        Beam(maxCol,row,Dir.L))
    }

    var maxEnergy = 0
    for(start in configs) {
        val e = energise(grid,start)
        if (e > maxEnergy) maxEnergy = e
    }

    return maxEnergy
}