package day_01

fun task2(lines: List<String>) {
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    lines.forEach {
        list1.add(it.substringBeforeLast(" ").trim().toInt())
        list2.add(it.substringAfterLast(" ").trim().toInt())
    }
    var total = 0
    for (n1 in list1) {
        val c = list2.count { it == n1 }
        total += c*n1
    }

    println("Day 01 task 2: $total")
}