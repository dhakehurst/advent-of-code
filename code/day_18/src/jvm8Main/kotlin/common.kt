package day_18

import kotlin.math.abs

enum class Dir { U, R, D, L }

data class Instruction(
    val dir:Dir,
    val dist:Long,
    val col:String
)


data class Vertix(
    val x:Long,
    val y:Long
)

fun area(vertices:List<Vertix>) :Long  {
    var a = 0L
    for(i in vertices.indices) {
        val p1 = vertices[i]
        val p2 = vertices.getOrNull(i+1) ?: vertices.first()
        val v = (p1.x)*(p2.y) - (p2.x)*(p1.y)
        a += v
    }
    val halfPath = vertices.size / 2
    return abs(a/2) //- (halfPath-1)
}