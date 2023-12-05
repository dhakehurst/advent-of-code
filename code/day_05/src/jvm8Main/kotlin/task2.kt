package day_05

fun task2(lines: List<String>) = task2_1(lines)

// just iterate for a long time ;-)
fun task2_1(lines: List<String>) {
    val seeds = lines[0].substringAfter(":").split(" ").mapNotNull { it.trim().toLongOrNull() }
    val seeds2 = mutableListOf<Range>()
    var i = 0
    while(i < seeds.size) {
        seeds2.add(Range(seeds[i], seeds[i+1]))
        i += 2
    }

    val mappers = mutableListOf<Mapper>()
    for (line in lines.drop(1)) {
        when {
            line.trim().isBlank() -> Unit
            line.contains(":") -> mappers.add(Mapper(line.substringBefore(":")))
            else -> {
                val nums = line.split(" ").mapNotNull { it.trim().toLongOrNull() }
                mappers.last().ranges.add(RangeMap(nums[0], nums[1], nums[2]))
            }
        }
    }

    println("Total SeedRanges: ${seeds2.size}")
    val locations = seeds2.mapIndexed { idx, sr ->
        println("seedRange: $idx - $sr")
        sr.minOf {
            mappers.fold(it) { acc, mpr ->
                mpr.map(acc)
            }
        }
    }
    println("Min: ${locations.min()}")
}

// try to work backwards.....does not work! - am missing something
fun task2_2(lines: List<String>) {
    val seeds = lines[0].substringAfter(":").split(" ").mapNotNull { it.trim().toLongOrNull() }
    val seeds2 = mutableListOf<Range>()
    var i = 0
    while(i < seeds.size) {
        seeds2.add(Range(seeds[i], seeds[i+1]))
        i += 2
    }

    val mappers = mutableListOf<Mapper>()
    for (line in lines.drop(1)) {
        when {
            line.trim().isBlank() -> Unit
            line.contains(":") -> mappers.add(Mapper(line.substringBefore(":")))
            else -> {
                val nums = line.split(" ").mapNotNull { it.trim().toLongOrNull() }
                mappers.last().ranges.add(RangeMap(nums[0], nums[1], nums[2]))
            }
        }
    }

    var md = mappers.last().srcForMinDstMax
    for(mpr in mappers.reversed().drop(1)) {
        mpr.filterToRangesWithDstLessThan(md)
        md = mpr.filteredSrcForMinDstMax
    }

    val minSeedsMaxToSearch = mappers.first().filteredSrcForMinDstMax

    println("Total SeedRanges: ${seeds2.size}")
    val locations = seeds2.mapIndexedNotNull { idx, sr ->
        println("seedRange: $idx - $sr")
        if (sr.max < minSeedsMaxToSearch) {
            println("mapping")
            sr.limitTo(minSeedsMaxToSearch)
            sr.minOf {
                mappers.fold(it) { acc, mpr ->
                    mpr.map(acc)
                }
            }
        } else {
            println("ignored")
            null
        }
    }
    println("Min: ${locations.min()}")
}

// use range intersections....in-work
fun task2_3(lines: List<String>) {
    val seeds = lines[0].substringAfter(":").split(" ").mapNotNull { it.trim().toLongOrNull() }
    val seeds2 = mutableListOf<Range>()
    var i = 0
    while(i < seeds.size) {
        seeds2.add(Range(seeds[i], seeds[i+1]))
        i += 2
    }

    val mappers = mutableListOf<Mapper>()
    for (line in lines.drop(1)) {
        when {
            line.trim().isBlank() -> Unit
            line.contains(":") -> mappers.add(Mapper(line.substringBefore(":")))
            else -> {
                val nums = line.split(" ").mapNotNull { it.trim().toLongOrNull() }
                mappers.last().ranges.add(RangeMap(nums[0], nums[1], nums[2]))
            }
        }
    }

    val locRanges = mappers.fold(seeds2 as List<Range>) { acc, mpr ->
        mpr.mapRanges(acc)
    }

    println("Min: ${locRanges.minOf { it.start }}")
}