package com.example.todo.utlis

import android.content.Context

fun getHourIn12(hour: Int): Int = if (hour > 12) hour - 12 else if (hour == 0) 12 else hour

fun getTimeAmPm(hour: Int): String = if (hour < 12) "AM" else "PM"

fun getCurrentLanguage(context: Context): String {
    val configuration = context.resources.configuration
    return configuration.locales[0].language
}

fun getFormattedTime(hour: Int, minutes: Int): String {
    val minuteString = if (minutes < 10) "0$minutes" else minutes.toString()
    return "${getHourIn12(hour)}:$minuteString ${getTimeAmPm(hour)}"
}
