package com.developer.rrd_projects.games.colorsGame

import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.developer.rrd_projects.R
import com.developer.rrd_projects.games.GamesActivity
import com.developer.rrd_projects.games.readGameSet
import com.developer.rrd_projects.playSound
import java.util.*
import kotlin.collections.ArrayList

class ColorsGame : GamesActivity() {

    private val rnd = Random()
    private var currentType = false
    private lateinit var scoreText: TextView
    private lateinit var wrongBtn: TextView
    private lateinit var correctBtn: TextView
    private lateinit var textUp: TextView
    private lateinit var textBottom: TextView
    private val arrColors: ArrayList<Int> = ArrayList()
    private val arrNameColors: ArrayList<String> =
        arrayListOf("red", "blue", "green", "yellow", "orange", "purple", "pink", "white")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setContentView(R.layout.activity_colors_game)

        initGame(this,"colorsGame", findViewById(R.id.start_btn), findViewById(R.id.cancel_btn), findViewById(R.id.timer), findViewById(R.id.game_timer))

        checkAttention(readGameSet(applicationContext).fNGameAttention,"Answer the question: \" does the color name on top","matches the text color on the bottom \" \n by clicking on answer buttons","colorsGame",intent)

        createPreStartTimer(findViewById(R.id.dark_screen), ::generateColor)
        createGameTimer(90)

        initColors()

        textUp = findViewById(R.id.text_name_color)
        textBottom = findViewById(R.id.text_text_color)

        scoreText = findViewById(R.id.score_text)

        wrongBtn = findViewById(R.id.wrong_btn)
        wrongBtn.setOnClickListener { ansFalse() }
        correctBtn = findViewById(R.id.correct_btn)
        correctBtn.setOnClickListener { ansTrue() }

        val buttonWidth = getHeight()/3
        wrongBtn.layoutParams.height = buttonWidth
        wrongBtn.layoutParams.width = buttonWidth

        correctBtn.layoutParams.height = buttonWidth
        correctBtn.layoutParams.width = buttonWidth
    }

    private fun getHeight():Int{
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return  size.y
    }

    private fun initColors() {
        arrColors.add(ContextCompat.getColor(this,R.color.red))
        arrColors.add(ContextCompat.getColor(this,R.color.blue))
        arrColors.add(ContextCompat.getColor(this,R.color.green))
        arrColors.add(ContextCompat.getColor(this,R.color.yellow))
        arrColors.add(ContextCompat.getColor(this,R.color.orange))
        arrColors.add(ContextCompat.getColor(this,R.color.purple))
        arrColors.add(ContextCompat.getColor(this,R.color.pink))
        arrColors.add(ContextCompat.getColor(this,R.color.white))
    }

    private fun ansTrue() {
        if (currentType) {
            addScore()
        } else minusScore()
        generateColor()
    }

    private fun minusScore() {
        playSound(this, R.raw.error_sound)
        if (score >= 60)
            score -= 60
        else score = 0

        scoreText.text = score.toString()
    }

    private fun addScore() {
        playSound(this, R.raw.success)
        score += 50
        scoreText.text = score.toString()
    }

    private fun ansFalse() {
        if (!currentType) {
            addScore()
        } else minusScore()
        generateColor()
    }

    private fun generateColor() {
        when (rnd.nextInt(2)) {
            0 -> createTrueSample()
            1 -> createFalseSample()
        }
    }

    private fun createFalseSample() {
        val numColor = rnd.nextInt(8)
        textUp.text = arrNameColors[numColor]
        textUp.setTextColor(arrColors[rnd.nextInt(8)])

        textBottom.text = arrNameColors[rnd.nextInt(8)]

        var another = rnd.nextInt(8)

        while (another == numColor) another = rnd.nextInt(8)

        textBottom.setTextColor(arrColors[another])

        currentType = false
    }

    private fun createTrueSample() {
        val numColor = rnd.nextInt(8)
        textUp.text = arrNameColors[numColor]
        textUp.setTextColor(arrColors[rnd.nextInt(8)])

        textBottom.text = arrNameColors[rnd.nextInt(8)]
        textBottom.setTextColor(arrColors[numColor])

        currentType = true
    }
}
