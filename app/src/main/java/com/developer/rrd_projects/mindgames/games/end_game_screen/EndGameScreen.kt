package com.developer.rrd_projects.mindgames.games.end_game_screen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.developer.rrd_projects.mindgames.Games
import com.developer.rrd_projects.mindgames.MyGameActivity
import com.developer.rrd_projects.mindgames.games.lampsGame.LampsGame
import com.developer.rrd_projects.mindgames.R
import com.developer.rrd_projects.mindgames.animators.animateProgressBar
import com.developer.rrd_projects.mindgames.games.anagramsGame.AnagramGame
import com.developer.rrd_projects.mindgames.games.colorsGame.ColorsGame
import com.developer.rrd_projects.mindgames.games.fNGame.FindNumGame
import com.developer.rrd_projects.mindgames.games.sortGame.SortGame
import com.developer.rrd_projects.mindgames.games_statistics.readStatistics
import com.developer.rrd_projects.mindgames.games_statistics.writeStatistics
import com.developer.rrd_projects.mindgames.person.Person
import com.developer.rrd_projects.mindgames.person.getExpForLevel
import com.developer.rrd_projects.mindgames.person.readPerson
import com.developer.rrd_projects.mindgames.person.writePerson
import com.developer.rrd_projects.mindgames.playSound

class EndGameScreen : MyGameActivity() {

    var person = Person(0, "-", 0, 0, 1, 0, 0, "-1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setContentView(R.layout.activity_end_game_screen)

        showScore()
    }

    private fun showScore() {
        val score: Int = intent.getIntExtra("game_score", 0)

        findViewById<TextView>(R.id.score).text = getString(R.string.score_str, score)

        person = readPerson(applicationContext)

        val level = person.level

        person = updateScore(score, person)

        updateProgressBar(person, level)
    }

    private val gameTimer = object : CountDownTimer(1050, 1050) {


        override fun onFinish() {
            updateProgressBar(person, person.level)
        }

        override fun onTick(millisUntilFinished: Long) {

        }

    }

    private fun updateProgressBar(person: Person, level: Int) {
        val progress: ProgressBar = findViewById(R.id.end_game_level_bar)
        val percent = ((person.exp * 100) / getExpForLevel(person.level)) * 100
        val progressText: TextView = findViewById(R.id.profile_exp_text)


        if (level < person.level) {
            progressText.text = getString(
                R.string.profile_level_progress,
                getExpForLevel(person.level - 1),
                getExpForLevel(person.level - 1)
            )
            animateProgressBar(progress, 10000, progress.progress)
            gameTimer.start()
        } else {
            progressText.text = getString(R.string.profile_level_progress, person.exp, getExpForLevel(person.level))
            animateProgressBar(progress, percent, progress.progress)
        }
    }


    private fun updateScore(s: Int, person: Person): Person {

        var maxScore = 0
        var dayAvar = 0
        var exp = 0
        var maxFlag: Boolean = true
        var dayFlag: Boolean = true
        person.gamesPlayed++

        when (intent.getStringExtra("game_ended")) {

            "fngame" -> {
                val stat = readStatistics(applicationContext, "fngame")
                maxFlag = s > stat.maxScore

                if (stat.gamesPlayed > 0) {
                    dayFlag = s > stat.getAvar()
                }

                stat.updateAfterGame(s)
                maxScore = stat.maxScore
                dayAvar = stat.getAvar()


                when {
                    s > 10000 -> exp = 125
                    s > 8000 -> exp = 100
                    s > 5000 -> exp = 50
                    s > 3000 -> exp = 25
                    s < 3000 -> exp = 5
                }

                person.timeInGame += 1
                writeStatistics(stat, applicationContext, "fngame")
            }

            "sortGame" -> {
                val stat = readStatistics(applicationContext, "sortgame")

                maxFlag = s > stat.maxScore

                if (stat.gamesPlayed > 0) {
                    dayFlag = s > stat.getAvar()
                }

                stat.updateAfterGame(s)
                maxScore = stat.maxScore
                dayAvar = stat.getAvar()


                when {

                    s > 3000 -> exp = 125
                    s > 2000 -> exp = 100
                    s > 1500 -> exp = 50
                    s < 1500 -> exp = 25
                }

                person.timeInGame += 1
                writeStatistics(stat, applicationContext, "sortgame")

            }
            "lamps" -> {
                val stat = readStatistics(applicationContext, "lampsgame")
                maxFlag = s > stat.maxScore

                if (stat.gamesPlayed > 0) {
                    dayFlag = s > stat.getAvar()
                }

                stat.updateAfterGame(s)
                maxScore = stat.maxScore
                dayAvar = stat.getAvar()


                when {

                    s > 5000 -> exp = 125
                    s > 3000 -> exp = 100
                    s > 3000 -> exp = 50
                    s < 3000 -> exp = 25
                }

                person.timeInGame += 1
                writeStatistics(stat, applicationContext, "lampsgame")
            }

            "colorsGame" -> {
                val stat = readStatistics(applicationContext, "colorsgame")
                maxFlag = s > stat.maxScore

                if (stat.gamesPlayed > 0) {
                    dayFlag = s > stat.getAvar()
                }

                stat.updateAfterGame(s)
                maxScore = stat.maxScore
                dayAvar = stat.getAvar()


                when {

                    s > 5000 -> exp = 125
                    s > 3000 -> exp = 100
                    s > 3000 -> exp = 50
                    s < 3000 -> exp = 25
                }

                person.timeInGame += 1
                writeStatistics(stat, applicationContext, "colorsgame")
            }

            "anagramGame" -> {
                val stat = readStatistics(applicationContext, "anagramgame")
                maxFlag = s > stat.maxScore

                if (stat.gamesPlayed > 0) {
                    dayFlag = s > stat.getAvar()
                }

                stat.updateAfterGame(s)
                maxScore = stat.maxScore
                dayAvar = stat.getAvar()


                when {

                    s > 5000 -> exp = 125
                    s > 3000 -> exp = 100
                    s > 3000 -> exp = 50
                    s < 3000 -> exp = 25
                }

                person.timeInGame += 1
                writeStatistics(stat, applicationContext, "anagramgame")
            }
        }



        person.updateExp(exp, applicationContext)
        writePerson(person, applicationContext)
        findViewById<TextView>(R.id.max_score).text = getString(R.string.max_score_str, maxScore)
        findViewById<TextView>(R.id.day_average).text = getString(R.string.day_average_str, dayAvar)
        findViewById<TextView>(R.id.end_game_exp_text).text = getString(R.string.exp_str, exp)

        val imgMax: ImageView = findViewById(R.id.max_img_view)
        val imgDay: ImageView = findViewById(R.id.day_img_view)

        if (maxFlag) {
            imgMax.setImageDrawable(getDrawable(R.drawable.stat_up_dr))
        } else imgMax.setImageDrawable(getDrawable(R.drawable.stat_down_dr))

        if (dayFlag) {
            imgDay.setImageDrawable(getDrawable(R.drawable.stat_up_dr))
        } else imgDay.setImageDrawable(getDrawable(R.drawable.stat_down_dr))

        return person
    }

    fun goToGames(view: View) {
        playSound(this,R.raw.menu_button_sound)
        val intent = Intent(this, Games::class.java)
        startActivity(intent)
    }

    fun restartGame(view: View) {
        playSound(this,R.raw.menu_button_sound)
        var i: Intent? = null

        when (intent.getStringExtra("game_ended")) {
            "fngame" -> i = Intent(this, FindNumGame::class.java)
            "sortGame" -> i = Intent(this, SortGame::class.java)
            "lamps" -> i = Intent(this, LampsGame::class.java)
            "colorsGame" -> i = Intent(this, ColorsGame::class.java)
            "anagramGame" -> i = Intent(this, AnagramGame::class.java)
        }

        startActivity(i)
    }
}
