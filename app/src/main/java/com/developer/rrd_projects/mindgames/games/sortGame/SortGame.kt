package com.developer.rrd_projects.mindgames.games.sortGame

import android.os.Bundle
import android.view.View
import android.widget.*
import com.developer.rrd_projects.mindgames.R
import com.developer.rrd_projects.mindgames.animators.animateShapeToSort
import com.developer.rrd_projects.mindgames.games.GamesActivity
import com.developer.rrd_projects.mindgames.games.readGameSet
import java.util.*

class SortGame : GamesActivity() {

    private var currentType: String = ""
    private lateinit var img: ImageView

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            hideSystemUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setContentView(R.layout.activity_sort_game)

        initGame(this, "sortGame", findViewById(R.id.start_btn), findViewById(R.id.cancel_btn), findViewById(R.id.timer))

        checkAttention(readGameSet(applicationContext).sortGameAttention, "Your goal as soon as possible", "sort shapes correctly","sortGame",intent)

        val circleBtn: ImageView = findViewById(R.id.circle_view)
        circleBtn.setOnClickListener { chooseCircle() }

        val triangleBtn: ImageView = findViewById(R.id.triangle_view)
        triangleBtn.setOnClickListener { chooseTriangle() }

        val rectangleBtn: ImageView = findViewById(R.id.rectangle_view)
        rectangleBtn.setOnClickListener { chooseRectangle() }



        createPreStartTimer(findViewById(R.id.dark_screen), ::generateShapeToSort)
        createGameTimer(60000,findViewById(R.id.game_timer))

    }

    private fun chooseRectangle() {
        if (currentType == "rectangle" && gameStarted) {
            scoreUp()
        }
        animateShapeToSort(
            img,
            (getScreenWidth() + img.width).toFloat(),
            (getScreenHeight() + img.width).toFloat(),
            800
        )
        generateShapeToSort()
    }

    private fun scoreUp() {
        score += 25
        findViewById<TextView>(R.id.score_text).text = score.toString()
    }

    private fun chooseTriangle() {
        if (currentType == "triangle" && gameStarted) {
            scoreUp()
        }
        animateShapeToSort(img, img.x, (getScreenHeight() + img.width).toFloat(), 800)
        generateShapeToSort()

    }

    private fun chooseCircle() {
        if (currentType == "circle" && gameStarted) {
            scoreUp()
        }
        animateShapeToSort(img, (-1 * img.width).toFloat(), (getScreenHeight() + img.width).toFloat(), 800)
        generateShapeToSort()
    }

    private fun generateShapeToSort() {
        val layout: RelativeLayout = findViewById(R.id.rl_game)

        img = ImageView(applicationContext)

        when (Random().nextInt(3)) {
            0 -> {
                currentType = "circle"
                img.setImageDrawable(getDrawable(R.drawable.circle))
            }

            1 -> {
                currentType = "triangle"
                img.setImageDrawable(getDrawable(R.drawable.triangle))
            }
            2 -> {
                currentType = "rectangle"
                img.setImageDrawable(getDrawable(R.drawable.rectangle))
            }
        }

        layout.addView(img)

        val width = (resources.getDimension(R.dimen.dimen_sort) / 2).toInt()

        img.layoutParams.width = width
        img.layoutParams.height = width

        img.translationX = (getScreenWidth() / 2).toFloat()
        img.translationY = (-1 * width).toFloat()

        animateShapeToSort(
            img,
            (getScreenWidth() / 2.0f),
            resources.getDimension(R.dimen.fngame_height_main) + 10,
            500
        )
    }
}
