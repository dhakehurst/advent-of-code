package day_12

import org.junit.Test
import kotlin.test.assertEquals

class test_matchFunction {

    @Test
    fun d_E() {
        val str = "."
        val grps = listOf<Int>()

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun d_0() {
        val str = "."
        val grps = listOf(0)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun d_1() {
        val str = "."
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun d_2() {
        val str = "."
        val grps = listOf(2)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun h_E() {
        val str = "#"
        val grps = listOf<Int>()

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun h_0() {
        val str = "#"
        val grps = listOf(0)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun h_1() {
        val str = "#"
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun h_2() {
        val str = "#"
        val grps = listOf(2)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun q_E() {
        val str = "?"
        val grps = listOf<Int>()

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun q_0() {
        val str = "?"
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun q_1() {
        val str = "?"
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun q_2() {
        val str = "?"
        val grps = listOf(2)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun d_0_1() {
        val str = "."
        val grps = listOf(0,1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun h_0_1() {
        val str = "#"
        val grps = listOf(0,1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun q_0_1() {
        val str = "?"
        val grps = listOf(0,1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(0, actual)
    }

    @Test
    fun hq_1() {
        val str = "#?"
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun dq_1() {
        val str = ".?"
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun qq_0() {
        val str = "??"
        val grps = listOf(0)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun qq_1() {
        val str = "??"
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(2, actual)
    }

    @Test
    fun qqq_1() {
        val str = "???"
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(3, actual)
    }

    @Test
    fun qqq_2() {
        val str = "???"
        val grps = listOf(2)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(2, actual)
    }

    @Test
    fun hqq_1_1() {
        val str = "#??"
        val grps = listOf(1,1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun hhqq_2_1() {
        val str = "##??"
        val grps = listOf(2,1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(1, actual)
    }

    @Test
    fun qqqq_1() {
        val str = "????"
        val grps = listOf(1)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(4, actual)
    }

    @Test
    fun qqqq_2() {
        val str = "????"
        val grps = listOf(2)

        val actual = countMatchesHashesIntoGaps(grps, str)
        assertEquals(3, actual)
    }

}