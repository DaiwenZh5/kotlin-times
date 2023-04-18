package io.github.daiwenzh5.kt.time

import java.time.Duration
import java.time.LocalDateTime
import java.time.Period

/**
 * 时间量拓展
 * @author daiwenzh5
 * @date 2023/4/22
 */
//-------------------------------------------------------------------------------------------------

/**
 * n 个世纪
 */
val Int.centuries: TimeAmount
    get() = TimeAmount(this * 100, 0, 0, 0)

/**
 * n 个十年
 */
val Int.decades: TimeAmount
    get() = TimeAmount(this * 10, 0, 0, 0)

/**
 * n 年
 */
val Int.years: TimeAmount
    get() = TimeAmount(this, 0, 0, 0)


/**
 * n 个月
 */
val Int.months: TimeAmount
    get() = TimeAmount(0, this, 0, 0)

/**
 * n 个星期
 */
val Int.weeks: TimeAmount
    get() = TimeAmount(0, 0, this * 7, 0)

/**
 * n 天
 */
val Int.days: TimeAmount
    get() = TimeAmount(0, 0, this, 0)

/**
 * n 个小时
 */
val Int.hours: TimeAmount
    get() = TimeAmount(0, 0, 0, this * 3_600_000)

/**
 * n 分钟
 */
val Int.minutes: TimeAmount
    get() = TimeAmount(0, 0, 0, this * 60_000)

/**
 * n 秒
 */
val Int.seconds: TimeAmount
    get() = TimeAmount(0, 0, 0, this * 1_000)

/**
 * n 毫秒
 */
val Int.millis: TimeAmount
    get() = TimeAmount(0, 0, 0, this)

/**
 * 加法
 *
 * @param amount 时间量
 * @return 一个基于当前时间加上指定单位长度的时间
 */
operator fun LocalDateTime.plus(amount: TimeAmount): LocalDateTime =
    this + amount.period + amount.duration

/**
 * 减法
 *
 * @param amount 时间量
 * @return 一个基于当前时间减去指定单位长度的时间
 */
operator fun LocalDateTime.minus(amount: TimeAmount): LocalDateTime =
    this - amount.period - amount.duration

/**
 * 一个表示时间长度的度量值，基于 java8 [Period] 以及 [Duration]。
 * 用于为 [LocalDateTime] 提供便捷的计算。
 *
 * @property years 年数
 * @property months 月数
 * @property days 天数
 * @property milliseconds 毫秒数
 */
data class TimeAmount constructor(
    private val years: Int,
    private val months: Int,
    private val days: Int,
    private val milliseconds: Int
) {

    /**
     * 日期量
     */
    internal val period: Period by lazy {
        Period.of(years, months, days)
    }

    /**
     * 时间量
     */
    internal val duration: Duration by lazy {
        Duration.ofMillis(milliseconds.toLong())
    }

    operator fun plus(other: TimeAmount): TimeAmount = TimeAmount(
        years + other.years,
        months + other.months,
        days + other.days,
        milliseconds + other.milliseconds
    )

    operator fun minus(other: TimeAmount): TimeAmount = TimeAmount(
        years - other.years,
        months - other.months,
        days - other.days,
        milliseconds - other.milliseconds
    )

    operator fun times(scale: Int): TimeAmount = TimeAmount(
        years * scale,
        months * scale,
        days * scale,
        milliseconds * scale
    )

    /**
     * 除法
     *
     * @param scale
     * @return
     */
    operator fun div(scale: Int): TimeAmount = TimeAmount(
        years / scale,
        months / scale,
        days / scale,
        milliseconds / scale
    )


    /**
     * 根据时间量获取基于当前时间之前的值
     */
    val ago: LocalDateTime get() = now - this

    /**
     * 根据时间量获取基于当前时间之后的值
     */
    val later: LocalDateTime get() = now + this
}

