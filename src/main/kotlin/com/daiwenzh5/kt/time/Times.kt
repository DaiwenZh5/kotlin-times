package com.daiwenzh5.kt.time

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
 * 根据字符串获取日期格式器
 */
val String.dateFormatter: DateTimeFormatter
    get() = dataFormatterCache.computeIfAbsent(this) {
        DateTimeFormatter.ofPattern(it)
    }
