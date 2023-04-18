package com.daiwenzh5.kt.time

import com.daiwenzh5.kt.time.json.DateRangeDeserializer
import com.daiwenzh5.kt.time.json.DateRangeSerializer
import com.daiwenzh5.kt.time.json.DateTimeRangeDeserializer
import com.daiwenzh5.kt.time.json.DateTimeRangeSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters

/**
 * 时间范围
 *
 * @author daiwenzh5
 * @date 2023/4/19
 */
// ================================================================================================

sealed class Range<T>(open val start: T, open val end: T) : Serializable {
    override fun toString() = "[$start,$end]"

    fun toPair(): Pair<T, T> = Pair(start, end)

    override fun equals(other: Any?): Boolean {
        if (other is Range<*>) {
            return (other.start == start) && (other.end == end)
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = start?.hashCode() ?: 0
        result = 31 * result + (end?.hashCode() ?: 0)
        return result
    }

}

@JsonDeserialize(using = DateTimeRangeDeserializer::class)
@JsonSerialize(using = DateTimeRangeSerializer::class)
class DateTimeRange(override val start: LocalDateTime, override val end: LocalDateTime) :
    Range<LocalDateTime>(start, end)

@JsonDeserialize(using = DateRangeDeserializer::class)
@JsonSerialize(using = DateRangeSerializer::class)
data class DateRange(override val start: LocalDate, override val end: LocalDate) : Range<LocalDate>(start, end)

/**
 * 一天的开始时间
 */
val LocalDateTime.startOfDay: LocalDateTime get() = this.toLocalDate().atStartOfDay()

/**
 * 一天的结束时间
 */
val LocalDateTime.endOfDay: LocalDateTime get() = this.with(LocalTime.MAX)

/**
 * 一天的开始时间和结束时间范围
 */
val LocalDateTime.rangeOfDay: DateTimeRange get() = this.startOfDay.let { DateTimeRange(it, it.endOfDay) }

/**
 * 按天修正时间范围。
 *
 * 将起始时间修正为当天起始时间，结束时间修正为当天结束时间。
 *
 * @return 起始日期的 00:00:00 和 结束日期的 23:59:59。
 */
fun DateTimeRange.fixedOfDay(): DateTimeRange = DateTimeRange(start.startOfDay, end.endOfDay)


/**
 * 当月的开始时间
 */
val LocalDateTime.startOfMonth: LocalDateTime
    get() = LocalDateTime.of(this.year, this.month, 1, 0, 0, 0, 0)

/**
 * 当月的结束时间
 */
val LocalDateTime.endOfMonth: LocalDateTime
    get() = this.with(TemporalAdjusters.lastDayOfMonth()).endOfDay

/**
 * 当月的开始时间和结束时间范围
 */
val LocalDateTime.rangeOfMonth: DateTimeRange get() = this.startOfMonth.let { DateTimeRange(it, it.endOfMonth) }

/**
 * 按月修正时间范围。
 *
 * 将起始时间修正为当月起始时间，结束时间修正为当月结束时间。
 *
 * @return 当月 1 日的 00:00:00 和 当月最后一日的 23:59:59。
 */
fun DateTimeRange.fixedOfMonth(): DateTimeRange = DateTimeRange(start.startOfMonth, end.endOfMonth)

/**
 * 一天的开始时间
 */
val LocalDate.startOfDay: LocalDateTime get() = this.atStartOfDay()

/**
 * 一天的结束时间
 */
val LocalDate.endOfDay: LocalDateTime get() = LocalDateTime.of(this, LocalTime.MAX)

/**
 * 按天修正时间范围。
 *
 * 将起始时间修正为当天起始时间，结束时间修正为当天结束时间。
 *
 * @return 起始日期的 00:00:00 和 结束日期的 23:59:59。
 */
fun DateRange.fixedOfDay(): DateTimeRange = DateTimeRange(start.startOfDay, end.endOfDay)

/**
 * 当月的开始时间
 */
val LocalDate.startOfMonth: LocalDateTime get() = LocalDateTime.of(this.year, this.month, 1, 0, 0, 0, 0)

/**
 * 当月的结束时间
 */
val LocalDate.endOfMonth: LocalDateTime get() = this.startOfDay.endOfMonth

/**
 * 当月的开始时间和结束时间范围
 */
val LocalDate.rangeOfMonth: DateTimeRange get() = this.startOfMonth.let { DateTimeRange(it, it.endOfMonth) }


/**
 * 按月修正时间范围。
 *
 * 将起始时间修正为当月起始时间，结束时间修正为当月结束时间。
 *
 * @return 当月 1 日的 00:00:00 和 当月最后一日的 23:59:59。
 */
fun DateRange.fixedOfMonth(): DateTimeRange = DateTimeRange(start.startOfMonth, end.endOfMonth)
