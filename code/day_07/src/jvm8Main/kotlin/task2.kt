package day_07

val map2 = mapOf(
    "A" to 14,
    "K" to 13,
    "Q" to 12,
    "J" to 1,
    "T" to 10
)

data class Hand2(
    val cards: String,
    val bid: Int
) : Comparable<Hand2> {

    val cardValues = cards.map { map2[it.toString()] ?: it.toString().toInt() }

    val grouped = cardValues.filterNot { map2["J"]==it }.groupBy { it }

    val numJokers = cardValues.count { it == map2["J"] }
    val maxGroup = grouped.maxOfOrNull { it.value.size } ?: 0
    val num2Groups = grouped.count { it.value.size == 2 }

    val isFiveOfAKind = maxGroup==5 || numJokers==5 || (maxGroup+numJokers)==5
    val isFourOfAKind = (maxGroup+numJokers)==4
    val isFullHouse = (maxGroup==3 && num2Groups==1) || (num2Groups==2 && numJokers==1) || (maxGroup==3 && numJokers==2)
    val isThreeOdAKind = (maxGroup+numJokers)==3
    val isTwoPair = num2Groups==2 || (num2Groups==1 && numJokers==1)
    val isOnePair = num2Groups==1 || numJokers==1

    val handValue = when {
        isFiveOfAKind -> 6
        isFourOfAKind -> 5
        isFullHouse -> 4
        isThreeOdAKind -> 3
        isTwoPair -> 2
        isOnePair -> 1
        else -> 0
    }

    override fun compareTo(other: Hand2): Int = when {
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

fun task2(lines: List<String>): Long {
    var total = 0L

    val hands = lines.map {
        val cards = it.substringBefore(" ")
        val bid = it.substringAfter(" ").toInt()
        Hand2(cards, bid)
    }

    val sorted = hands.sortedBy { it }

    sorted.forEachIndexed { idx, it ->
        println("${it.handValue}  ${it.cards} ${it.bid}  ${idx+1}  ${it.bid * (idx+1)}")
        total += it.bid * (idx+1)
    }

    println("Day 07 task 1: $total")
    return total
}