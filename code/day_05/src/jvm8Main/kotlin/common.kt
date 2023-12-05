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

    fun mapRanges(inp: List<Range>): List<Range> {
        return inp.flatMap { inpRng ->
            ranges.map {
                it.mapRange(inpRng)
            }
        }
    }

}

data class RangeMap(
    val dst: Long,
    val src: Long,
    val rng: Long
) {
    val srcRange = Range(src, rng)
    val dstRange = Range(dst, rng)

    val srcMax get() = src + (rng-1)
    val dstMax get() = dst + (rng-1)

    fun mapOrNull(inp: Long): Long? =
        when {
            inp in src..srcMax -> dst + (inp - src)
            else -> null
        }

    fun mapRange(inp: Range): Range = when {
        inp.start >= src && inp.max <= srcMax -> Range(mapOrNull(inp.start)!!, inp.length)
        else -> error("Input out of range")
    }


    override fun toString(): String = "Range [$src-${src + rng}] ==> [$dst-${dst + rng}]"
}
