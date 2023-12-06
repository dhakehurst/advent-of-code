package day_05

import org.junit.Test
import kotlin.test.assertEquals

class test_LongRangeIntersection {

    @Test
    fun equal_ranges() {
        val r1 = LongRange(0,10)
        val r2 = LongRange(0,10)

        val actual = r1.intersect(r2)
        val expected = LongRangeIntersection(null, LongRange(0,10), null)

        assertEquals(expected, actual)
    }

    @Test
    fun this_before_other_no_intersect() {
        val r1 = LongRange(0,10)
        val r2 = LongRange(20,100)

        val actual = r1.intersect(r2)
        val expected = null

        assertEquals(expected, actual)
    }

    @Test
    fun this_after_other_no_intersect() {
        val r1 = LongRange(20,100)
        val r2 = LongRange(0,10)

        val actual = r1.intersect(r2)
        val expected = null

        assertEquals(expected, actual)
    }

    @Test
    fun this_before_other_with_intersect() {
        val r1 = LongRange(0,10)
        val r2 = LongRange(5,20)

        val actual = r1.intersect(r2)
        val expected = LongRangeIntersection(LongRange(0,4), LongRange(5,10), null)

        assertEquals(expected, actual)
    }

    @Test
    fun this_after_other_with_intersect() {
        val r1 = LongRange(5,20)
        val r2 = LongRange(0,10)

        val actual = r1.intersect(r2)
        val expected = LongRangeIntersection(null, LongRange(5,10), LongRange(11,20))

        assertEquals(expected, actual)
    }

    @Test
    fun this_subsets_other() {
        val r1 = LongRange(5,10)
        val r2 = LongRange(0,20)

        val actual = r1.intersect(r2)
        val expected = LongRangeIntersection(null, LongRange(5,10), null)

        assertEquals(expected, actual)
    }

    @Test
    fun this_supersets_other() {
        val r1 = LongRange(0,20)
        val r2 = LongRange(5,10)

        val actual = r1.intersect(r2)
        val expected = LongRangeIntersection(LongRange(0,4), LongRange(5,10), LongRange(11,20))

        assertEquals(expected, actual)
    }

}