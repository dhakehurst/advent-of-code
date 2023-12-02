package task_02_2

import korlibs.io.file.std.resourcesVfs

suspend fun main() {
    val max = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
    val lines = resourcesVfs["input.txt"].readLines().toList()
    var total = 0

    for (line in lines) {
        val game = line.substringBefore(":")
        val gameNumber = game.substring(5).toInt()
        val sets = line.substringAfter(":").split(";")
        val mins = mutableMapOf(
            "red" to 0,
            "green" to 0,
            "blue" to 0
        )
        for (set in sets) {
            val groups = set.split(",")
            for (grp in groups) {
                val num = grp.trim().substringBefore(" ").toInt()
                val col = grp.trim().substringAfter(" ")
                if (mins[col]!! < num) {
                    mins[col] = num
                }
            }
        }
        val power = mins["red"]!! * mins["green"]!! * mins["blue"]!!
        total += power
    }
    println(total)
}