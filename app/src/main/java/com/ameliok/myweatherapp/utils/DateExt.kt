package com.ameliok.myweatherapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

// Example: "2020-06-23" (default format by API) "2022-11-23 18:00:00
val dateFormatDash = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
// Example: "02 Feb 2017, 7PM"
val dateHourFormat = SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.ENGLISH)
// Example : "9AM"
val hourFormat = SimpleDateFormat("haa", Locale.ENGLISH)

fun Date?.formatDate(formatter: DateFormat): String? = this?.let { formatter.format(it) }

fun Date?.getDaysDifference(now: Date = Calendar.getInstance().time): Long {
    if (this == null) return 0

    val nowCalendar = GregorianCalendar(TimeZone.getDefault()).apply {
        time = now
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.HOUR_OF_DAY, 0)
    }
    val specifiedCalendar = GregorianCalendar(TimeZone.getDefault()).also {
        it.time = this
        it.set(Calendar.HOUR, 0)
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.SECOND, 0)
        it.set(Calendar.MILLISECOND, 0)
        it.set(Calendar.HOUR_OF_DAY, 0)
    }

    val millisDiff = abs(nowCalendar.timeInMillis - specifiedCalendar.timeInMillis)
    val dayInMilliseconds = 24 * 60 * 60 * 1000
    return millisDiff / dayInMilliseconds
}