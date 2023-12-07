package day_07

import org.junit.Test
import kotlin.test.assertTrue

class test_Hand2 {

    @Test
    fun t() {

        val h1 = Hand2("JJJJJ", 1)
        val h2 = Hand2("JJJA7", 1)

        assertTrue(h1 > h2)
    }
}