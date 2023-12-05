package day_04

fun task1(lines: List<String>) {
    var total = 0
    for (i in lines.indices) {
        val line = lines[i]

        val goals = line.substringAfter(":").substringBefore("|").split(" ").mapNotNull{ it.trim().toIntOrNull() }
        val nums = line.substringAfter("|").split(" ").mapNotNull{ it.trim().toIntOrNull() }

        val wins = nums.filter { goals.contains(it) }
        val value = when {
            wins.isNotEmpty() -> wins.drop(1).fold(1) { acc, it -> acc * 2 }
            else -> 0
        }
        total+=value
    }
    println(total)
}