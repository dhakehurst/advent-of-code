package day_16

fun task1(lines: List<String>): Int {

    val grid = GridAsString(lines.map { it.toMutableList() })

    return energise(grid, Beam(0,0,Dir.R))
}