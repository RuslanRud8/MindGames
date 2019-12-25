package com.developer.rrd_projects.games_statistics

import com.developer.rrd_projects.getCurrentDateStr
import java.lang.StringBuilder

class Statistics {
    var scoresList: ArrayList<Day> = ArrayList()
    var datesList: ArrayList<String> = ArrayList()

    var maxScore: Int = 0
    var gamesPlayed:Int = 0


    fun updateAfterGame(score:Int){
        if(score > maxScore){
            maxScore = score
        }


        val date = getCurrentDateStr()

        if( datesList.isEmpty() || date != datesList.last()){
            datesList.add(date)
            scoresList.add(Day())
        }

        scoresList.last().updateDay(score)

        gamesPlayed++
    }

    fun getAvar() : Int{
       return scoresList.last().dayAvr
    }

    fun arrToStr(): String {
        val res = StringBuilder()

        for (i in scoresList) {

            res.append(i.dayAvr.toString())
            res.append(" ")
        }
        res.deleteCharAt(res.length - 1)

        return res.toString()
    }

    fun arrToStrD(): String {
        val res = StringBuilder()

        for (i in scoresList.last().scores) {

            res.append(i.toString())
            res.append(" ")
        }
        res.deleteCharAt(res.length - 1)

        return res.toString()
    }

    fun arrToStrS(): String {
        val res = StringBuilder()

        for (i in datesList) {

            res.append(i)
            res.append(" ")
        }
        res.deleteCharAt(res.length - 1)

        return res.toString()
    }

    fun setCurrentDateScores(str:String){

        if(str != " ") {
            val s: List<String> = str.split(" ")

            for (i in s.indices) {
                scoresList.last().scores.add(s[i].toInt())
            }
        }
    }

    fun setScoresList(str: String){
        if(str != " ") {

            val s: List<String> = str.split(" ")

            for (i in s.indices) {
                scoresList.add(Day())
                scoresList[i].dayAvr = s[i].toInt()
            }
        }
    }

    fun setDatesList(str:String){
        if(str != " ") {
            val s: List<String> = str.split(" ")

            for (i in s.indices) {
                datesList.add(s[i])
            }
        }
    }

}