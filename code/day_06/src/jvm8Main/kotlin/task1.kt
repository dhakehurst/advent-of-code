package day_06

fun task1(lines: List<String>):Long {
    var total = 1L

    val times = lines[0].substringAfter(":").split(" ").mapNotNull { it.trim().toIntOrNull() }
    val distances = lines[1].substringAfter(":").split(" ").mapNotNull { it.trim().toIntOrNull() }

    for(i in times.indices) {
        val raceTime = times[i]
        val recordDist = distances[i]
        var totalCanWin = 0
        var holdTime = 0
        while (holdTime < raceTime) {
            val speed = holdTime
            val timeLeft = raceTime - holdTime
            val myDist = timeLeft * speed
            if (myDist > recordDist) {
                totalCanWin++
            }
            holdTime++
        }
        total *= totalCanWin
    }


    println("Day 06 task 1: $total")
    return total
}