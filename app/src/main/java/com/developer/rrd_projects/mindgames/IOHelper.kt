package com.developer.rrd_projects.mindgames

import android.content.Context
import java.io.IOException

fun writeToFile(context: Context, fileName: String, str: String) {
    try {
        val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        fos.write(str.toByteArray(), 0, str.length)
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
        println("error beach")
    }
}