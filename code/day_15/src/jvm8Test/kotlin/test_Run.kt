package day_15

import korlibs.io.async.suspendTest
import korlibs.io.file.std.resourcesVfs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

class test_Run {

    lateinit var line: String

    @BeforeTest
    fun before() = suspendTest {
        line = resourcesVfs["input.txt"].readString()
    }

    @Test
    fun run_task1() {
        val actual = task1(line)
        println("Day 15 task 1: $actual")
        val expected = 507666UL
        assertEquals(expected, actual)
    }

    @Test
    fun run_task2() {
        val result = measureTimedValue {
            task2(line)
        }
        println("Duration: ${result.duration.inWholeMicroseconds} us")
        val actual = result.value
        println("Day 15 task 2: $actual")
        val expected = 233537L
        assertEquals(expected, actual)
    }

}