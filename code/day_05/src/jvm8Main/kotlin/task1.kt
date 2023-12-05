package day_05

fun task1(lines: List<String>) {

    val seeds = lines[0].substringAfter(":").split(" ").mapNotNull { it.trim().toLongOrNull() }
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

    val locations = seeds.map {
        mappers.fold(it) { acc, mpr ->
            mpr.map(acc)
        }
    }
    println("Min: ${locations.min()}")
}