package day_12

import korlibs.io.lang.substr
import kotlin.math.min

class BitString(
    val value: ULong
) : Iterable<Boolean> {

    val groups: List<Int> by lazy {
        val g = mutableListOf<Int>()
        var grp = 0
        for (b in this.reversed()) {
            if (b) {
                grp++
            } else {
                if (0 != grp) g.add(grp)
                grp = 0
            }
        }
        if (0 != grp) g.add(grp)
        g
    }

    fun setBit(n: Int): BitString = BitString(value or 2UL.pow(n))
    fun get(n: Int): Boolean = (value and 2UL.pow(n)) != 0UL

    fun matchesOr(other: BitString) = (this.value or other.value) == this.value
    fun matchesInvOr(other: BitString) = (this.value.inv() or other.value) == this.value.inv()

    override fun iterator(): Iterator<Boolean> = object : AbstractIterator<Boolean>() {
        var v = value
        override fun computeNext() {
            when {
                0UL == v -> done()
                else -> {
                    setNext((v and 1UL) == 1UL)
                    v = v.shr(1)
                }
            }
        }
    }

    override fun hashCode(): Int = value.hashCode()
    override fun equals(other: Any?): Boolean = when {
        other !is BitString -> false
        this.value != other.value -> false
        else -> true
    }

    override fun toString(): String = value.toString(2)
}

val List<Int>.toBitString
    get() = BitString(this.foldIndexed(0UL) { x, a, i ->
        when (i) {
            0 -> a.shl(1)
            else -> a.shl(1).inc()
        }
    })

fun genStr(opt: List<Int>, groups: List<Int>): String {
    var str = ".".repeat(opt[0])
    for (i in 0 until groups.size - 1) {
        val grpLen = groups[i]
        val optLen = opt[i + 1]
        str += "#".repeat(grpLen) + ".".repeat(optLen + 1)
    }
    return str + "#".repeat(groups.last()) + ".".repeat(opt.last())
}

fun genLong(opt: List<Int>, groups: List<Int>): ULong {
    var v = 0UL//".".repeat(opt[0])
    for (i in 0 until groups.size - 1) {
        val grpLen = groups[i]
        val optLen = opt[i + 1]
        val grpVal = 2UL.pow(grpLen) - 1u //"#".repeat(groups[i])
        val shGrpVal = grpVal.shl(optLen + 1) //".".repeat(opt[i + 1]+1)
        v = v.shl(grpLen + optLen + 1)
        v += shGrpVal
    }
    v = v.shl(groups.last())
    v += 2UL.pow(groups.last()) - 1u
    v = v.shl(opt.last())
    return v
}

fun distribute(balls: Int, boxes: Int, distribution: List<Int> = emptyList()): List<List<Int>> {
    return if (boxes == 1) {
        println((distribution + balls).joinToString())
        listOf(distribution + balls)
    } else {
        val result = mutableListOf<List<Int>>()
        for (i in 0..min(1, balls)) {
            result += distribute(balls - i, boxes - 1, distribution + i)
        }
        result
    }
}

data class PatternAsLongs(
    val str: String,
    val groups: List<Int>,
    val expect: Expect
) {
    val hashes = BitString(str.asBinaryULong("#"))
    val dots = BitString(str.asBinaryULong("."))
    val blanks = BitString(str.asBinaryULong("?"))

    fun matches(other: BitString) =
        other.matchesOr(hashes) && other.matchesInvOr(dots) && other.groups == groups

    fun fillBlanksWith(dist: BitString): BitString {
        var filled = BitString(hashes.value)
        var d = 0
        blanks.forEachIndexed { x, i ->
            if (i) {
                if (dist.get(d)) {
                    filled = BitString(filled.value or 2UL.pow(x))
                } else {
                    //
                }
                d++
            } else {
                //
            }
        }
        return filled
    }
}

data class CacheKey(
    val c1: String,
    val c2: List<Int>,
    val c3:Expect
)

val cache3 = mutableMapOf<CacheKey, Long>()
fun submatch(str: String, wanted: List<Int>): Long {
    val curGrps = BitString(str.asBinaryULong("#")).groups
    return if (curGrps == wanted) 1 else 0
}

fun dgroupDiff(gMain: List<Int>, grp: Int?): List<Int> {
    return when {
        gMain.isEmpty() -> gMain
        0 == gMain.first() -> gMain.drop(1)
        else -> gMain
    }
}

fun hgroupDiff(gMain: List<Int>, grp: Int?): List<Int> {
    return when {
        null == grp -> gMain
        gMain.isEmpty() -> gMain
        //grp == gMain.first() -> gMain.drop(1)
        else -> {
            val gf = gMain.first()
            listOf(gf - 1) + gMain.drop(1)
        }
    }
}

fun countMatchDistribute3(start: Int, pattern: PatternAsLongs, balls: Int, boxes: Int): Long {
    val key = CacheKey(pattern.str, pattern.groups, pattern.expect)
    val cached = cache3[key]
    return if (null == cached) {
        val v = when {
            pattern.str.length == 0 -> when {
                0 == pattern.groups.sum() -> 1L
                else -> 0L
            }

            0 == boxes -> {
                val ch = pattern.str[0]
                val ng = when (ch) {
                    '.' -> dgroupDiff(pattern.groups, 1)
                    '#' -> pattern.groups
                    else -> error("")
                }
                submatch(pattern.str, ng)
            }

            pattern.groups.isEmpty() -> {
                submatch(pattern.str, emptyList())
            }

//            0 == balls -> when {
//                0 == pattern.groups.sum() -> 1L
//                else -> 0L
//            }

            else -> {
                val ch = pattern.str[0]
                val rhs = pattern.str.substr(1)
                when (ch) {
                    '.' -> when {
                        pattern.expect == Expect.HASH -> 0L
                        else -> {
                            val ng = dgroupDiff(pattern.groups, 1)
                            val nx = when {
                                else -> Expect.ANY
                            }
                            countMatchDistribute3(start + 1, PatternAsLongs(rhs, ng, nx), balls, boxes)
                        }
                    }

                    '#' -> when {
                        pattern.expect == Expect.DOT -> 0L
                        pattern.groups.first() == 0 -> 0L //end of group should be  a'.'
                        else -> {
                            val ng = hgroupDiff(pattern.groups, 1)
                            val nx = when {
                                pattern.groups.first()==1 -> Expect.DOT
                                else -> Expect.HASH
                            }
                            countMatchDistribute3(start + 1, PatternAsLongs(rhs, ng, nx), balls, boxes)
                        }
                    }


                    '?' -> {
                        var count = 0L
                        // first try '.'
                        val dc = when {
                            pattern.expect == Expect.HASH -> 0L
                            else -> {
                                val dgroupsDiff = dgroupDiff(pattern.groups, 1)
                                val nx = when {
                                    else -> Expect.ANY
                                }
                                countMatchDistribute3(start + 1, PatternAsLongs(rhs, dgroupsDiff, nx), balls, boxes - 1)
                            }
                        }
                        count += dc

                        // then '#'
                        val hc = when {
                            pattern.expect == Expect.DOT -> 0L
                            pattern.groups.first() == 0 -> 0L
                            else -> {
                                val ng = hgroupDiff(pattern.groups, 1)
                                val nx = when {
                                    pattern.groups.first()==1 -> Expect.DOT
                                    else -> Expect.HASH
                                }
                                countMatchDistribute3(start + 1, PatternAsLongs(rhs, ng, nx), balls - 1, boxes - 1)
                            }
                        }
                        count += hc
                        count
                    }

                    else -> error("")
                }
            }
        }
        cache3[key] = v
        v
    } else {
        cached
    }
}

enum class Expect { ANY, DOT, HASH }

fun countMatchesHashesIntoGaps(groups: List<Int>, record: String): Long {
    val gaps = record.count { it == '?' }
    val neededHashes = groups.sum() - record.count { it == '#' }
    println("$record, $groups")
    println("len: ${record.length}")
    println("groups: ${groups.size}")
    println("neededHashes: $neededHashes into: $gaps")
    val p = PatternAsLongs(record, groups, Expect.ANY)
    cache3.clear()
    //distribute( neededHashes, gaps)
    val count = countMatchDistribute3(0, p, neededHashes, gaps)
    return count
}

val memo = mutableMapOf<Pair<Int, Int>, ULong>()
fun combination(n: Int, m: Int): ULong {
    if (m == 0 || m == n) return 1u
    if (m == 1) return n.toULong()
    if (memo.containsKey(Pair(n, m))) {
        return memo[Pair(n, m)]!!
    }
    val result = combination(n - 1, m - 1) + combination(n - 1, m)
    memo[Pair(n, m)] = result
    return result
}

fun task2(lines: List<String>): Long {
    var total = 0L

    //distribute(2,3)

    for (line in lines) {
        val record = line.substringBefore(" ")
        val record2 = "$record?$record?$record?$record?$record"

        val groupSizes = line.substringAfter(" ").split(",").map { it.trim().toInt() }
        val groupSizes2 = groupSizes + groupSizes + groupSizes + groupSizes + groupSizes
        //val regEx = toRegEx(groupSizes2)
        //val alternativeCount = countMatchesOptionsFromGroups(groupSizes2, record2)
        val alternativeCount = countMatchesHashesIntoGaps(groupSizes2, record2)
        println("ways: $alternativeCount")
        total += alternativeCount
    }

    return total
}