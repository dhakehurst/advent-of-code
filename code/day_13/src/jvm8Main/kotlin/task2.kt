package day_13

enum class D { V, H }

fun Grid<Char>.reflectPoint(orig:Pair<D,Int>):Pair<D,Int>? {
    val o = if (orig.first==D.V) orig.second else -1
    val rv = reflectVert(this,o)
    return when {
        0 != rv  -> Pair(D.V,rv)
        else -> {
            val o2 = if (orig.first==D.H) orig.second else -1
            val rh = reflectHoriz(this,o2)
            when {
                0 != rh  -> Pair(D.H, rh)
                else -> null
            }
        }
    }
}

fun tryFlipped(grid: Grid<Char>, orig: Pair<D,Int>): Pair<D,Int> {
    for (fi in 0 until grid.size.cols) {
        for (fj in 0 until grid.size.rows) {
            val fg = grid.flip(fi, fj)
            val rp = fg.reflectPoint(orig)
            if (null!=rp && rp!=orig) {
                return rp
            }
        }
    }
    error("")
}

fun task2(lines: List<String>): Long {
    var total = 0L
    val grids = lines.toGrids()

    for (g in grids.indices) {
        val grid = grids[g]
        val original = grid.reflectPoint(Pair(D.V,-1))!!
        println(g)
        val res = tryFlipped(grid, original)
        total += when(res.first) {
            D.V -> {
                println("rv: ${res.second}")
                res.second
            }
            D.H -> {
                println("rh: ${res.second}")
                (100 * res.second)
            }
        }
    }

    return total
}