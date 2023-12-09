package day_09

fun task2(lines: List<String>) : Int {
    var total = 0

    for (line in lines) {
        val seq = NumSequenceTop(line.split(" ").map { it.trim().toInt() })
        total += seq.prev
    }

    return total
}