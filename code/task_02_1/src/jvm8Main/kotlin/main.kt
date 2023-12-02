package task_02_1

import korlibs.io.file.std.resourcesVfs

suspend fun main() {
    val max = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
    val lines = resourcesVfs["input.txt"].readLines().toList()
    var total = 0

    for(line in lines) {
        val game = line.substringBefore(":")
        val gameNumber = game.substring(5).toInt()
        val sets = line.substringAfter(":").split(";")
        var setsOK = true
        for (set in sets) {
            val groups = set.split(",")
            var grpsOK = true
            for(grp in groups) {
                val num = grp.trim().substringBefore(" ").toInt()
                val col = grp.trim().substringAfter(" ")
                if (max[col]!! < num) {
                    grpsOK = false
                }
            }
            if (grpsOK.not()) {
                setsOK = false
            }
        }
        if (setsOK) {
            total += gameNumber
        }
    }
    println(total)
}