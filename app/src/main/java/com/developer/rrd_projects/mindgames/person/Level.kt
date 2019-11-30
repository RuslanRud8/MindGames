package com.developer.rrd_projects.mindgames.person

import kotlin.math.pow

val arr : Array<Int> = arrayOf(1,2,3,4,5,5,6,7,8,9,10,10,11,12,13,14,15,15,15,16,17,18,19,20,20,20,20,21,21,21,22,22,22)
const val Q = 1.3
const val B1 = 100

fun getLevel(level :Int):Int{
    return if(level >= 34) 23
    else arr[level-1]
}

fun getExpForLevel(level:Int) :Int{
    return (B1* (Q.pow((level - 1).toDouble()))).toInt()
}