package day_02

import korlibs.io.async.suspendTest
import korlibs.io.file.std.resourcesVfs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.measureTime

class test_Run {

    lateinit var lines: List<String>

    @BeforeTest
    fun before() = suspendTest {
        lines = resourcesVfs["input.txt"].readLines().toList()
    }

    @Test
    fun run_task1() {
        val d = measureTime {
            task1(lines)
        }
        println("Duration: ${d.inWholeMicroseconds} us")
    }

    @Test
    fun run_task2() {
        val d = measureTime {
            task2b(lines)
        }
        println("Duration: ${d.inWholeMicroseconds} us")
    }
}