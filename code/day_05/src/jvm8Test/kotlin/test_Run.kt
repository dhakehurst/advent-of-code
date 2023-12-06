package day_05

import korlibs.io.async.suspendTest
import korlibs.io.file.std.resourcesVfs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
        val actual = task2(lines)
        val expected = 137516820L
        assertEquals(expected, actual)
    }

}