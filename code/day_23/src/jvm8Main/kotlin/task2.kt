package day_23


fun wouldDrop(dropped:Set<Brick>,td:Set<Brick>, cache:MutableMap<Brick,Set<Brick>>) : Set<Brick> {
    val newDropped = dropped + td
    val ntd = td.flatMap { b ->
        b.supports.filter { (it.supportedBy-newDropped).isEmpty() }
    }.toSet()
    val s = ntd.flatMap {
        cache[it] ?: run {
           val r= wouldDrop(newDropped, ntd, cache)
            cache[it] = r
            r
        }

    }.toSet() + ntd
    return s
}


fun task2(lines: List<String>): ULong {

    val bricks = lines.map { Brick(Brick.next++, it) }
    val orderedGrp = bricks.groupBy { it.zMin }.toSortedMap()
    val dropped = mutableListOf<Brick>()
    for(grp in orderedGrp) {
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

    for (i in dropped.size - 1 downTo 0) {
        val br = dropped[i]
        val above = dropped.filter { it.zMin == br.zMax+1u }
        val aboveAndIntersect = above.filter { it.intersectsXY(br) }
        for (br2 in aboveAndIntersect) {
            br2.supportedBy.add(br)
            br.supports.add(br2)
        }
    }

    var total = 0UL
    dropped.forEach {
        val wouldDrop = wouldDrop(emptySet(), setOf(it), mutableMapOf())
        total += wouldDrop.size.toUInt()
    }

    return total
}