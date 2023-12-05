package day_01

fun task1(lines: List<String>) {
    var total = 0
    for(line in lines) {
        var first = 0
        var last = 0
        line.forEach {
            when{
                it in '0'..'9' -> {
                    if (0==first) first = it.digitToInt()
                    last = it.digitToInt()
                }
            }
        }
       total += (first*10)+last
    }
    println("Day 01 task 1: $total")
}