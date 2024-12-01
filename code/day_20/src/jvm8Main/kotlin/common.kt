package day_19

data class Applied(
    val result: GenericPart?,
    val remainder: List<GenericPart>
)

sealed class Rule {
    companion object {
        fun create(input: String): Rule {
            return when {
                "R" == input -> RejectRule
                "A" == input -> AcceptRule
                input.contains('<') -> {
                    val prop = input.substringBefore("<")
                    val value = input.substringAfter("<").substringBefore(":").toUInt()
                    val next = input.substringAfter(":")
                    LessThanRule(prop, value, next)
                }

                input.contains('>') -> {
                    val prop = input.substringBefore(">")
                    val value = input.substringAfter(">").substringBefore(":").toUInt()
                    val next = input.substringAfter(":")
                    GreaterThanRule(prop, value, next)
                }

                input.contains('=') -> {
                    val prop = input.substringBefore("=")
                    val value = input.substringAfter("=").substringBefore(":").toUInt()
                    val next = input.substringAfter(":")
                    EqualToRule(prop, value, next)
                }

                else -> ChangeWorkflowRule(input)
            }
        }
    }

    /**
     * return either the next rule in the workflow or null if rule not applicable
     */
    abstract fun process(part: Part): String?

    abstract fun applyTo(gp: GenericPart): Applied?
}

data object AcceptRule : Rule() {
    override fun process(part: Part): String = "A"

    override fun applyTo(gp: GenericPart): Applied = Applied(GenericPart(nextWf = "A", x = gp.x, m = gp.m, a = gp.a, s = gp.s), emptyList())
}

data object RejectRule : Rule() {
    override fun process(part: Part): String = "R"

    override fun applyTo(gp: GenericPart): Applied = Applied(GenericPart(nextWf = "R", x = gp.x, m = gp.m, a = gp.a, s = gp.s), emptyList())
}

data class ChangeWorkflowRule(
    val next: String
) : Rule() {
    override fun process(part: Part): String = next

    override fun applyTo(gp: GenericPart): Applied = Applied(GenericPart(nextWf = next, x = gp.x, m = gp.m, a = gp.a, s = gp.s), emptyList())
}

fun GenericPart.applyToX(next: String, pair: Pair<UIntRange, List<UIntRange>>): Applied = pair.let { (r, l) ->
    Applied(
        GenericPart(next, r, m, a, s),
        l.map { GenericPart(nextWf, it, m, a, s) },
    )
}

fun GenericPart.applyToM(next: String, pair: Pair<UIntRange, List<UIntRange>>): Applied = pair.let { (r, l) ->
    Applied(
        GenericPart(next, x, r, a, s),
        l.map { GenericPart(nextWf, x, it, a, s) },
    )
}

fun GenericPart.applyToA(next: String, pair: Pair<UIntRange, List<UIntRange>>): Applied = pair.let { (r, l) ->
    Applied(
        GenericPart(next, x, m, r, s),
        l.map { GenericPart(nextWf, x, m, it, s) },
    )
}

fun GenericPart.applyToS(next: String, pair: Pair<UIntRange, List<UIntRange>>): Applied = pair.let { (r, l) ->
    Applied(
        GenericPart(next, x, m, a, r),
        l.map { GenericPart(nextWf, x, m, a, it) },
    )
}

fun UIntRange.lessThan(v: UInt) = when {
    v < first -> null
    v > last -> Pair(this, emptyList<UIntRange>())
    else -> Pair(UIntRange(first, v - 1u), listOf(UIntRange(v, last)))
}

fun UIntRange.greaterThan(v: UInt) = when {
    v > last -> null
    v < first -> Pair(this, emptyList<UIntRange>())
    else -> Pair(UIntRange(v + 1u, last), listOf(UIntRange(first, v)))
}

fun UIntRange.equalTo(v: UInt) = when {
    v < first -> null
    v > last -> null
    else -> Pair(UIntRange(v, v), listOf(UIntRange(first, v - 1u), UIntRange(v + 1u, last)))
}

data class LessThanRule(
    val prop: String,
    val value: UInt,
    val next: String
) : Rule() {
    override fun process(part: Part): String? {
        val propValue = part.properties[prop]
        return when {
            null == propValue -> null
            propValue < value -> next
            else -> null
        }
    }

    override fun applyTo(gp: GenericPart): Applied? {
        return when (prop) {
            "x" -> gp.x.lessThan(value)?.let { gp.applyToX(next, it) }
            "m" -> gp.m.lessThan(value)?.let { gp.applyToM(next, it) }
            "a" -> gp.a.lessThan(value)?.let { gp.applyToA(next, it) }
            "s" -> gp.s.lessThan(value)?.let { gp.applyToS(next, it) }
            else -> error("")
        }
    }
}

data class GreaterThanRule(
    val prop: String,
    val value: UInt,
    val next: String
) : Rule() {
    override fun process(part: Part): String? {
        val propValue = part.properties[prop]
        return when {
            null == propValue -> null
            propValue > value -> next
            else -> null
        }
    }

    override fun applyTo(gp: GenericPart): Applied? {
        return when (prop) {
            "x" -> gp.x.greaterThan(value)?.let { gp.applyToX(next, it) }
            "m" -> gp.m.greaterThan(value)?.let { gp.applyToM(next, it) }
            "a" -> gp.a.greaterThan(value)?.let { gp.applyToA(next, it) }
            "s" -> gp.s.greaterThan(value)?.let { gp.applyToS(next, it) }
            else -> error("")
        }
    }
}

data class EqualToRule(
    val prop: String,
    val value: UInt,
    val next: String
) : Rule() {
    override fun process(part: Part): String? {
        val propValue = part.properties[prop]
        return when {
            null == propValue -> null
            propValue == value -> next
            else -> null
        }
    }

    override fun applyTo(gp: GenericPart): Applied? {
        return when (prop) {
            "x" -> gp.x.equalTo(value)?.let { gp.applyToX(next, it) }
            "m" -> gp.m.equalTo(value)?.let { gp.applyToM(next, it) }
            "a" -> gp.a.equalTo(value)?.let { gp.applyToA(next, it) }
            "s" -> gp.s.equalTo(value)?.let { gp.applyToS(next, it) }
            else -> error("")
        }
    }

}

data class Part(val input: String) {
    val properties: Map<String, UInt> by lazy {
        input.split(",").associate { Pair(it.substringBefore("="), it.substringAfter("=").toUInt()) }
    }

    val rating: UInt by lazy { properties.values.sum() }
}

data class Workflow(
    val input: String
) {
    val name: String by lazy { input.substringBefore("{") }
    val rules: List<Rule> by lazy { input.substringAfter("{").substringBefore("}").split(",").map { Rule.create(it) } }

    fun process(part: Part): String = rules.firstNotNullOf { it.process(part) }

    fun applyTo(gp: GenericPart): List<GenericPart> {
        //rules.mapNotNull { rl -> rl.applyTo(gp) }
        val res = mutableListOf<GenericPart>()
        var rest = listOf(gp)
        for (rl in rules) {
            for (r in rest) {
                val ap = rl.applyTo(r)
                rest = ap?.let {
                    ap.result?.let { res.add(ap.result) }
                    ap.remainder
                } ?: emptyList()
            }
        }
        return res
    }
}

class Factory(
    val input: List<String>
) {
    val workflows: Map<String, Workflow> by lazy {
        input.associate { ip ->
            Workflow(ip).let { Pair(it.name, it) }
        }
    }

    val inWorkflow: Workflow by lazy { workflows["in"]!! }

    fun process(part: Part): Part? {
        var wf = inWorkflow
        while (true) {
            val nwfn = wf.process(part)
            when (nwfn) {
                "A" -> return part
                "R" -> return null
                else -> wf = workflows[nwfn] ?: error("")
            }
        }
    }

    fun countAccepted(gp: GenericPart): ULong {
        val accepted = mutableListOf<GenericPart>()
        val todo = mutableListOf(gp)
        while (todo.isNotEmpty()) {
            val ngp = todo.removeFirst()
            when (ngp.nextWf) {
                "A" -> accepted.add(ngp)
                "R" -> Unit
                else -> {
                    val wf = workflows[ngp.nextWf]!!
                    val n = wf.applyTo(ngp)
                    todo.addAll(n)
                }
            }
        }
        val c = accepted.sumOf { it.ratingCount }
        return c
    }
}