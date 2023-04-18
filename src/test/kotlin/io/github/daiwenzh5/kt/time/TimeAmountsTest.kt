package io.github.daiwenzh5.kt.time

import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * 时间量测试
 * @author daiwenzh5
 * @date 2023/4/18
 */
class TimeAmountsTest {

    @Test
    fun `test with centuries`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(1923, 1, 1, 0, 0, 0),
            time - 1.centuries
        )

        assertEquals(
            LocalDateTime.of(2123, 1, 1, 0, 0, 0),
            time + 1.centuries
        )
    }

    @Test
    fun `test with decades`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2013, 1, 1, 0, 0, 0),
            time - 1.decades
        )

        assertEquals(
            LocalDateTime.of(2033, 1, 1, 0, 0, 0),
            time + 1.decades
        )
    }

    @Test
    fun `test with years`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2020, 1, 1, 0, 0, 0),
            time - 3.years
        )

        assertEquals(
            LocalDateTime.of(2026, 1, 1, 0, 0, 0),
            time + 3.years
        )
    }

    @Test
    fun `test with months`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2022, 9, 1, 0, 0, 0),
            time - 4.months
        )

        assertEquals(
            LocalDateTime.of(2023, 5, 1, 0, 0, 0),
            time + 4.months
        )
    }

    @Test
    fun `test with weeks`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2022, 12, 11, 0, 0, 0),
            time - 3.weeks
        )

        assertEquals(
            LocalDateTime.of(2023, 1, 22, 0, 0, 0),
            time + 3.weeks
        )
    }

    @Test
    fun `test with days`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2022, 12, 29, 0, 0, 0),
            time - 3.days
        )

        assertEquals(
            LocalDateTime.of(2023, 1, 4, 0, 0, 0),
            time + 3.days
        )
    }

    @Test
    fun `test with hours`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2022, 12, 29, 21, 0, 0),
            time - 51.hours
        )

        assertEquals(
            LocalDateTime.of(2023, 1, 2, 3, 0, 0),
            time + 27.hours
        )
    }

    @Test
    fun `test with minutes`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2023, 1, 1, 0, 30, 0),
            time + 30.minutes
        )

        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 0, 0),
            time - 60.minutes
        )
    }

    @Test
    fun `test with seconds`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2023, 1, 1, 0, 0, 30),
            time + 30.seconds
        )

        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 59, 0),
            time - 60.seconds
        )
    }

    @Test
    fun `test with milliseconds`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0, 500_000_000),
            time + 500.millis
        )

        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 59, 59, 500_000_000),
            time - 500.millis
        )
    }

    @Test
    fun `test with ago`() {
        assertEquals(
            (now - 1.days).dayOfYear,
            1.days.ago.dayOfYear
        )
    }


    @Test
    fun `test with later`() {
        assertEquals(
            (now + 1.days).dayOfYear,
            1.days.later.dayOfYear
        )
    }

    @Test
    fun `test times`() {
        assertEquals(
            (now + 10.days).dayOfYear,
            (1.days * 10).later.dayOfYear
        )
    }

    @Test
    fun `test div`() {
        assertEquals(
            (now + 2.days).dayOfYear,
            (20.days / 10).later.dayOfYear
        )
    }
}