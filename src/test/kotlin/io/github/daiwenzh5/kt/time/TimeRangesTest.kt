package io.github.daiwenzh5.kt.time

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 *
 *
 * @author daiwenzh5
 * @date 2023/4/19
 */
internal class TimeRangesTest {

    @Test
    fun `test equals`() {
        val dateTimeRange = DateTimeTimeRange(
            LocalDateTime.of(2023, 4, 19, 0, 0, 0),
            LocalDateTime.of(2023, 4, 19, 23, 59, 59),
        )
        assertEquals(
            DateTimeTimeRange(
                LocalDateTime.of(2023, 4, 19, 0, 0, 0),
                LocalDateTime.of(2023, 4, 19, 23, 59, 59),
            ), dateTimeRange
        )

        assertNotEquals(
            DateTimeTimeRange(
                LocalDateTime.of(2023, 4, 19, 0, 0, 0),
                LocalDateTime.of(2023, 4, 20, 23, 59, 59),
            ), dateTimeRange
        )
    }

    @Test
    fun `test  toString`() {
        val dateTimeRange = DateTimeTimeRange(
            LocalDateTime.of(2023, 4, 19, 0, 0, 0),
            LocalDateTime.of(2023, 4, 19, 23, 59, 59),
        )
        assertEquals("[2023-04-19T00:00,2023-04-19T23:59:59]", dateTimeRange.toString())
    }


    @Nested
    inner class UseJacksonTest {

        private val objectMapper = ObjectMapper().apply {
            val formatter = "yyyy-MM-dd HH:mm:ss".dateFormatter
            registerModule(
                SimpleModule()
                    .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(formatter))
                    .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(formatter))
            )
        }

        @Test
        fun `test with deserialize`() {
            val json = """[
                "2020-03-27 17:00:00",
                "2020-03-27 18:00:00"
                ]"""
            assertDoesNotThrow {
                objectMapper.readValue(json, DateTimeTimeRange::class.java)
            }
        }

        @Test
        fun `test with serialize`() {
            val dateTimeRange = DateTimeTimeRange(
                LocalDateTime.of(2023, 4, 19, 0, 0, 0),
                LocalDateTime.of(2023, 4, 19, 23, 59, 59),
            )
            assertEquals(
                """["2023-04-19 00:00:00","2023-04-19 23:59:59"]""",
                objectMapper.writeValueAsString(dateTimeRange)
            )
        }
    }

    @Nested
    inner class LocalDateTimeExt {
        @Test
        fun `test start of day`() {
            assertEquals(
                LocalDateTime.of(2023, 4, 19, 0, 0, 0),
                LocalDateTime.of(2023, 4, 19, 12, 17, 57).startOfDay
            )
        }

        @Test
        fun `test end of day`() {
            assertEquals(
                LocalDateTime.of(2023, 4, 19, 23, 59, 59, 999999999),
                LocalDateTime.of(2023, 4, 19, 12, 17, 57).endOfDay
            )
        }

        @Test
        fun `test range of day`() {
            assertEquals(
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 19, 0, 0, 0),
                    LocalDateTime.of(2023, 4, 19, 23, 59, 59, 999999999)
                ),
                LocalDateTime.of(2023, 4, 19, 12, 17, 57).rangeOfDay
            )
        }

        @Test
        fun `test fixed of day`() {
            assertEquals(
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 19, 0, 0, 0),
                    LocalDateTime.of(2023, 4, 19, 23, 59, 59, 999999999)
                ),
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 19, 11, 0, 0),
                    LocalDateTime.of(2023, 4, 19, 11, 59, 59)
                ).fixedOfDay()
            )
        }

        @Test
        fun `test start of month`() {
            assertEquals(
                LocalDateTime.of(2023, 4, 1, 0, 0, 0),
                LocalDateTime.of(2023, 4, 19, 12, 17, 57).startOfMonth
            )
        }

        @Test
        fun `test end of month`() {
            assertEquals(
                LocalDateTime.of(2023, 4, 30, 23, 59, 59, 999999999),
                LocalDateTime.of(2023, 4, 19, 12, 17, 57).endOfMonth
            )
        }

        @Test
        fun `test range of month`() {
            assertEquals(
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 1, 0, 0, 0),
                    LocalDateTime.of(2023, 4, 30, 23, 59, 59, 999999999)
                ),
                LocalDateTime.of(2023, 4, 19, 12, 17, 57).rangeOfMonth
            )
        }

        @Test
        fun `test fixed of month`() {
            assertEquals(
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 1, 0, 0, 0),
                    LocalDateTime.of(2023, 4, 30, 23, 59, 59, 999999999)
                ),
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 19, 11, 0, 0),
                    LocalDateTime.of(2023, 4, 19, 11, 59, 59)
                ).fixedOfMonth()
            )
        }

    }

    @Nested
    inner class LocalDateExt {
        @Test
        fun `test start of day`() {
            assertEquals(
                LocalDateTime.of(2023, 4, 19, 0, 0, 0),
                LocalDate.of(2023, 4, 19).startOfDay,
            )
        }

        @Test
        fun `test end of day`() {
            assertEquals(
                LocalDateTime.of(2023, 4, 19, 23, 59, 59, 999999999),
                LocalDate.of(2023, 4, 19).endOfDay,
            )
        }

        @Test
        fun `test fixed of day`() {
            assertEquals(
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 19, 0, 0, 0),
                    LocalDateTime.of(2023, 4, 19, 23, 59, 59, 999999999)
                ),
                DateRange(
                    LocalDate.of(2023, 4, 19),
                    LocalDate.of(2023, 4, 19)
                ).fixedOfDay()
            )
        }

        @Test
        fun `test start of month`() {
            assertEquals(
                LocalDateTime.of(2023, 4, 1, 0, 0, 0),
                LocalDate.of(2023, 4, 19).startOfMonth
            )
        }

        @Test
        fun `test end of month`() {
            assertEquals(
                LocalDateTime.of(2023, 4, 30, 23, 59, 59, 999999999),
                LocalDate.of(2023, 4, 19).endOfMonth
            )
        }
        @Test
        fun `test range of Month`() {
            assertEquals(
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 1, 0, 0, 0),
                    LocalDateTime.of(2023, 4, 30, 23, 59, 59, 999999999)
                ),
                LocalDate.of(2023, 4, 19).rangeOfMonth
            )
        }

        @Test
        fun `test fixed of Month`() {
            assertEquals(
                DateTimeTimeRange(
                    LocalDateTime.of(2023, 4, 1, 0, 0, 0),
                    LocalDateTime.of(2023, 4, 30, 23, 59, 59, 999999999)
                ),
                DateRange(
                    LocalDate.of(2023, 4, 19),
                    LocalDate.of(2023, 4, 19)
                ).fixedOfMonth()
            )
        }
    }
}