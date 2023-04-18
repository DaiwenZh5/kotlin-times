package com.daiwenzh5.kt.time

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

/**
 * 时间偏移量
 * @author daiwenzh5
 * @date 2023/4/19
 */
//-------------------------------------------------------------------------------------------------

/**
 * 用于记录时间偏移值以及单位的数据类型
 *
 * @property value 偏移量
 * @property unit 单位
 */
data class TimeOffset(val value: Int, val unit: TemporalUnit)

/**
 * n 世纪
 */
val Int.CENTURIES: TimeOffset
    get() = TimeOffset(this, ChronoUnit.CENTURIES)

/**
 * n 个十年
 */
val Int.DECADES: TimeOffset
    get() = TimeOffset(this, ChronoUnit.DECADES)

/**
 * n 年
 */
val Int.YEARS: TimeOffset
    get() = TimeOffset(this, ChronoUnit.YEARS)

/**
 * n 月
 */
val Int.MONTHS: TimeOffset
    get() = TimeOffset(this, ChronoUnit.MONTHS)

/**
 * n 星期
 */
val Int.WEEKS: TimeOffset
    get() = TimeOffset(this, ChronoUnit.WEEKS)

/**
 * n 天
 */
val Int.DAYS: TimeOffset
    get() = TimeOffset(this, ChronoUnit.DAYS)

/**
 * n 小时
 */
val Int.HOURS: TimeOffset
    get() = TimeOffset(this, ChronoUnit.HOURS)

/**
 * n 分钟
 */
val Int.MINUTES: TimeOffset
    get() = TimeOffset(this, ChronoUnit.MINUTES)

/**
 * n 秒
 */
val Int.SECONDS: TimeOffset
    get() = TimeOffset(this, ChronoUnit.SECONDS)

/**
 * n 毫秒
 */
val Int.MILLIS: TimeOffset
    get() = TimeOffset(this, ChronoUnit.MILLIS)

/**
 *
 * 乘法
 * @param times 倍数
 */
operator fun TimeOffset.times(times: Int) = TimeOffset(value * times, unit)

/**
 * 减法
 *
 * @param timeOffset 偏移时间
 * @return 一个基于当前时间减去指定单位长度的时间
 */
operator fun LocalDateTime.minus(timeOffset: TimeOffset): LocalDateTime =
    this.minus(timeOffset.value.toLong(), timeOffset.unit)

/**
 * 加法
 *
 * @param timeOffset 偏移时间
 * @return 一个基于当前时间加上指定单位长度的时间
 */
operator fun LocalDateTime.plus(timeOffset: TimeOffset): LocalDateTime =
    this.plus(timeOffset.value.toLong(), timeOffset.unit)

/**
 * 根据偏移时间获取基于当前时间之前的值
 */
val TimeOffset.ago: LocalDateTime get() = now.minus(value.toLong(), unit)

/**
 * 根据偏移时间获取基于当前时间之后的值
 */
val TimeOffset.later: LocalDateTime get() = now.plus(value.toLong(), unit)