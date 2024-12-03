package day_03.task1

val expr = Regex("mul[(]([0-9]+)[,]([0-9]+)[)]")
fun process(str:String) :List<Pair<Int,Int>>{
    return expr.findAll(str).map {
        val v1 = it.groups[1]!!.value.toInt()
        val v2 = it.groups[2]!!.value.toInt()
        Pair(v1,v2)
    }.toList()
}

fun task1(lines: List<String>) {
    val test = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"

    val inp = lines.joinToString("")
    val res = process(inp)
    val total = res.fold(0) { acc, it -> acc + (it.first * it.second) }

    println("Day 03 task 1: $total")
}