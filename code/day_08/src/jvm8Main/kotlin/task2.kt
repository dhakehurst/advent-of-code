package day_08

fun lcm(v1:Long, v2:Long): Long {
    var gcd = 1L
    var i = 1L
    while (i <= v1 && i <= v2) {
        if (v1 % i == 0L && v2 % i == 0L) {
            gcd = i
        }
        i++
    }

    return v1 * v2 / gcd
}

fun task2(lines: List<String>): Long {
    val lrs = lines[0].map { it.toString() }

    val map = DesertMap()
    for (i in 2 until lines.size) {
        map.addEntry(lines[i])
    }

    val locs = map.starts
    val counts = locs.map {
        var loc = it
        var count = 0
        var stepNum = 0
        while (loc.isEnd.not()) {
            val dir = lrs[stepNum]
            loc = map.next(loc,dir)
            count++
            stepNum++
            if(stepNum == lrs.size) stepNum=0
        }
        count
    }
    val cs = counts.map { (it/lrs.size).toLong() }
    val total = cs.reduce{a,i -> lcm(a,i) } * lrs.size
    return total
}