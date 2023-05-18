package io.github.daiwenzh5.kt.time.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import io.github.daiwenzh5.kt.time.DateRange
import io.github.daiwenzh5.kt.time.DateTimeTimeRange
import io.github.daiwenzh5.kt.time.TimeRange
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
abstract class RangeDeserializer<T, R : TimeRange<T>> : JsonDeserializer<R>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): R? {
        val array = p.readValueAs(getType())
        if (array.isEmpty()) {
            return null
        }
        return create(array[0], if (array.size > 1) array[1] else array[0])
    }

    abstract fun getType(): Class<Array<T>>

    abstract fun create(start: T, end: T): R
}

class DateTimeRangeDeserializer : RangeDeserializer<LocalDateTime, DateTimeTimeRange>() {
    override fun getType(): Class<Array<LocalDateTime>> = Array<LocalDateTime>::class.java

    override fun create(start: LocalDateTime, end: LocalDateTime): DateTimeTimeRange = DateTimeTimeRange(start, end)
}


abstract class RangeSerializer<T> : JsonSerializer<TimeRange<T>>() {

    abstract val type: Class<T>
    override fun serialize(value: TimeRange<T>?, gen: JsonGenerator, provider: SerializerProvider) {
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