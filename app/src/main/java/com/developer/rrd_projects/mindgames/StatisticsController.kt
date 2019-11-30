package com.developer.rrd_projects.mindgames

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.developer.rrd_projects.mindgames.animators.animateGear
import com.developer.rrd_projects.mindgames.games_statistics.Statistics
import com.developer.rrd_projects.mindgames.games_statistics.readStatistics
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class StatisticsController : AppCompatActivity() {

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            hideSystemUi()
        }
    }

    private fun hideSystemUi() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContentView(R.layout.activity_statistics)

        val fNGameImg: ImageView = findViewById(R.id.findNumGamePr)
        fNGameImg.setOnClickListener { openStat("fngame") }

        val lampsGameImg: ImageView = findViewById(R.id.pr_1)
        lampsGameImg.setOnClickListener { openStat("lampsgame") }

        val sortGameImg: ImageView = findViewById(R.id.pr_2)
        sortGameImg.setOnClickListener { openStat("sortgame") }

        val colorsGameImg: ImageView = findViewById(R.id.pr_3)
        colorsGameImg.setOnClickListener { openStat("colorsgame") }

        val anagramGameImg: ImageView = findViewById(R.id.anagram)
        anagramGameImg.setOnClickListener { openStat("anagramgame") }

        val menuBtn: Button = findViewById(R.id.menu_btn)
        menuBtn.setOnClickListener {goToMainMenu()}

        val chooseBtn: Button = findViewById(R.id.choose_game_btn)
        chooseBtn.setOnClickListener {chooseGame()}

        val gamesBtn: Button = findViewById(R.id.go_to_games_btn)
        gamesBtn.setOnClickListener {goToGames()}

        startGears()
    }

    private fun goToGames() {
        startActivity(Intent(this, Games::class.java))
        finish()
        return
    }

    private fun chooseGame() {
        hideStatChart()
        showGamesView()
        val gamesBtn: Button = findViewById(R.id.choose_game_btn)
        gamesBtn.isEnabled = false
        val alert: TextView = findViewById(R.id.alert_text)
        alert.visibility = View.GONE
    }

    private fun showGamesView() {
        val games: ScrollView = findViewById(R.id.games_preview_scroll_bar)
        games.visibility = View.VISIBLE
    }

    private fun hideStatChart() {
        val chart: LineChart = findViewById(R.id.line_chart)
        chart.visibility = View.GONE
    }

    private fun startGears(){
        animateGear(findViewById(R.id.left_blue_gear))
        animateGear(findViewById(R.id.left_green_gear))
        animateGear(findViewById(R.id.left_orange_gear))
        animateGear(findViewById(R.id.right_blue_gear))
        animateGear(findViewById(R.id.right_green_gear))
        animateGear(findViewById(R.id.right_orange_gear))
    }


    private fun goToMainMenu() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        return
    }


    private fun openStat(filename:String){
        hideGamesView()
        openStatChart()
        val chooseBtn : Button = findViewById(R.id.choose_game_btn)
        chooseBtn.isEnabled = true

        val stat: Statistics = readStatistics(applicationContext, filename)

        val dates = stat.datesList
        val scores = stat.scoresList

        val list:ArrayList<Entry> = ArrayList()

        for (i in dates.indices){
            list.add(Entry(i.toFloat(),scores[i].dayAvr.toFloat()))
        }

        val lineDataSet = LineDataSet(list, "scores")
        lineDataSet.setCircleColor(Color.BLUE)
        lineDataSet.setDrawCircles(true)
        lineDataSet.lineWidth = 3f
        lineDataSet.fillColor = Color.WHITE
        lineDataSet.color = Color.BLUE
        lineDataSet.setDrawCircleHole(true)

        val lineData = LineData(lineDataSet)

        val chart : LineChart = findViewById(R.id.line_chart)
        chart.data = lineData
        chart.background = getDrawable(R.drawable.chart_bakcground)
        chart.xAxis.setDrawLabels(false)
        chart.xAxis.axisLineColor = Color.GREEN
        chart.xAxis.axisLineWidth = 3f
        chart.axisLeft.axisLineColor = Color.GREEN
        chart.axisLeft.axisLineWidth = 3f
        chart.axisRight.isEnabled = false
        chart.description.text = "day average results"
        chart.invalidate()

        if(dates.isEmpty()){
            val alert: TextView = findViewById(R.id.alert_text)
            alert.visibility = View.VISIBLE
        }
    }


    private fun openStatChart() {
        val chart: LineChart = findViewById(R.id.line_chart)
        chart.visibility = View.VISIBLE
    }

    private fun hideGamesView() {
        val games: ScrollView = findViewById(R.id.games_preview_scroll_bar)
        games.visibility = View.GONE
    }
}
