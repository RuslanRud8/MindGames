package com.developer.rrd_projects.mindgames.games_statistics

class Day {

    val scores: ArrayList<Int> = ArrayList()
    var dayAvr: Int = 0

    fun updateDay(score: Int) {
        scores.add(score)

        var sum = 0

        for (i in scores.indices) {
            sum += scores[i]
        }

        dayAvr = sum / scores.size
    }


}