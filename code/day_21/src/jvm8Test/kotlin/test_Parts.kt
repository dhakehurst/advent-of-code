package day_19

import org.junit.Test
import kotlin.test.assertEquals

class test_Parts {

    @Test
    fun lessThan() {
        val actual = UIntRange(1351u, 4000u).lessThan(2770u)
        assertEquals(UIntRange(1351u,2769u), actual!!.first)
        assertEquals(listOf(UIntRange(2770u,4000u)), actual!!.second)
    }

}