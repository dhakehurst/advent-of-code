package task_01_2

import korlibs.io.file.std.resourcesVfs

suspend fun main() {
    val map = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )
    val forward = Regex("[1-9]|one|two|three|four|five|six|seven|eight|nine")
    val reverse = Regex("[1-9]|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin")
    val lines = resourcesVfs["input.txt"].readLines().toList()
    var total = 0
    for(line in lines) {
        var first = 0
        for (i in line.indices) {
            val m = forward.matchAt(line, i)
            when(m) {
                null -> continue
                else -> {
                    first = map[m.value] ?: error("Internal error: '${m.value}' not found")
                    break
                }
            }
        }
        val reversedLine = line.reversed()
        var last = 0
        for (i in reversedLine.indices) {
            val m = reverse.matchAt(reversedLine, i)
            when(m) {
                null -> continue
                else -> {
                    last = map[m.value.reversed()] ?: error("Internal error: '${m.value}' not found")
                    break
                }
            }
        }
        println("$first$last - $line")
       total += (first*10)+last
    }
    println(total)
}