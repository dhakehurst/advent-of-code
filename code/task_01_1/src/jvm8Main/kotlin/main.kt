package task_01_1

import korlibs.io.file.std.resourcesVfs

suspend fun main() {
    val lines = resourcesVfs["input.txt"].readLines().toList()
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
    println(total)
}