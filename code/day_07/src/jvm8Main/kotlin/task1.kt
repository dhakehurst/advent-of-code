package day_07

val map1 = mapOf(
    "A" to 14,
    "K" to 13,
    "Q" to 12,
    "J" to 11,
    "T" to 10
)

data class Hand1(
    val cards: String,
    val bid: Int
) : Comparable<Hand1> {

    val cardValues = cards.map { map1[it.toString()] ?: it.toString().toInt() }

    val grouped = cardValues.groupBy { it }
    val isFiveOfAKind = grouped.size==1
    val isFourOfAKind = grouped.size==2 && grouped.maxOf { it.value.size }==4
    val isFullHouse = grouped.size==2 && grouped.maxOf { it.value.size }==3 && grouped.minOf { it.value.size }==2
    val isThreeOdAKind = grouped.size==3 && grouped.maxOf { it.value.size }==3
    val isTwoPair = grouped.size==3  && grouped.maxOf { it.value.size }==2
    val isOnePair = grouped.size==4  && grouped.maxOf { it.value.size }==2

    val handValue = when {
        isFiveOfAKind -> 6
        isFourOfAKind -> 5
        isFullHouse -> 4
        isThreeOdAKind -> 3
        isTwoPair -> 2
        isOnePair -> 1
        else -> 0
    }

    override fun compareTo(other: Hand1): Int = when {
        this.handValue > other.handValue -> 1
        this.handValue < other.handValue -> -1
        this.handValue == other.handValue -> {
            var r = 0
            for(i in 0 .. 4) {
                r = when {
                    cardValues[i] > other.cardValues[i] -> +1
                    cardValues[i] < other.cardValues[i] -> -1
                    else -> 0
                }
                if (0!=r) break
            }
            r
        }
        else -> 0
    }
}

fun task1(lines: List<String>): Long {
    var total = 0L

    val hands = lines.map {
        val cards = it.substringBefore(" ")
        val bid = it.substringAfter(" ").toInt()
        Hand1(cards, bid)
    }

    val sorted = hands.sortedBy { it }

    sorted.forEachIndexed { idx, it ->
        println("${it.cards} ${it.bid}  ${idx+1}  ${it.bid * (idx+1)}")
        total += it.bid * (idx+1)
    }

    println("Day 07 task 1: $total")
    return total
}