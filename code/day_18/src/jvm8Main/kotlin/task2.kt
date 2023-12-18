package day_18


fun task2(lines: List<String>): Long {

    val instructions = lines.map {
        val split = it.split(" ")
        //val dir = Dir.valueOf(split[0])
        //val dist = split[1].toInt()
        val col = split[2].trim('(', ')','#')
        val dist = col.dropLast(1).toLong(16)
        val dv = col.last().digitToInt()
        val dir = when(dv) {
            0 -> Dir.R
            1 -> Dir.D
            2 -> Dir.L
            3 -> Dir.U
            else -> error("")
        }
        Instruction(dir, dist, col)
    }

    val vertices = mutableListOf<Vertix>()
    vertices.add(Vertix(0, 0))
    var v = vertices.first()
    var perim = 1L
    for (ins in instructions) {
        val inc = ins.dist
        perim += inc
        v = when (ins.dir) {
            Dir.U -> Vertix(v.x,v.y-inc).also { vertices.add(it) }
            Dir.R -> Vertix(v.x+inc,v.y).also { vertices.add(it) }
            Dir.D -> Vertix(v.x,v.y+inc).also { vertices.add(it) }
            Dir.L -> Vertix(v.x-inc,v.y).also { vertices.add(it) }
        }
    }

    val area = area(vertices)


    return area + (perim+1)/2
}