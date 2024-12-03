package day_03.task2

data class Instruction(
    val ins:String,
    val arg1:Int,
    val arg2:Int
)

val expr = Regex("(don[']t[(][)])|(do[(][)])|((mul)[(]([0-9]+)[,]([0-9]+)[)])")
fun process(str:String) :List<Instruction>{
    return expr.findAll(str).map {
        val ins = when {
            null!=it.groups[1] -> it.groups[1]!!.value
            null!=it.groups[2] -> it.groups[2]!!.value
            null!=it.groups[4] -> it.groups[4]!!.value
            else -> error("should not happen")
        }
        val v1 = it.groups[5]?.value?.toInt() ?: 0
        val v2 = it.groups[6]?.value?.toInt() ?: 0
        Instruction(ins,v1,v2)
    }.toList()
}

fun runit(prog:List<Instruction>):Int {
    var on = true
    var tot = 0
    for(ins in prog) {
        when(ins.ins) {
            "mul" -> if (on) {tot += ins.arg1*ins.arg2}
            "do()" -> on = true
            "don't()" -> on = false
        }
    }
    return tot
}

fun task2(lines: List<String>) {
    val test2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))\n"
    val inp = lines.joinToString("")
    val prog = process(inp)
    val total = runit(prog)


    println("Day 03 task 2: $total")
}