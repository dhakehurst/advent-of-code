package day_02


fun task1(lines: List<String>) {

    println("num lines: "+lines.size)
    var total = 0
    for (line in lines) {
        val nums = line.split(" ").map { it.trim().toInt() }
        val fv1 = nums[0]
        val fv2 = nums[1]
        val increases = when {
            fv1 < fv2 -> true
            fv1 > fv2 -> false
            else -> continue
        }

        val duplicates = nums.toSet().size != nums.size
        if (duplicates) {
            continue
        }
        var safe = true
        for (i in 0 until nums.size - 1) {
            val v1 = nums[i]
            val v2 = nums[i + 1]
            val diff = when {
                increases -> v2 - v1
                else -> v1 - v2
            }
            when {
                diff <= 0 -> {
                    safe = false
                    break
                }

                diff <= 3 -> Unit
                else -> {
                    safe = false
                    break
                }
            }
        }
        if (safe) total += 1

    }

    println("Day 02 task 1: $total")
}