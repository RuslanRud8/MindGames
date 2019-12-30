package com.developer.rrd_projects

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getCurrentDateStr() : String{
    val currentDate = Date()

    val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) as DateFormat

    return dateFormat.format(currentDate)
}


