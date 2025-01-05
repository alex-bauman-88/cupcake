package com.example.cupcake.test

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getFormattedDate(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, 1)
    val formatter = SimpleDateFormat("EEE MMM d", Locale.getDefault())
    return formatter.format(calendar.time)
}