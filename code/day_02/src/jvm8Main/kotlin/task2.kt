package day_02

import kotlin.math.abs

enum class Dir { UNK, INC, DEC }

fun safePair(dir: Dir, v1: Int, v2: Int): Boolean = when {
    v1 == v2 -> false
    else -> when (dir) {
        Dir.UNK -> abs(v1 - v2) > 0 && abs(v1 - v2) < 4
        Dir.INC -> (v2 - v1) > 0 && (v2 - v1) < 4
        Dir.DEC -> (v1 - v2) > 0 && (v1 - v2) < 4
    }
}

/**
 *
 */
fun safeTriple(dir: Dir, v1: Int, v2: Int, v3: Int): Pair<Int, Dir> = when {
    safePair(dir, v1, v2) -> Pair(+1, dirIfUnknown(dir, v1, v2))
    safePair(dir, v1, v3) -> Pair(+2, dirIfUnknown(dir, v1, v3))
    safePair(dir, v2, v3) -> Pair(+2, dirIfUnknown(dir, v2, v3))
    else -> Pair(-1, dir)
}

fun dirOf(v1: Int, v2: Int) = when {
    v1 < v2 -> Dir.INC
    v1 > v2 -> Dir.DEC
    else -> Dir.UNK
}

fun dirIfUnknown(dir: Dir, v1: Int, v2: Int) = when {
    Dir.UNK == dir -> dirOf(v1, v2)
    else -> dir
}

fun checkSafe(list: List<Int>) {

}

fun lineSafe(nums: List<Int>): Boolean {
    var i = 0
    val dir = dirOf(nums[0], nums[1])
    while (i < nums.size - 1) {
        val v1 = nums[i]
        val v2 = nums[i + 1]
        when (safePair(dir, v1, v2)) {
            true -> Unit
            else -> return false
        }
        i += 1
    }
    return  true
}

fun task2b(lines: List<String>) {
    println("num lines: " + lines.size)
    var total = 0
    for (ln in lines.indices) {
        val line = lines[ln]
        val nums = line.split(" ").map { it.trim().toInt() }
        if (lineSafe(nums)) {
            total+=1
        } else {
            // try with each removed
            for(i in 0..nums.size - 1) {
                val nums2 = nums.take(i) + nums.drop(i + 1)
                if (lineSafe(nums2)) {
                    total+=1
                    break
                } else {
                    continue
                }
            }
        }
    }
    println("Day 02 task 2: $total")
}

fun task2(lines: List<String>) {
    println("num lines: " + lines.size)
    var total = 0
    for (ln in lines.indices) {
        val line = lines[ln]
        val nums = line.split(" ").map { it.trim().toInt() }
        var unsafeAt = mutableListOf<Int>()
        var i = 0
        var nx = 1
        var dir = Dir.UNK
        while (i < nums.size - 2) {
            val v1 = nums[i]
            val v2 = nums[i + 1]
            val v3 = nums[i + 2]
            val p = safeTriple(dir, v1, v2, v3)
            nx = p.first
            dir = p.second
            when (nx) {
                1 -> Unit // all ok
                2 -> when { // skipped one
                    unsafeAt.isEmpty() -> unsafeAt.add(v1)
                    else -> {
                        unsafeAt.add(v1)
                        i = nums.size + 2 //stop this list, already skipped one
                    }
                }

                else -> i = nums.size + 2 //stop this list
            }
            i += nx
        }

        if (i < nums.size - 1) {
            when (safePair(dir, nums[i], nums[i + 1])) {
                true -> Unit
                else -> unsafeAt.add(nums[i])
            }
        }

        if (unsafeAt.size < 2) {
            total += 1
            checkSafe(nums)
        } else {
            println("$ln Unsafe at $unsafeAt: $line")
        }

    }

    println("Day 02 task 2: $total")
}
