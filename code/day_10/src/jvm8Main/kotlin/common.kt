package day_10

import kotlin.math.abs

data class Pipe(
    val x: Int,
    val y: Int,
    val ch: Char
) {

    fun next(prev: Pipe, map: PipeMap): Pair<Pipe, Pipe?> {
        val n = when (ch) {
            '|' -> listOf(map.getOrNull(x, y - 1), map.getOrNull(x, y + 1)).firstOrNull { it != prev }
            '-' -> listOf(map.getOrNull(x - 1, y), map.getOrNull(x + 1, y)).firstOrNull { it != prev }
            'L' -> listOf(map.getOrNull(x, y - 1), map.getOrNull(x + 1, y)).firstOrNull { it != prev }
            'J' -> listOf(map.getOrNull(x, y - 1), map.getOrNull(x - 1, y)).firstOrNull { it != prev }
            '7' -> listOf(map.getOrNull(x - 1, y), map.getOrNull(x, y + 1)).firstOrNull { it != prev }
            'F' -> listOf(map.getOrNull(x + 1, y), map.getOrNull(x, y + 1)).firstOrNull { it != prev }
            '.' -> null //error("not in pipe")
            'S' -> error("Start!")
            else -> error("invalid")
        }
        return Pair(this, n)
    }

}

class PipeMap(
    val pipes: List<List<Pipe>>,
    val start: Pipe
) {

    val path = mutableListOf<Pipe>()

    val area :Int get() {
        var a = 0
        val verts = path.filterNot { it.ch=='-' || it.ch=='|' }
        val hp = verts.size / 2
        for(i in verts.indices) {
            val p1 = verts[i]
            val p2 = verts.getOrNull(i+1) ?: verts.first()
            val v = (p1.x)*(p2.y) - (p2.x)*(p1.y)
            a += v
        }
        val halfPath = path.size/2
        return abs(a/2) - (halfPath-1)
    }

    fun connections(p: Pipe) = listOf(
        Pair(p, this.getOrNull(p.x, p.y - 1)),
        Pair(p, this.getOrNull(p.x + 1, p.y)),
        Pair(p, this.getOrNull(p.x, p.y + 1)),
        Pair(p, this.getOrNull(p.x - 1, p.y)),
    )

    fun countLoop(from: Pair<Pipe, Pipe?>): Int {
        path.clear()
        path.add(from.first)
        var pos = from
        var count = 1
        while (pos.second != start) {
            val np = pos.second!!.next(pos.first, this)
            if (null == np.second) {
                count = 0
                break
            } else {
                count++
                path.add(np.first)
                pos = np
            }
        }
        return count
    }

    fun getOrNull(x: Int, y: Int) = pipes.getOrNull(y)?.getOrNull(x)
}
