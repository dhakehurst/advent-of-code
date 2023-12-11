package day_10

fun task1(lines: List<String>): Int {
    var total = 0
    var start: Pipe? = null
    val pipes = lines.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            val p = Pipe(x, y, c)
            if ('S' == p.ch) start = p
            p
        }
    }

    val map = PipeMap(pipes, start!!)

    val conns = map.connections(map.start)
    for (con in conns) {
        val count = map.countLoop(con)
        if (count > 0) {
            total = count
            break
        }
    }


    return total / 2
}