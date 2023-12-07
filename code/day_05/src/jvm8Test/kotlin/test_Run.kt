package day_05

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
        val expected = 389056265L
        assertEquals(expected, actual)
    }

    @Test
    fun run_task2() {
       val dv= measureTimedValue {
            task2(lines)
        }
        println("Duration: ${dv.duration.inWholeMicroseconds} us")
        val actual = dv.value
        val expected = 137516820L
        assertEquals(expected, actual)
    }

}