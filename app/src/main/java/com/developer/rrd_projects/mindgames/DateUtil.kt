package com.developer.rrd_projects.mindgames

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getCurrentDateStr() : String{
    val currentDate = Date()

    val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    return dateFormat.format(currentDate)
}


