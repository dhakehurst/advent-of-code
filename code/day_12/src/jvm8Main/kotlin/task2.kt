package day_12

import java.util.*
import kotlin.math.pow

fun genStr(opt:List<Int>, groups: List<Int>) :String {
    var str = ".".repeat(opt[0])
    for (i in groups.indices) {
        str += "#".repeat(groups[i]) + ".".repeat(opt[i+1])
    }
    return str
}

fun distribute(balls: Int, boxes: Int, distribution: List<Int> = emptyList()): List<List<Int>> {
    return if (boxes == 1) {
        //println((distribution + balls).joinToString())
        listOf(distribution+balls)
    } else {
        val result = mutableListOf<List<Int>>()
        for (i in 0..balls) {
            result += distribute(balls - i, boxes - 1, distribution + i)
        }
        result
    }
}

fun countMatchDistribute(groups: List<Int>, pattern:Regex, balls: Int, boxes: Int, distribution: List<Int> = emptyList()):Long {
    return if (boxes == 1) {
        //println((distribution + balls).joinToString())
        val opt = distribution+balls
        when {
            opt.drop(1).dropLast(1).any { it==0 } -> 0L
            genStr(opt,groups).matches(pattern) -> 1L
            else -> 0L
        }
    } else {
        var count = 0L
        for (i in 0..balls) {
            count += countMatchDistribute(groups, pattern,balls - i, boxes - 1, distribution + i)
        }
        count
    }
}

fun countMatchesOptionsFromGroups(groups:List<Int>, record: String) : Long {
    val patternStr = record.replace('.','_').replace('?','.').replace("_","[.]")
    println("$patternStr")
    val pattern = Regex(patternStr)
    //val pattern = toRegEx(groups)
    val length = record.length
    val diff = length - groups.sum()
    val numGaps = groups.size+1
    //val options = distribute(diff, numGaps)
    val count = countMatchDistribute(groups, pattern, diff, numGaps)
    return count
}

fun task2(lines: List<String>): Long {
    var total = 0L

    for (line in lines) {
        val record = line.substringBefore(" ")
        val record2 = "$record?$record?$record?$record?$record"

        val groupSizes = line.substringAfter(" ").split(",").map { it.trim().toInt() }
        val groupSizes2 = groupSizes + groupSizes + groupSizes + groupSizes + groupSizes
        println(record2)
        //val regEx = toRegEx(groupSizes2)
        val alternativeCount = countMatchesOptionsFromGroups(groupSizes2, record2)
        println(alternativeCount)
        total += alternativeCount
    }

    return total
}