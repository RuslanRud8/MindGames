package com.developer.rrd_projects.mindgames.person

import android.content.Context
import com.developer.rrd_projects.mindgames.games_statistics.readStatistics

class Person(
    var gamesPlayed: Int,
    var favGame: String,
    var maxExp: Int,
    var timeInGame: Int,
    var level: Int,
    var exp: Int,
    var icon: Int,
    var userName: String

){

    internal fun updateExp(e:Int, context: Context){
        if(exp + e >= getExpForLevel(level)){
            startNewLevel(e)
        }else exp+=e

        if(e > maxExp){
            maxExp = e
        }

        var max = 0

        var stat = readStatistics(context, "fngame")
        if(stat.gamesPlayed > max){
            favGame = "find number game"
            max = stat.gamesPlayed
        }

        stat = readStatistics(context, "lampsgame")
        if(stat.gamesPlayed > max){
            favGame = "lamps game"
            max = stat.gamesPlayed
        }

        stat = readStatistics(context, "sortgame")
        if(stat.gamesPlayed > max){
            favGame = "sort game"
            max = stat.gamesPlayed
        }

        stat = readStatistics(context, "colorsgame")
        if(stat.gamesPlayed > max){
            favGame = "colors game"
            max = stat.gamesPlayed
        }

        stat = readStatistics(context, "anagramgame")
        if(stat.gamesPlayed > max){
            favGame = "anagram game"
            max = stat.gamesPlayed
        }


    }

    private fun startNewLevel(e: Int) {
        exp = e - (getExpForLevel(level)-exp)
        level++
    }
}