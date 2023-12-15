package day_12

import java.util.*
import kotlin.math.min
import kotlin.math.pow

class BitString(
    val value: ULong
) : Iterable<Boolean> {

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

fun countMatchDistribute(groups: List<Int>, pattern: PatternAsLongs, balls: Int, boxes: Int, distribution: List<Int> = emptyList()): Long {
    return if (boxes == 1) {
        //println((distribution + balls).joinToString())
        val opt = distribution + balls
//        val s = genStr(opt,groups)
//        println(s)
        val r = when {
            pattern.matches(BitString(genLong(opt, groups))) -> 1L
            else -> 0L
        }
//        println("try matching: ($r) ${genLong(opt, groups).toString(2)}")
        r
    } else {
        var count = 0L
        for (i in 0..min(1, balls)) {
            count += countMatchDistribute(groups, pattern, balls - i, boxes - 1, distribution + i)
        }
        count
    }
}

data class PatternAsLongs(
    val str: String
) {
    val hashes = BitString(str.asBinaryULong("#"))
    val dots = BitString(str.asBinaryULong("."))
    val blanks = BitString(str.asBinaryULong("?"))

    fun matches(other: BitString) =
        other.matchesOr(hashes) && other.matchesInvOr(dots)

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

val String.toPatternAsLongs get() = PatternAsLongs(this)

// count how many from the distribution will match the pattern
// boxes are the '?', balls are the missing '#'
//
fun countMatchDistribute2(pattern: PatternAsLongs, balls: Int, boxes: Int, distribution: List<Int> = emptyList()): Long {
    return if (boxes == 1) {
        //println((distribution + balls).joinToString())
        val opt = distribution + balls
//        val s = genStr(opt,groups)
//        println(s)
        val r = when {
            balls > 1 -> 0L
            pattern.matches(pattern.fillBlanksWith(opt.toBitString)) -> 1L
            else -> 0L
        }
//        println("try matching: ($r) ${genLong(opt, groups).toString(2)}")
        r
    } else {
        var count = 0L
        for (i in 0..min(1, balls)) {
            count += countMatchDistribute2(pattern, balls - i, boxes - 1, distribution + i)
        }
        count
    }
}

fun countMatchesOptionsFromGroups(groups: List<Int>, record: String): Long {
    //val patternStr = record.replace('.', '_').replace('?', '.').replace("_", "[.]")
    //val pattern = Regex(patternStr)
    val p = record.toPatternAsLongs
    //val pattern = toRegEx(groups)
    val length = record.length
    val diff = length - groups.sum() - (groups.size - 1)
    val numGaps = groups.size + 1
    //val options = distribute(diff, numGaps)
//    println(p.toString())
    println("groups: z=${groups.size} s=${groups.sum()} $groups")
//    println("diff: $diff")
//    println("numGaps: $numGaps")
    println("balls: $diff  boxes: $numGaps")
    val count = when (diff) {
        0 -> 1
        else -> countMatchDistribute(groups, p, diff, numGaps)
    }
    return count
}

fun countMatchesHashesIntoGaps(groups: List<Int>, record: String): Long {
    val gaps = record.count { it == '?' }
    val neededHashes = groups.sum() - record.count { it == '#' }
    println(record)
    println("len: ${record.length}")
    println("neededHashes: $neededHashes into: $gaps")
    val p = record.toPatternAsLongs
    val count = countMatchDistribute2(p, neededHashes, gaps)
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
        val alternativeCount = countMatchesHashesIntoGaps(groupSizes, record)
        println("ways: $alternativeCount")
        total += alternativeCount
    }

    return total
}