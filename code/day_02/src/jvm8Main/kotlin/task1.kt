package day_02

fun task1(lines: List<String>) {
    val max = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
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
    println("Day 02 task 1: $total")
}