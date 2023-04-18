package com.daiwenzh5.kt.time

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

/**
 * 时间符号
 * @author daiwenzh5
 * @date 2023/4/19
 */
//-------------------------------------------------------------------------------------------------

/**
 * 用于标记时间方向的符号
 */
sealed interface TimeSign

@Suppress("ClassName")
object ago : TimeSign

@Suppress("ClassName")
object later : TimeSign

/**
 * n 年前（后）
 *
 * @param sign 时间符号
 * @return LocalDateTime
 */
infix fun Int.years(sign: TimeSign): LocalDateTime = this.calc(sign, ChronoUnit.YEARS)

/**
 * n 月前（后）
 *
 * @param sign 时间符号
 * @return LocalDateTime
 */
infix fun Int.months(sign: TimeSign): LocalDateTime = this.calc(sign, ChronoUnit.MONTHS)

/**
 * n 星期前（后）
 *
 * @param sign 时间符号
 * @return LocalDateTime
 */
infix fun Int.weeks(sign: TimeSign): LocalDateTime = this.calc(sign, ChronoUnit.WEEKS)

/**
 * n 天前（后）
 *
 * @param sign 时间符号
 * @return LocalDateTime
 */
infix fun Int.days(sign: TimeSign): LocalDateTime = this.calc(sign, ChronoUnit.DAYS)

/**
 * n 小时前（后）
 *
 * @param sign 时间符号
 * @return LocalDateTime
 */
infix fun Int.hours(sign: TimeSign): LocalDateTime = this.calc(sign, ChronoUnit.HOURS)

/**
 * n 分钟前（后）
 *
 * @param sign 时间符号
 * @return LocalDateTime
 */
infix fun Int.minutes(sign: TimeSign): LocalDateTime = this.calc(sign, ChronoUnit.MINUTES)

/**
 * n 秒前（后）
 *
 * @param sign 时间符号
 * @return LocalDateTime
 */
infix fun Int.seconds(sign: TimeSign): LocalDateTime = this.calc(sign, ChronoUnit.SECONDS)

/**
 * n 毫秒前（后）
 *
 * @param sign 时间符号
 * @return LocalDateTime
 */
infix fun Int.millis(sign: TimeSign): LocalDateTime = this.calc(sign, ChronoUnit.MILLIS)

private fun Int.calc(sign: TimeSign, unit: ChronoUnit): LocalDateTime {
    val func: (Long, TemporalUnit) -> LocalDateTime = if (sign is ago) now::minus else now::plus
    return func(this.toLong(), unit)
}



