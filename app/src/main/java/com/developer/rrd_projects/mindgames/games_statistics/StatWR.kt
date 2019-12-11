package com.developer.rrd_projects.mindgames.games_statistics

import android.content.Context
import android.content.SharedPreferences

fun writeStatistics(stat: Statistics, context: Context, filename: String) {

    val personSave: SharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
    val ed = personSave.edit()
    ed.putString("scores", stat.arrToStr())
    ed.putString("dates", stat.arrToStrS())
    ed.putString("current_day_scores", stat.arrToStrD())
    ed.putInt("max_score", stat.maxScore)
    ed.putInt("games_played", stat.gamesPlayed)
    ed.apply()

}


fun readStatistics(context: Context, filename: String): Statistics {

    val stat = Statistics()

    val personSave = context.getSharedPreferences(filename, Context.MODE_PRIVATE)

    stat.setScoresList(personSave.getString("scores"," ")!!)
    stat.setCurrentDateScores(personSave.getString("current_day_scores"," ")!!)
    stat.setDatesList(personSave.getString("dates"," ")!!)
    stat.maxScore = personSave.getInt("max_score",0)
    stat.gamesPlayed = personSave.getInt("games_played",0)

    return stat
}