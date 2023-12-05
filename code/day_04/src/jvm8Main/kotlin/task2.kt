package day_04

fun task2(lines: List<String>) {
    var total = 0
    val cardValue = mutableListOf<Int>()
    val cardTotals = mutableMapOf<Int,Int>()

    for (i in lines.indices) {
        val line = lines[i]
        val cardNum = i+1
        cardTotals[cardNum] = (cardTotals[cardNum] ?: 0 ) + 1

        val goals = line.substringAfter(":").substringBefore("|").split(" ").mapNotNull{ it.trim().toIntOrNull() }
        val nums = line.substringAfter("|").split(" ").mapNotNull{ it.trim().toIntOrNull() }

        val wins = nums.filter { goals.contains(it) }
        val value = wins.size
        cardValue.add(value)
        for(j in 1 .. value) {
            val x = cardTotals[cardNum+j] ?: 0
            cardTotals[cardNum+j] = x+cardTotals[cardNum]!!
        }
    }

    total = cardTotals.values.sum()

    println(total)
}