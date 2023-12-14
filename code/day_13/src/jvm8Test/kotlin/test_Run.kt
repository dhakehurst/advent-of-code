package day_13

import korlibs.io.async.suspendTest
import korlibs.io.file.std.resourcesVfs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

class test_Run {

    lateinit var lines: List<String>

    @BeforeTest
    fun before() = suspendTest {
        lines = resourcesVfs["input.txt"].readLines().toList()
    }

    @Test
    fun run_task1() {
        val actual = task1(lines)
        println("Day 13 task 1: $actual")
        val expected = 30518
        assertEquals(expected, actual)
    }

    @Test
    fun run_task2() {
        val result = measureTimedValue {
            task2(lines)
        }
        println("Duration: ${result.duration.inWholeMicroseconds} us")
        val actual = result.value
        println("Day 13 task 2: $actual")
        val expected = 11795205644011L
        assertEquals(expected, actual)
    }

    @Test
    fun pals() {
        val s = "x.."
        val p = palindromesIn(s)
        assertEquals(1, p.size)
    }
}