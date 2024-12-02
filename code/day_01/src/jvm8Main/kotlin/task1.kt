package day_01

import kotlin.math.abs

fun task1(lines: List<String>) {
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    lines.forEach {
        list1.add(it.substringBeforeLast(" ").trim().toInt())
        list2.add(it.substringAfterLast(" ").trim().toInt())
    }
    list1.sort()
    list2.sort()
    var total = 0;
    for(i in list1.indices) {
        total += abs(list1[i] - list2[i])
    }
    println("Day 01 task 1: $total")
}