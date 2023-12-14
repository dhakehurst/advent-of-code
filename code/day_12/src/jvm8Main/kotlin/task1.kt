package day_12

import korlibs.number.toStringUnsigned

fun toRegEx(ns:List<Int>):Regex {
    val hashes = ns.joinToString(separator = "([.]+)") { "([#]{$it})" }
    return Regex("([.]*)$hashes([.]*)")
}

fun createOptions(record:String) :List<String> {
    val i = record.indexOf('?')
    return when(i) {
        -1 -> listOf(record)
        else -> {
            createOptions(record.replaceFirst('?','.')) + createOptions(record.replaceFirst('?','#'))
        }
    }
}

fun task1(lines: List<String>): Long {
    var total = 0L

    for (line in lines) {
        val record = line.substringBefore(" ")
        val groupSizes = line.substringAfter(" ").split(",").map { it.trim().toInt() }
        val regEx = toRegEx(groupSizes)
        val options = createOptions(record)
        val alternativeCount = options.count { it.matches(regEx) }
        total+=alternativeCount
    }

    return total
}