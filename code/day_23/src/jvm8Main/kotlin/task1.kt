package day_23

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Coord(
    val str: String
) {
    val x: UInt
    val y: UInt
    val z: UInt

    init {
        val split = str.split(",")
        x = split[0].toUInt()
        y = split[1].toUInt()
        z = split[2].toUInt()
    }
}

data class Brick(
    val id: UInt,
    val str: String
) {
    companion object {
        var next = 0u
    }

    val end1: Coord
    val end2: Coord

    init {
        val split = str.split("~")
        end1 = Coord(split[0])
        end2 = Coord(split[1])
    }

    val supportedBy = mutableSetOf<Brick>()
    val supports = mutableSetOf<Brick>()

    val xMin: UInt get() = min(end1.x, end2.x)
    val yMin: UInt get() = min(end1.y, end2.y)
    val xMax: UInt get() = max(end1.x, end2.x)
    val yMax: UInt get() = max(end1.y, end2.y)
    val zMin: UInt get() = min(end1.z, end2.z)
    val zMax: UInt get() = max(end1.z, end2.z)

    val zSize get() = zMax - zMin + 1u

    val supportsTotal: Set<Brick> by lazy {
        (supports.flatMap { it.supportsTotal } + supports).toSet()
    }

    fun wouldDropSize(td: Set<Brick>): UInt {
        val wd = this.supports.filter {
            (it.supportedBy - td).size == 0
        }.toSet()
        val ntd = td+wd
        val t = wd.sumOf { it.wouldDropSize(ntd) } + wd.size.toUInt()
        return t
    }

    fun intersectsXY(other: Brick) = when {
        this.xMin > other.xMax -> false
        this.xMax < other.xMin -> false
        this.yMin > other.yMax -> false
        this.yMax < other.yMin -> false
        else -> true
    }

    fun supportedByTransitive(other: Brick): Boolean = when {
        this.zMin <= other.zMax -> false
        this.supportedBy.isEmpty() -> true
        //this.intersectsXY(other) -> true
        else -> this.supportedBy.any { it.supportedByTransitive(other) }
    }

    fun droppedTo(nz: UInt) = when {
        zMin == end1.z -> Brick(id, "${end1.x},${end1.y},$nz~${end2.x},${end2.y},${nz + (end2.z - end1.z)}")
        else -> Brick(id, "${end1.x},${end1.y},${nz + (end1.z - end2.z)}~${end2.x},${end2.y},$nz")
    }

    override fun toString(): String = "$id $str"
}

fun List<Brick>.print() {
    val xMax = this.maxOf { it.xMax }.toInt() + 1
    val yMax = this.maxOf { it.yMax }.toInt() + 1
    val grid = MutableList(yMax) { MutableList(xMax) { "." } }
    this.forEach {
        for (i in it.yMin..it.yMax) {
            for (j in it.xMin..it.xMax) {
                grid[i.toInt()][j.toInt()] = it.zSize.toString()
            }
        }
    }
    grid.forEach {
        println(it.joinToString(separator = ""))
    }
}

fun task1(lines: List<String>): ULong {

    val bricks = lines.map { Brick(Brick.next++, it) }

    val orderedGrp = bricks.groupBy { it.zMin }.toSortedMap()

    val dropped = mutableListOf<Brick>()
    for (grp in orderedGrp) {
        for (br in grp.value) {
            val blocksUnder = dropped.filter { it.zMax < br.zMin }
            val under = blocksUnder.sortedBy { it.zMax }.reversed().firstOrNull { it.intersectsXY(br) }
            val dt = if (null == under) {
                br.droppedTo(1u)
            } else {
                br.droppedTo(under.zMax + 1u)
            }
            dropped.add(dt)
        }
    }

    val byZMin = dropped.groupBy { it.zMin }


    for (i in dropped.size - 1 downTo 0) {
        val br = dropped[i]
        val above = dropped.filter { it.zMin == br.zMax + 1u }
        val aboveAndIntersect = above.filter { it.intersectsXY(br) }
        for (br2 in aboveAndIntersect) {
            br2.supportedBy.add(br)
            br.supports.add(br2)
        }
    }

    dropped.forEach { b ->
        b.supportedBy.forEach { s ->
            check(s.intersectsXY(b))
            check(s.zMax + 1u == b.zMin)
        }
        b.supports.forEach { s ->
            check(s.intersectsXY(b))
            check(s.zMin == b.zMax + 1u)
        }
    }

    val notSupporting = dropped.filter { br -> br.supports.isEmpty() || br.supports.all { it.supportedBy.size > 1 } }


    return notSupporting.size.toULong()
}