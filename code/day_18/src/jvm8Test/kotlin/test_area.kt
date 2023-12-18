package day_18

import org.junit.Test
import kotlin.test.assertEquals

class test_area {

    @Test
    fun a1_1() {
        val actual = area(listOf(
            Vertix(0,0),
            Vertix(1,0),
            Vertix(1,1),
            Vertix(0,1),
            Vertix(0,0),
        ))

        assertEquals(1, actual)
    }

    @Test
    fun a2_2() {
        val actual = area(listOf(
            Vertix(0,0),
            Vertix(2,0),
            Vertix(2,2),
            Vertix(0,2),
            Vertix(0,0),
        ))

        assertEquals(4, actual)
    }

}