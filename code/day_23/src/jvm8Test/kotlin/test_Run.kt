package day_23

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
        println("Day 23 task 1: $actual")
        val expected = 407UL
        assertEquals(expected, actual)
    }

    @Test
    fun run_task2() {
        val result = measureTimedValue {
            task2(lines)
        }
        println("Duration: ${result.duration.inWholeMicroseconds} us")
        val actual = result.value
        println("Day 23 task 2: $actual")
        val expected = 167409079868000UL
        assertEquals(expected, actual)
    }

}