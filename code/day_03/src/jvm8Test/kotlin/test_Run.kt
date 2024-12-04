package day_03

import korlibs.io.async.suspendTest
import korlibs.io.file.std.resourcesVfs
import kotlin.test.BeforeTest
import kotlin.test.Test

class test_Run {

    lateinit var lines: List<String>

    @BeforeTest
    fun before() = suspendTest {
        lines = resourcesVfs["input3.txt"].readLines().toList()
    }

    @Test
    fun run_task1() = day_03.task1.task1(lines)

    @Test
    fun run_task2() = day_03.task2.task2(lines)

}