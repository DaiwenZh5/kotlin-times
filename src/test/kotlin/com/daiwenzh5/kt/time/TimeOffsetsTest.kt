package com.daiwenzh5.kt.time

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * 偏移时间测试
 * @author daiwenzh5
 * @date 2023/4/18
 */
class TimeOffsetsTest {

    @Test
    fun `test with centuries`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(1923, 1, 1, 0, 0, 0),
            time - 1.CENTURIES
        )

        assertEquals(
            LocalDateTime.of(2123, 1, 1, 0, 0, 0),
            time + 1.CENTURIES
        )
    }

    @Test
    fun `test with decades`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2013, 1, 1, 0, 0, 0),
            time - 1.DECADES
        )

        assertEquals(
            LocalDateTime.of(2033, 1, 1, 0, 0, 0),
            time + 1.DECADES
        )
    }

    @Test
    fun `test with years`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2020, 1, 1, 0, 0, 0),
            time - 3.YEARS
        )

        assertEquals(
            LocalDateTime.of(2026, 1, 1, 0, 0, 0),
            time + 3.YEARS
        )
    }

    @Test
    fun `test with months`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2022, 9, 1, 0, 0, 0),
            time - 4.MONTHS
        )

        assertEquals(
            LocalDateTime.of(2023, 5, 1, 0, 0, 0),
            time + 4.MONTHS
        )
    }

    @Test
    fun `test with weeks`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2022, 12, 11, 0, 0, 0),
            time - 3.WEEKS
        )

        assertEquals(
            LocalDateTime.of(2023, 1, 22, 0, 0, 0),
            time + 3.WEEKS
        )
    }

    @Test
    fun `test with days`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2022, 12, 29, 0, 0, 0),
            time - 3.DAYS
        )

        assertEquals(
            LocalDateTime.of(2023, 1, 4, 0, 0, 0),
            time + 3.DAYS
        )
    }

    @Test
    fun `test with hours`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2022, 12, 29, 21, 0, 0),
            time - 51.HOURS
        )

        assertEquals(
            LocalDateTime.of(2023, 1, 2, 3, 0, 0),
            time + 27.HOURS
        )
    }

    @Test
    fun `test with minutes`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2023, 1, 1, 0, 30, 0),
            time + 30.MINUTES
        )

        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 0, 0),
            time - 60.MINUTES
        )
    }

    @Test
    fun `test with seconds`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2023, 1, 1, 0, 0, 30),
            time + 30.SECONDS
        )

        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 59, 0),
            time - 60.SECONDS
        )
    }

    @Test
    fun `test with milliseconds`() {
        val time = LocalDateTime.of(2023, 1, 1, 0, 0, 0)

        assertEquals(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0, 500_000_000),
            time + 500.MILLIS
        )

        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 59, 59, 500_000_000),
            time - 500.MILLIS
        )
    }

    @Test
    fun `test with ago`() {
        assertEquals(
            (now - 1.DAYS).dayOfYear,
            1.DAYS.ago.dayOfYear
        )
    }


    @Test
    fun `test with later`() {
        assertEquals(
            (now + 1.DAYS).dayOfYear,
            1.DAYS.later.dayOfYear
        )
    }

    @Test
    fun `test times`() {
        assertEquals(
            (now + 10.DAYS).dayOfYear,
            (1.DAYS * 10).later.dayOfYear
        )
    }
}