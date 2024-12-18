package day_04

import advent.of.code.lib.Grid

fun task2(lines: List<String>) {
    val M_S = Regex("M.S")
    val _A_ = Regex(".A.")
    val S_M = Regex("S.M")
    val S_S = Regex("S.S")
    val M_M = Regex("M.M")
    val grid = Grid<Char>(lines.map { it.toList() },{0UL})
    var total = 0
    val windows = grid.windowsOfSize(3,3)
    for (w in windows) {
        var found1 = true
        found1 = found1 && w.lines[0].joinToString(separator = "").matches(M_S)
        found1 = found1 && w.lines[1].joinToString(separator = "").matches(_A_)
        found1 = found1 && w.lines[2].joinToString(separator = "").matches(M_S)
        if (found1) {
            total+=1
        }

        var found2 = true
        found2 = found2 && w.lines[0].joinToString(separator = "").matches(S_M)
        found2 = found2 && w.lines[1].joinToString(separator = "").matches(_A_)
        found2 = found2 && w.lines[2].joinToString(separator = "").matches(S_M)
        if (found2) {
            total+=1
        }

        var found3 = true
        found3 = found3 && w.lines[0].joinToString(separator = "").matches(S_S)
        found3 = found3 && w.lines[1].joinToString(separator = "").matches(_A_)
        found3 = found3 && w.lines[2].joinToString(separator = "").matches(M_M)
        if (found3) {
            total+=1
        }

        var found4 = true
        found4 = found4 && w.lines[0].joinToString(separator = "").matches(M_M)
        found4 = found4 && w.lines[1].joinToString(separator = "").matches(_A_)
        found4 = found4 && w.lines[2].joinToString(separator = "").matches(S_S)
        if (found4) {
            total+=1
        }
    }


    println(total)
}