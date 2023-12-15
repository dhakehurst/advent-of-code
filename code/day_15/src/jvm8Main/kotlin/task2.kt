package day_15

class Box() {
    val map = linkedMapOf<String, Int>()
    override fun toString(): String = map.toString()
}

class Boxes() {

    val boxes = MutableList(256) { Box() }

    val power:Long get() {
        var pwr = 0L
        for(b in boxes.indices) {
            val bx = boxes[b]
            val slots = bx.map.values.toList()
            for(s in slots.indices) {
                pwr += (1+b) * (1+s) * slots[s]
            }
        }
        return pwr
    }

    fun command(str: String) {
        var cur = 0
        var cmd = ' '
        var v = 0
        var lbl = ""
        for (i in str.indices) {
            val c = str[i]
            when (c) {
                '=' -> {
                    cmd = c
                    val fl = str.substring(i+1)
                    v = when {
                        fl.isBlank() -> 0
                        else -> fl.toInt()
                    }
                    break
                }
                '-' ->  {
                    cmd = c
                    val fl = str.substring(i+1)
                    v = when {
                        fl.isBlank() -> 0
                        else -> fl.toInt()
                    }
                    break
                }
                else -> {
                    lbl+=c
                    cur += c.code  // + ascii code
                    cur *= 17    // * 17
                    cur = cur and 255 // % 256
                }
            }
        }
        when(cmd) {
            '-' -> {
                boxes[cur].map.remove(lbl)
            }
            '=' -> {
                boxes[cur].map[lbl] = v
            }

            else -> error("")
        }
    }

}


fun task2(line: String): Long {
    var total = 0L
    val boxes = Boxes()
    val commands = line.split(",")
    for (cmd in commands) {
        boxes.command(cmd)
    }
    total = boxes.power
    return total
}