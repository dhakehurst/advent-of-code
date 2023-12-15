package day_12

import kotlin.math.pow

fun ULong.pow(n: Int): ULong = this.toDouble().pow(n).toULong()
fun String.asBinaryULong(charsAsOne:String): ULong =
    this.foldIndexed(0UL) { i, a, c ->
        when  {
            charsAsOne.contains(c) -> a + (2UL.pow((this.length-1)-i ))
            else -> a
        }
    }

