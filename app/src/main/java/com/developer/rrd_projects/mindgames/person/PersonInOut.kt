package com.developer.rrd_projects.mindgames.person

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.util.Xml
import com.developer.rrd_projects.mindgames.codeIn
import com.developer.rrd_projects.mindgames.codeOut
import org.xmlpull.v1.XmlSerializer
import java.io.*
import java.io.FileInputStream
import java.lang.Exception
import com.developer.rrd_projects.mindgames.writeToFile
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory


fun writePerson(person: Person, context: Context) {

    val personSave: SharedPreferences = context.getSharedPreferences("personSave", MODE_PRIVATE)
    val ed = personSave.edit()
    ed.putInt("games_played", person.gamesPlayed)
    ed.putString("fav_game", person.favGame)
    ed.putInt("max_exp", person.maxExp)
    ed.putInt("time_in_game", person.timeInGame)
    ed.putInt("level", person.level)
    ed.putInt("exp", person.exp)
    ed.putInt("icon", person.icon)
    ed.putString("user_name", person.userName)
    ed.apply()

}

private val person = Person(0,"-",0,0,1,0,0,"-1")

fun readPerson(context: Context): Person {

    val personSave = context.getSharedPreferences("personSave", MODE_PRIVATE)

    person.userName = personSave.getString("user_name", "-1")!!
    person.favGame = personSave.getString("fav_game", "-")!!
    person.timeInGame = personSave.getInt("time_in_game", 0)
    person.maxExp = personSave.getInt("max_exp", 0)
    person.level = personSave.getInt("level", 1)
    person.icon = personSave.getInt("icon", 0)
    person.gamesPlayed = personSave.getInt("games_played", 0)
    person.exp = personSave.getInt("exp", 0)

    return person
}
