package task_03_1

import korlibs.io.file.std.resourcesVfs
import kotlin.math.max
import kotlin.math.min

data class GeatAtHasNumAt(
    val gi: Int,
    val gj: Int,
    val num: Int,
    val ni:Int,
    val nj:Int
)

suspend fun main() {
    val lines = resourcesVfs["input.txt"].readLines().toList()


    var total = 0

    val gearNums = mutableListOf<GeatAtHasNumAt>()

    val numEx = Regex("[0-9]+")
    val symEx = Regex("[^.0-9]")
    val gearEx = Regex("[*]")
    for (i in lines.indices) {
        val line = lines[i]

        var j = 0
        while (j < line.length) {
            val m = numEx.matchAt(line, j)
            var num = 0
            if (null == m) {
                j++
            } else {
                num = m.value.toInt()

                val s = max(0, j - 1)
                val e = min(line.length, j + m.value.length + 1)

                if (i > 0) {
                    val gi = i-1
                    val lineabovesegment = lines[gi].substring(s, e)
                    for(it in gearEx.findAll(lineabovesegment)) {
                        val gj = s+it.range.start
                        gearNums.add(GeatAtHasNumAt(gi,gj,num,i,j))
                    }
                }

                val beforeToAfter = lines[i].substring(s, e)
                for(it in gearEx.findAll(beforeToAfter)) {
                    val gj = s+it.range.start
                    gearNums.add(GeatAtHasNumAt(i,gj,num,i,j))
                }

                if (i < lines.size - 1) {
                    val linebelowsegment = lines[i + 1].substring(s, e)
                    val gi = i+1
                    for(it in gearEx.findAll(linebelowsegment)) {
                        val gj = s+it.range.start
                        gearNums.add(GeatAtHasNumAt(gi,gj,num,i,j))
                    }
                }

                j += m.value.length
            }
        }
    }
    val groups =gearNums.groupBy { Pair(it.gi,it.gj) }
    groups.forEach {
        when(it.value.size) {
            0 -> error("0 should not occur")
            1 -> Unit //ignore
            2 -> {
                val ratio = it.value[0].num * it.value[1].num
                total+=ratio
            }
            else -> error(">2 Not handled")
        }
    }
    println(total)
}