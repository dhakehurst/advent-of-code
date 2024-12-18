package day_04

import advent.of.code.lib.Grid

fun task1(lines: List<String>) {
    val XMAS = Regex("XMAS")
    val SAMX = Regex("SAMX")
    val grid = Grid<Char>(lines.map { it.toList() },{0UL})
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
        c3 += XMAS.findAll(diag.joinToString(separator = "")).count()
        c3 += SAMX.findAll(diag.joinToString(separator = "")).count()

    }
    println("diag up found $c3")

    var c4 = 0
    println("diag down ${grid.diagonalsDown.size}")
    for (diag in grid.diagonalsDown) {
        c4 += XMAS.findAll(diag.joinToString(separator = "")).count()
        c4 += SAMX.findAll(diag.joinToString(separator = "")).count()

    }
    println("diag down found $c4")

    total = c1 + c2 + c3 + c4
    println(total)
}