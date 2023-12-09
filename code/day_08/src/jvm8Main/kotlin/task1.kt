package day_08

class Location(val s:String) {
    val isStart = s.endsWith("A")
    val isEnd = s.endsWith("Z")

    override fun hashCode(): Int = s.hashCode()
    override fun equals(other: Any?): Boolean = when {
        other !is Location -> false
        this.s != other.s -> false
        else -> true
    }

    override fun toString(): String = s
}

class DesertMap() {

    val entries = mutableMapOf<Location,Pair<Location,Location>>()
    val starts = mutableListOf<Location>()

    fun addEntry(s:String) {
        val key = Location(s.substringBefore("=").trim())
        if (key.isStart) starts.add(key)
        val pr = s.substringAfter("(").substringBeforeLast(")").split(",")
        val e = Pair(Location(pr[0].trim()), Location(pr[1].trim()))
        entries[key] = e
    }

    fun next(loc:Location, dir:String) = when(dir) {
        "L" -> entries[loc]!!.first
        "R" -> entries[loc]!!.second
        else -> error("Should not happen")
    }

}

fun task1(lines: List<String>): Int {
    val lrs = lines[0]
    val map = DesertMap()

    for (i in 2 until lines.size) {
        map.addEntry(lines[i])
    }

    var count = 0
    var stepNum = 0
    var loc = Location("AAA")
    while (loc.s!="ZZZ") {
        val dir = lrs[stepNum].toString()
        loc = map.next(loc,dir)
        count++
        stepNum++
        if(stepNum == lrs.length) stepNum=0
    }
    return count
}