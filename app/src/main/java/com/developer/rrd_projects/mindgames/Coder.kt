package com.developer.rrd_projects.mindgames

import android.util.Log

fun codeOut(str: String): String {

    val res = StringBuffer()

    res.append(str.toByteArray())

    Log.i("AAAAARESOUT", res.toString())


    return str
}

fun codeIn(str: String): String {
    val res = StringBuffer()

    str.toString()

    Log.i("AAAAARESIN", res.toString())


    return str
}