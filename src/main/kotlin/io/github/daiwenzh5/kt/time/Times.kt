package io.github.daiwenzh5.kt.time

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap

/**
 * 时间拓展
 * @author daiwenzh5
 * @date 2023/4/18
 */
//-------------------------------------------------------------------------------------------------


/**
 * 当前时间
 */
val now: LocalDateTime get() = LocalDateTime.now()

internal val dataFormatterCache = ConcurrentHashMap<String, DateTimeFormatter>()

/**
 * 根据字符串获取日期格式化器
 */
val String.dateFormatter: DateTimeFormatter
    get() = dataFormatterCache.computeIfAbsent(this) {
        DateTimeFormatter.ofPattern(it)
    }

/**
 * 格式化日期时间
 *
 * @param pattern 模式
 * @return 按照指定模式格式化的日期时间字符串
 */
fun LocalDateTime.format(pattern: String): String = this.format(pattern.dateFormatter)

/**
 * 格式化日期
 *
 * @param pattern 模式
 * @return 按照指定模式格式化的日期字符串
 */
fun LocalDate.format(pattern: String): String = this.format(pattern.dateFormatter)