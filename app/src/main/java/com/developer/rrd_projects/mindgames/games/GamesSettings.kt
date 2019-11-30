package com.developer.rrd_projects.mindgames.games

import android.content.Context
import android.content.SharedPreferences
import android.util.Xml
import com.developer.rrd_projects.mindgames.codeIn
import com.developer.rrd_projects.mindgames.codeOut
import com.developer.rrd_projects.mindgames.writeToFile
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import org.xmlpull.v1.XmlSerializer
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.StringWriter
import java.lang.Exception

fun writeGameSet(set: GamesSet, context: Context) {

    val personSave: SharedPreferences = context.getSharedPreferences("games_settings", Context.MODE_PRIVATE)
    val ed = personSave.edit()
    ed.putBoolean("f_n_game", set.fNGameAttention)
    ed.putBoolean("sort_game", set.sortGameAttention)
    ed.putBoolean("lamps_game", set.lampsGameAttention)
    ed.putBoolean("colors_game", set.colorsGameAttention)
    ed.putBoolean("anagram_game", set.angramsGameAttention)
    ed.putBoolean("math_game", set.mathGameAttention)
    ed.putBoolean("f_i_game", set.findItemGameAttention)
    ed.putBoolean("nums_game", set.numsGameAttention)
    ed.putBoolean("buttons_animations", set.buttonsAnimation)
    ed.putBoolean("alarm", set.alarmMode)
    ed.putInt("time", set.time)
    ed.apply()

}

fun readGameSet(context: Context): GamesSet {
    val gamesSet = GamesSet()

    val personSave = context.getSharedPreferences("games_settings", Context.MODE_PRIVATE)

    gamesSet.fNGameAttention = personSave.getBoolean("f_n_game", true)
    gamesSet.sortGameAttention = personSave.getBoolean("sort_game", true)
    gamesSet.lampsGameAttention = personSave.getBoolean("lamps_game", true)
    gamesSet.colorsGameAttention = personSave.getBoolean("colors_game", true)
    gamesSet.angramsGameAttention = personSave.getBoolean("anagram_game", true)
    gamesSet.mathGameAttention = personSave.getBoolean("math_game", true)
    gamesSet.findItemGameAttention = personSave.getBoolean("f_i_game", true)
    gamesSet.numsGameAttention = personSave.getBoolean("nums_game", true)
    gamesSet.buttonsAnimation = personSave.getBoolean("buttons_animations", true)
    gamesSet.alarmMode = personSave.getBoolean("alarm", true)
    gamesSet.time = personSave.getInt("time",720)

    return gamesSet

}