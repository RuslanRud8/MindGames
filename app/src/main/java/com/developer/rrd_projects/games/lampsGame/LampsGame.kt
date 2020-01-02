package com.developer.rrd_projects.games.lampsGame

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.developer.rrd_projects.R
import com.developer.rrd_projects.games.GamesActivity
import com.developer.rrd_projects.games.readGameSet
import com.developer.rrd_projects.playSound
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class LampsGame : GamesActivity() {

    private val lampsArray: ArrayList<ImageView> = ArrayList()
    private val arrToShow: ArrayList<Int> = ArrayList()
    private var showing: Boolean = false
    private var numCurrent: Int = 0
    private  val scoreM = 123.4567
    private var  wrongAns : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setContentView(R.layout.activity_lamps_game)

        initGame(this, "lamps", findViewById(R.id.start_btn), findViewById(R.id.cancel_btn), findViewById(R.id.timer), findViewById(R.id.game_timer))

        checkAttention(readGameSet(applicationContext).lampsGameAttention,"You need to repeat the","flash order as soon as possible","lamps",intent)

        createPreStartTimer(findViewById(R.id.dark_screen), ::generateLamps)
        createGameTimer(90)

        initLamps()
    }

    private fun initLamps() {
        lampsArray.add(findViewById(R.id.lamp_1))
        lampsArray.add(findViewById(R.id.lamp_2))
        lampsArray.add(findViewById(R.id.lamp_3))
        lampsArray.add(findViewById(R.id.lamp_4))
        lampsArray.add(findViewById(R.id.lamp_5))
        lampsArray.add(findViewById(R.id.lamp_6))
        lampsArray.add(findViewById(R.id.lamp_7))
        lampsArray.add(findViewById(R.id.lamp_8))
        lampsArray.add(findViewById(R.id.lamp_9))
        lampsArray.add(findViewById(R.id.lamp_10))
        lampsArray.add(findViewById(R.id.lamp_11))
        lampsArray.add(findViewById(R.id.lamp_12))
        lampsArray.add(findViewById(R.id.lamp_13))
        lampsArray.add(findViewById(R.id.lamp_14))
        lampsArray.add(findViewById(R.id.lamp_15))

        lampsArray[0].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[1].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[2].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[3].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[4].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[5].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[6].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[7].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[8].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[9].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[10].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[11].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[12].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[13].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }
        lampsArray[14].setOnClickListener { lamp -> checkLamp(lamp as ImageView) }

    }

    private fun checkLamp(lamp: ImageView) {
        if (!wrongAns && !showing && gameStarted) {

            if (arrToShow[numCurrent] == lampsArray.indexOf(lamp)) {
                trueAns(lamp)

                if (numCurrent + 1 == arrToShow.size) {
                    if (level < 15) {
                        level++
                    }
                    playSound(this, R.raw.lamp_enabled)

                    MainScope().launch { startNewLampsShow() }

                } else {
                    numCurrent++
                    playSound(this, R.raw.lamp_enabled)
                }

            } else falseAns(lamp)

        }
    }

    private suspend fun startNewLampsShow(){
        showing = true
        delay(100L)
        playSound(this, R.raw.success)
        delay(900L)
        score += (scoreM * arrToShow.size).toInt()
        updateScore()
        generateLamps()
    }

    private fun falseAns(lamp: ImageView) {
        playSound(this,R.raw.error_sound)
        wrongAns = true
        if (level > 1) {
            level--
        }
        lamp.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.lamp_red))

//        Handler().postDelayed( {
//            //anything you want to start after 1s
//            wrongAns = false
//            generateLamps()
//        }, 1000)

        MainScope().launch { delayWrongAns() }
    }

     private suspend fun delayWrongAns(){
        delay(1500L)
        wrongAns = false
        generateLamps()
    }

    private fun trueAns(lamp: ImageView) {

        lamp.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.lamp_green))
    }

    private fun updateScore() {
        findViewById<TextView>(R.id.score_text).text = score.toString()
    }

    private fun updateLamp() {
        for (i in lampsArray) i.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.lamp_gray))
    }

    private fun generateLamps() {
        updateLamp()

        numCurrent = 0
        showing = true

        if(arrToShow.isNotEmpty()) {
            arrToShow.clear()
        }

        val rnd = Random()

        var i = 0
        while (i < level + 2) {

            var temp = rnd.nextInt(15)

            while (arrToShow.contains(temp)) {temp = rnd.nextInt(15)}

            arrToShow.add(temp)

            i++
        }

        MainScope().launch { showLamps() }

        Log.i("AAAAAAAAAA", "start")
    }

    private suspend fun showLamps(){

        for (i in  arrToShow) {
            lampsArray[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.lamp_yellow))
            delay(1000L)
            lampsArray[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.lamp_gray))
        }

        showing = false
    }
}
