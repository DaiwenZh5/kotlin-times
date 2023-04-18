package com.daiwenzh5.kt.time.json

import com.daiwenzh5.kt.time.DateRange
import com.daiwenzh5.kt.time.DateTimeRange
import com.daiwenzh5.kt.time.Range
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 基于 Jackson 的 TimeRange 拓展
 *
 * @author daiwenzh5
 * @date 2023/4/19
 */
// ================================================================================================

/**
 * 日期时间范围
 */
abstract class RangeDeserializer<T, R : Range<T>> : JsonDeserializer<R>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): R {
        val array = p.readValueAs(getType())
        if (array.isEmpty()) {
            throw TimeConvertException("Range must contain at least one value!")
        }
        return create(array[0], if (array.size > 1) array[1] else array[0])
    }

    abstract fun getType(): Class<Array<T>>

    abstract fun create(start: T, end: T): R
}

class DateTimeRangeDeserializer : RangeDeserializer<LocalDateTime, DateTimeRange>() {
    override fun getType(): Class<Array<LocalDateTime>> = Array<LocalDateTime>::class.java

    override fun create(start: LocalDateTime, end: LocalDateTime): DateTimeRange = DateTimeRange(start, end)
}


abstract class RangeSerializer<T> : JsonSerializer<Range<T>>() {

    abstract val type: Class<T>
    override fun serialize(value: Range<T>?, gen: JsonGenerator, provider: SerializerProvider) {
        value?.apply {
            provider.findValueSerializer(type)?.let {
                gen.writeStartArray()
                it.serialize(start, gen, provider)
                it.serialize(end, gen, provider)
                gen.writeEndArray()
            } ?: gen.writeString(toString())
        }
    }
}

class DateTimeRangeSerializer : RangeSerializer<LocalDateTime>() {
    override val type: Class<LocalDateTime>
        get() = LocalDateTime::class.java

}

class DateRangeSerializer : RangeSerializer<LocalDate>() {
    override val type: Class<LocalDate>
        get() = LocalDate::class.java

}

class DateRangeDeserializer : RangeDeserializer<LocalDate, DateRange>() {
    override fun getType(): Class<Array<LocalDate>> = Array<LocalDate>::class.java

    override fun create(start: LocalDate, end: LocalDate): DateRange = DateRange(start, end)

}