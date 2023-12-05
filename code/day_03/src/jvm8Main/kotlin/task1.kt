package day_03

import kotlin.math.max
import kotlin.math.min

fun task1(lines: List<String>) {

    var total = 0

    val numEx = Regex("[0-9]+")
    val symEx = Regex("[^.0-9]")
    for (i in lines.indices) {
        val line = lines[i]

        var j = 0
        while(j<line.length) {
            val m = numEx.matchAt(line, j)
            var num = 0
            var isPart = false
            if (null==m) {
                j++
            } else {
                num = m.value.toInt()

                val s = max(0,j-1)
                val e = min(line.length, j+m.value.length+1)

                if (i > 0) {
                    val lineabovesegment = lines[i-1].substring(s,e)
                    if( symEx.containsMatchIn(lineabovesegment) ) isPart=true
                }

                val beforeToAfter = lines[i].substring(s,e)

                if(symEx.containsMatchIn(beforeToAfter)) isPart=true
                if (i  < lines.size-1) {
                    val linebelowsegment = lines[i+1].substring(s,e)
                    if( symEx.containsMatchIn(linebelowsegment) ) isPart=true
                }

                j+=m.value.length
            }
            if (isPart) {
                total += num
            }
        }
    }
    println("Day 03 task 1: $total")
}