package day_19


fun task1(lines: List<String>): ULong {

    val factoryLines = lines.takeWhile { it.isNotBlank() }
    val partLines = lines.drop(factoryLines.size+1)
    val factory = Factory(factoryLines)
    val parts = partLines.map { Part(it.trim('{','}')) }

    var total = 0UL
    for (part in parts) {
        val accepted = factory.process(part)
        total += accepted?.rating?.toULong() ?: 0UL
    }

    return total
}