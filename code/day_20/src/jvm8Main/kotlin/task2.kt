package day_19

class GenericPart(
    val nextWf:String,
    val x: UIntRange,
    val m: UIntRange,
    val a: UIntRange,
    val s: UIntRange
) {
    val ratingCount:ULong by lazy {
        x.size * m.size * a.size * s.size
    }

    val UIntRange.size:ULong get() = (last-first+1u).toULong()

    override fun toString(): String = "${nextWf} ${x.first}-x-${x.last} ${m.first}-m-${m.last} ${a.first}-a-${a.last} ${s.first}-s-${s.last}"
}

fun task2(lines: List<String>): ULong {

    val factoryLines = lines.takeWhile { it.isNotBlank() }
    val partLines = lines.drop(factoryLines.size + 1)
    val factory = Factory(factoryLines)

    val r = factory.countAccepted(
        GenericPart(
            nextWf = "in",
            x = UIntRange(1u, 4000u),
            m = UIntRange(1u, 4000u),
            a = UIntRange(1u, 4000u),
            s = UIntRange(1u, 4000u)
        )
    )

    return r
}