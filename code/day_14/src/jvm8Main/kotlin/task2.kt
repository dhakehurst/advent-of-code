package day_14

val List<Char>.join get() = this.joinToString(separator = "")

fun String.tilt(): String {
    val segments = this.split("#")
    return segments.map {
        val os = it.count { it == 'O' }
        "O".repeat(os) + ".".repeat(it.length - os)
    }.joinToString(separator = "#")
}

fun GridAsString.tiltAndRotate(): GridAsString {
    val newLines = this.columns.map {
        it.tilt().reversed()
    }
    return GridAsString(newLines)
}


fun GridAsString.cycle(): GridAsString {
    var g = this
    for (i in 0 until 4) {
        g = g.tiltAndRotate()
    }
    return g
}


fun String.sumWeight():Long {
    var sum = 0L
    val top = this.length
    for (i in 0 until this.length) {
        val c = this[i]
        when(c) {
            '.' -> {}
            '#' -> {}
            'O' -> {
                sum += top-i
            }
        }
    }
    return sum
}

fun GridAsString.sumWeight():Long =
    this.columns.fold(0) { a,i -> a + i.sumWeight() }


fun task2(lines: List<String>): Long {
    var total = 0L
    val grid = GridAsString(lines)
    val os = lines.fold(0) { a, l -> a+l.count { it=='O' }  }
    val hs = lines.fold(0) { a, l -> a+l.count { it=='#' }  }

    var g = grid
    println(g.sumWeight())
    for (i in 0 until 10000){ //0000000) {
        g = g.cycle()
    }
    println(g.sumWeight())
    return g.sumWeight()
}