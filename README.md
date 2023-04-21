# kotlin-times

[![Publish](https://github.com/DaiwenZh5/kotlin-times/actions/workflows/publish.yml/badge.svg)](https://github.com/DaiwenZh5/kotlin-times/actions/workflows/publish.yml)

基于 kotlin 的时间类型拓展工具。

## 使用

### 一、时间量

为了方便计算，引入 `TimeAmount` 类型用于衡量日期或时间的长度。

并对 Int 类型提供拓展属性以实现类似于单位的概念，如 `3.minutes`
表示三分钟。

```kotlin
// 两年半
val twoAndAHalfYear: TimeAmount = 2.years + 6.months

// 一天前
val oneDayAgo: LocalDateTime = now - 1.days
// 或
val oneDayAgo: LocalDateTime = 1.days.ago

// 三个月零五天后
val anyTime: LocalDateTime = now + 3.months + 5.days

```

### 二、时间范围

在前端组件中，时间范围选择器通常是返回一个数组对象，包含起始时间、结束时间。

`TimeRange` 类型是对此类数据的封装，在接口传入参数定义时使用，可直接通过 `[startTime, endTime]` 此类数据（基于 Jackson 的）反序列化。

### 三、时间符号

`TimeSign` 是用于描述时间前后的接口类型，其有且仅有两个类型实例 `object ago` 以及 `object later`。
结合 Kotlin 的拓展方法以及中缀函数，提供更强的可读性。

```kotlin
import java.time.LocalDateTime

// 一年前
val oneYearAgo: LocalDateTime = 1 years ago

// 三天后
val threeDaysLater: LocalDateTime = 3 days later
```
### 四、其他

```kotlin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 全局属性 now 
val now: LocalDateTime get() = LocalDateTime.now()

// 字符串格式化模板，其中会对相同字符串缓存
val formatter: DateTimeFormatter = "yyyy-MM-dd HH:mm:ss".dateFormatter
```
### 内置拓展说明
| 属性名       | 描述 | TimeAmount | TimeSign |  
|-----------|----|:----------:|:--------:|
| centuries | 世纪 |     √      |    ×     |
| decades   | 十年 |     √      |    ×     |
| years     | 年  |     √      |    √     |
| months    | 月  |     √      |    √     |
| weeks     | 星期 |     √      |    √     |
| days      | 日  |     √      |    √     |
| hours     | 时  |     √      |    √     |
| minutes   | 分  |     √      |    √     |
| seconds   | 秒  |     √      |    √     |
| millis    | 毫秒 |     √      |    √     |