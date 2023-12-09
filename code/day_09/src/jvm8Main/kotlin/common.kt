package day_09

interface NumSequence {
    val child: NumSequence

    val next: Int
    val prev: Int
    val lastDiff: Int
    val firstDiff: Int
    val firstDiff2: Int

    fun last(i: Int): Int
    fun first(i: Int): Int
}

abstract class NumSequenceAbstract() : NumSequence {

    override val child by lazy { NumSequenceChild(this) }

    override val lastDiff: Int get() = last(1) - last(2)

    override val firstDiff: Int get() = first(2) - first(1)
    override val firstDiff2: Int get() = first(3) - first(2)
}

class NumSequenceTop(
    val list: List<Int>
) : NumSequenceAbstract() {

    override val next: Int get() = last(1) + child.next

    override val prev: Int get() = first(1) - child.prev

    override fun last(i: Int): Int = list[list.size - i]

    override fun first(i: Int): Int = list[0 + (i - 1)]

}

class NumSequenceChild(
    private val parent: NumSequence
) : NumSequenceAbstract() {

    override val next: Int
        get() = when (parent.lastDiff) {
            0 -> 0
            else -> last(1) + child.next
        }

    override val prev: Int
        get() = when {
            0==parent.firstDiff && 0==parent.firstDiff2 -> 0
            else -> first(1) - child.prev
        }

    override fun last(i: Int): Int = parent.last(1 + (i - 1)) - parent.last(2 + (i - 1))

    override fun first(i: Int): Int = parent.first(2 + (i - 1)) - parent.first(1 + (i - 1))
}
