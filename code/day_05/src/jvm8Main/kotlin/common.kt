package day_05

class Range(
    val start: Long,
    val length: Long
) : Iterable<Long> {
    val max = start + length

    var limit = length

    override fun iterator(): Iterator<Long> = object : AbstractIterator<Long>() {
        var i = 0
        override fun computeNext() {
            when {
                i >= length -> done()
                else -> {
                    setNext(start + i)
                    i++
                }
            }
        }

    }

    fun limitTo(maxSeedNum: Long) {
        limit = kotlin.math.min(length, maxSeedNum - start)
    }

    override fun toString(): String = "[$start-${start + length}]"
}

data class Mapper(
    val name: String
) {
    val ranges = mutableListOf<RangeMap>()
    lateinit var filtered: List<RangeMap>

    fun map(inp: Long): Long =
        ranges.firstNotNullOfOrNull { it.mapOrNull(inp) } ?: inp

    fun filteredMap(inp: Long): Long =
        filtered.firstNotNullOfOrNull { it.mapOrNull(inp) } ?: inp

    val srcForMinDstMax by lazy {
        ranges.sortedBy { it.dstMax }.first().src
    }

    val filteredSrcForMinDstMax by lazy {
        filtered.minBy { it.dstMax }.src
    }

    fun filterToRangesWithDstLessThan(dstMax: Long) {
        println("Mapper '$name' limited to dst $dstMax")
        filtered = ranges.filter {
            it.dstMax <= dstMax
        }
    }

    fun mapRanges(inp: List<LongRange>): List<LongRange> {
        val unmapped = inp.toMutableList()
        val mapped = mutableListOf<LongRange>()
        while (unmapped.isNotEmpty()) {
            val toMap = unmapped.removeFirst()
            val rng = ranges.firstOrNull { toMap.intersect(it.srcRange) != null }
            when (rng) {
                null -> mapped.add(toMap) //direct maping
                else -> {
                    val i = toMap.intersect(rng.srcRange)!!
                    mapped.add(rng.mapRange(i.covered))
                    i.before?.let { unmapped.add(it) }
                    i.after?.let { unmapped.add(it) }
                }
            }
        }
        return mapped
    }

}

data class LongRangeIntersection(
    val before: LongRange?,
    val covered: LongRange,
    val after: LongRange?
)

fun LongRange.isSubsetOf(other: LongRange) = this.first >= other.first && this.last <= other.last
fun LongRange.intersect(other: LongRange) = when {
    this.last > other.first && this.first < other.last -> LongRangeIntersection(
        before = when {
            this.first < other.first -> LongRange(this.start, other.start - 1)
            else -> null
        },
        covered = LongRange(kotlin.math.max(this.first, other.first), kotlin.math.min(this.last, other.last)),
        after = when {
            this.last > other.last -> LongRange(other.last + 1, this.last)
            else -> null
        }
    )

    else -> null
}

data class RangeMap(
    val dst: Long,
    val src: Long,
    val rng: Long
) {
    val srcRange = LongRange(src, src + rng - 1)
    val dstRange = LongRange(dst, dst + rng - 1)

    val srcMax get() = src + (rng - 1)
    val dstMax get() = dst + (rng - 1)

    fun mapOrNull(inp: Long): Long? =
        when {
            inp in src..srcMax -> dst + (inp - src)
            else -> null
        }

    fun mapRange(inp: LongRange): LongRange = when {
        inp.first >= src && inp.last <= srcMax -> LongRange(mapOrNull(inp.first)!!, mapOrNull(inp.last)!!)
        else -> error("Input out of range")
    }

    override fun toString(): String = "Range [$src-${src + rng}] ==> [$dst-${dst + rng}]"
}
