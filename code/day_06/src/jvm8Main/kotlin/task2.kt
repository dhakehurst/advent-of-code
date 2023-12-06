package day_06

fun task2(lines: List<String>):Long {
    val raceTime = lines[0].substringAfter(":").replace(" ", "").toLong()
    val recordDist = lines[1].substringAfter(":").replace(" ", "").toLong()

    var totalCanWin = 0L
    var holdTime = 0L
    while (holdTime < raceTime) {
        val speed = holdTime
        val timeLeft = raceTime - holdTime
        val myDist = timeLeft * speed
        if (myDist > recordDist) {
            totalCanWin++
        }
        holdTime++
    }

    println("Day 06 task 2: $totalCanWin")
    return totalCanWin
}