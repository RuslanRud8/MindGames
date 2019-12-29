package com.developer.rrd_projects

import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.developer.rrd_projects.animators.animateGear
import com.developer.rrd_projects.games_statistics.Statistics
import com.developer.rrd_projects.games_statistics.readStatistics
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class StatisticsController : MyGameActivity() {

    private var statOpened = false
    private lateinit var menuBtn:Button
    private lateinit var gamesBtn:Button
    var previews: ArrayList<ImageView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContentView(R.layout.activity_statistics)

        val fNGameImg: ImageView = findViewById(R.id.findNumGamePr)
        fNGameImg.setOnClickListener { openStat("fngame") }
        previews.add(fNGameImg)

        val lampsGameImg: ImageView = findViewById(R.id.pr_1)
        lampsGameImg.setOnClickListener { openStat("lampsgame") }
        previews.add(lampsGameImg)

        val sortGameImg: ImageView = findViewById(R.id.pr_2)
        sortGameImg.setOnClickListener { openStat("sortgame") }
        previews.add(sortGameImg)

        val colorsGameImg: ImageView = findViewById(R.id.pr_3)
        colorsGameImg.setOnClickListener { openStat("colorsgame") }
        previews.add(colorsGameImg)

        val anagramGameImg: ImageView = findViewById(R.id.anagram)
        anagramGameImg.setOnClickListener { openStat("anagramgame") }
        previews.add(anagramGameImg)

        setWidthForImages()

        menuBtn = findViewById(R.id.menu_btn)
        menuBtn.setOnClickListener {goToMainMenu()}

        gamesBtn = findViewById(R.id.go_to_games_btn)
        gamesBtn.setOnClickListener {goToGames()}
    }

    private fun setWidthForImages() {
        val totalWidth = getScreenWidth()*0.9
        val width:Int = (totalWidth / 4).toInt()

        var i = 0
        while (i <5){
            previews[i].layoutParams.width = width
            previews[i].layoutParams.height = (width*0.63).toInt()
            i++
        }

    }

    private fun goToGames() {
        playSound(this, R.raw.menu_button_sound)
        if(!statOpened) {
            startActivity(Intent(this, Games::class.java))
            finish()
        }else {
            statOpened = false
            gamesBtn.text = getString(R.string.games_str)
            hideStatChart()
            showGamesView()
            val alert: TextView = findViewById(R.id.alert_text)
            alert.visibility = View.GONE
        }
    }

    private fun showGamesView() {
        val games: ScrollView = findViewById(R.id.games_preview_scroll_bar)
        games.visibility = View.VISIBLE
    }

    private fun hideStatChart() {
        val chart: LineChart = findViewById(R.id.line_chart)
        chart.visibility = View.GONE
    }

    private fun goToMainMenu() {
        playSound(this,R.raw.menu_button_sound)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun openStat(filename:String){
        playSound(this,R.raw.menu_button_sound)
        hideGamesView()
        openStatChart()

        gamesBtn.text = getString(R.string.back_btn_str)
        statOpened = true

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
        chart.xAxis.setDrawLabels(true)
        chart.xAxis.axisLineColor = Color.GREEN
        chart.xAxis.axisLineWidth = 3f
        chart.axisLeft.axisLineColor = Color.GREEN
        chart.axisLeft.axisLineWidth = 3f
        chart.axisRight.isEnabled = true
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

    private fun getScreenWidth(): Int {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }
}
