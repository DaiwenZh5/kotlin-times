package io.github.daiwenzh5.kt.time

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * 时间符号测试
 * @author daiwenzh5
 * @date 2023/4/19
 */
class TimeSignsTest {

    @Test
    fun `test years ago`() {
        assertEquals(now.year - 3, (3 years ago).year)
    }

    @Test
    fun `test years later`() {
        assertEquals(now.year + 3, (3 years later).year)
    }

    @Test
    fun `test months ago`() {
        assertEquals(now.monthValue.offsetWithMod(-5, 12), (5 months ago).monthValue)
    }

    @Test
    fun `test months later`() {
        assertEquals(now.monthValue.offsetWithMod(5, 12), (5 months later).monthValue)
    }

    @Test
    fun `test weeks ago`() {
        assertEquals(now.dayOfWeek, (2 weeks ago).dayOfWeek)
    }

    @Test
    fun `test weeks later`() {
        assertEquals(now.dayOfWeek, (2 weeks later).dayOfWeek)
    }

    @Test
    fun `test days ago`() {
        assertEquals(now.dayOfYear - 10, (10 days ago).dayOfYear)
    }

    @Test
    fun `test days later`() {
        assertEquals(now.dayOfYear + 10, (10 days later).dayOfYear)
    }

    @Test
    fun `test hours ago`() {
        assertEquals(now.hour.offsetWithMod(-6, 24), (6 hours ago).hour)

    }

    @Test
    fun `test hours later`() {
        assertEquals(now.hour.offsetWithMod(6, 24), (6 hours later).hour)
    }

    @Test
    fun `test minutes ago`() {
        assertEquals(now.minute.offsetWithMod(-30 ,60), (30 minutes ago).minute)
    }

    @Test
    fun `test minutes later`() {
        assertEquals(now.minute.offsetWithMod(30 ,60), (30 minutes later).minute)
    }

    @Test
    fun `test seconds ago`() {
        assertEquals(now.second.offsetWithMod(-20, 60), (20 seconds ago).second)
    }

    @Test
    fun `test seconds later`() {
        assertEquals(now.second.offsetWithMod(20, 60), (20 seconds later).second)
    }

    @Test
    fun `test millis ago`() {
        assertEquals(now.hour - 1, (3_600_000 millis ago).hour)
    }

    @Test
    fun `test millis later`() {
        assertEquals(now.hour + 1, (3_600_000 millis later).hour)
    }

    private fun Int.offsetWithMod(offset: Int, mod: Int): Int  = (this + mod + offset) % mod

}