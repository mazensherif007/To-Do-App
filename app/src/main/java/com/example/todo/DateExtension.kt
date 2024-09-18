package com.example.todo

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MILLISECOND
import java.util.Calendar.MINUTE
import java.util.Calendar.MONTH
import java.util.Calendar.SECOND
import java.util.Calendar.YEAR
import java.util.Locale

fun Calendar.ignoreTime(){
    set(Calendar.HOUR_OF_DAY,0)
    set(Calendar.MINUTE,0)
    set(Calendar.SECOND,0)
    set(Calendar.MILLISECOND,0)
}
fun Calendar.ignoreDate(){
    set(Calendar.DAY_OF_MONTH,0)
    set(Calendar.MONTH,0)
    set(Calendar.YEAR,0)
}
fun Calendar.setCurrentDate(year:Int,month:Int,day:Int){
    set(Calendar.YEAR,year)
    set(Calendar.MONTH,month)
    set(Calendar.DAY_OF_MONTH,day)
}
fun Calendar.setCurrentTime(hour:Int,minute:Int){
    set(Calendar.MINUTE,minute)
    set(Calendar.HOUR_OF_DAY,hour)
}
fun Calendar.formatDateOnly():String{
    val dateFormat = SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH)
    return dateFormat.format(time)
}
fun Calendar.formatTimeOnly():String{
    val dateFormat = SimpleDateFormat("HH:mm a", Locale.ENGLISH)
    return dateFormat.format(time)
}
fun Long.formatTimeOnly():String{
    val dateFormat = SimpleDateFormat("HH:mm a", Locale.ENGLISH)
    return dateFormat.format(this)
}
fun Calendar.clearTime() {
    set(HOUR_OF_DAY, 0)
    set(MINUTE, 0)
    set(SECOND, 0)
    set(MILLISECOND, 0)
}

fun Calendar.clearDate() {
    set(YEAR, 0)
    set(MONTH, 0)
    set(DAY_OF_MONTH, 0)
}
