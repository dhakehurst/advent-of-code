package day_17

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class test_Grid {

    @Test
    fun g1_1() {
        val lines = listOf(
            "1"
        )

        val actual = task1(lines)
        assertEquals(0UL, actual)
    }

    @Test
    fun g2_2() {
        val lines = listOf(
            "00",
            "11"
        )

        val actual = task1(lines)
        assertEquals(1UL, actual)
    }

    @Test
    fun g3_3_a() {
        val lines = listOf(
            "000",
            "321",
            "652"
        )

        val actual = task1(lines)
        assertEquals(3UL, actual)
    }

    @Test
    fun g3_3_b() {
        val lines = listOf(
            "065",
            "124",
            "002"
        )

        val actual = task1(lines)
        assertEquals(3UL, actual)
    }

    @Test
    fun g3_3_c() {
        val lines = listOf(
            "015",
            "714",
            "812"
        )

        val actual = task1(lines)
        assertEquals(5UL, actual)
    }

    @Test
    fun g5_5a() {
        val lines = listOf(
            "19111",
            "11191",
            "99911",
            "99919",
            "99911",
        )

        val actual = task1(lines)
        assertEquals(12UL, actual)
    }

    @Test
    fun g5_5b() {
        val lines = listOf(
            "11999",
            "91999",
            "11999",
            "19111",
            "11191",
        )

        val actual = task1(lines)
        assertEquals(12UL, actual)
    }
}