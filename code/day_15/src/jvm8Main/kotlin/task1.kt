package day_15

fun proc(cur:Pair<ULong, ULong>, c:Char) = when(c) {
    ',' -> Pair(cur.first + cur.second,0UL)
    else ->{
        val c1 = cur.second+ c.code.toUInt()  // + ascii code
        val c2 = c1 * 17u//.shl(3) + c1.value) // * 17
        val c3 = c2 and 255u // % 256
        Pair(cur.first, c3)
    }
}

fun task1(line: String): ULong {

    val total = line.fold(Pair(0UL, 0UL)) { a, i -> proc(a, i)  }

    return total.first + total.second
}