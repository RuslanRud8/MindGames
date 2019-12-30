package com.developer.rrd_projects.games.fNGame

import android.os.Bundle
import android.view.View
import android.widget.*
import com.developer.rrd_projects.R
import com.developer.rrd_projects.games.GamesActivity
import com.developer.rrd_projects.games.readGameSet
import com.developer.rrd_projects.playSound
import java.util.*
import kotlin.collections.ArrayList

class FindNumGame : GamesActivity() {

    private val buttons: ArrayList<GameButton> = ArrayList()
    private var numToFind: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setContentView(R.layout.activity_find_num_game)

        initGame(this,"fngame", findViewById(R.id.start_btn), findViewById(R.id.cancel_btn), findViewById(R.id.timer), findViewById(R.id.game_timer))

        checkAttention(readGameSet(applicationContext).fNGameAttention,"You need to find given","numbers as soon as possible","fNGame",intent)

        createPreStartTimer(findViewById(R.id.dark_screen), ::generateButtons)
        createGameTimer(60)
    }

    private fun generateButtons() {
        when (level) {
            1 -> generateFirstLevel(6, 2, 3, 0, 100, 50F, 0)
            2 -> generateFirstLevel(8, 2, 4, 50, 200, 40F,2)
            3 -> generateFirstLevel(8, 2, 4, 50, 200, 40F,4)
            4 -> generateFirstLevel(12, 3, 4, 200, 200, 40F, 6)
            5 -> generateFirstLevel(12, 3, 4, 200, 200, 40F, 8)
            6 -> generateFirstLevel(12, 3, 4, 400, 100, 35F, 8)
            7 -> generateFirstLevel(12, 3, 4, 400, 100, 35F,10)
            8 -> generateFirstLevel(16, 4, 4, 800, 100, 35F, 12)
            9 -> generateFirstLevel(16, 4, 4, 800, 100, 35F, 14)
            10 -> generateFirstLevel(20, 4, 5, 1000, 100, 30F ,18)
            11 -> generateFirstLevel(20, 4, 5, 1000, 100, 30F, 24)
            12 -> generateFirstLevel(25, 5, 5, 1500, 100, 25F,24)
            13 -> generateFirstLevel(25, 5, 5, 2500, 100, 25F,24)
            14 -> generateFirstLevel(30, 5, 6, 4900, 200, 22F,30)
            15 -> generateFirstLevel(30, 5, 6, 4900, 200, 22F,30)
            16 -> generateFirstLevel(36, 6, 6, 8000, 50, 22F,36)
            17 -> generateFirstLevel(36, 6, 6, 8000, 50, 22F,36)
        }
    }

    private fun generateFirstLevel(
        allNum: Int,
        numRow: Int,
        numColumn: Int,
        bottomBound: Int,
        num: Int,
        textSize: Float,
        numOfAnimations:Int
    ) {
        val rnd = Random()
        numToFind = rnd.nextInt(num) + bottomBound

        val numOfTypeAnim = numOfAnimations/2
        var nubOfScaleAnim = 0
        var namOfRotateAims = 0

        val rl: RelativeLayout = findViewById(R.id.rl_game)
        val timer: TextView = findViewById(R.id.timer)

        val numText: TextView = findViewById(R.id.num_text)
        val scoreText :TextView = findViewById(R.id.score_text)
        var marginTop: Float = (numText.height) * 1.1.toFloat()

        val totalWidth = (getScreenWidth() * 0.8) // width of the screen without empty space between buttons

        val btnHeight: Int = ((getTotalHeight() / numRow) * 0.8).toInt()

       val btnWidth = (totalWidth / numColumn).toInt()

        val yTemp: Int = ((getTotalHeight() / numRow))

        var x1 = ((getScreenWidth() *0.2) / (numColumn+1)).toFloat()

        val xTemp = x1 + btnWidth
        val tx1 = x1

        var i = 0
        var l = 0
        while (l < allNum) {
            val btn = GameButton(x1, marginTop, btnHeight, btnWidth, applicationContext, numToSearch(), textSize)
            buttons.add(btn)
            btn.btn.setOnClickListener {

                if(btn.btn.text == numToFind.toString()){
                    playSound(this,R.raw.success)

                    if(level < 17) {
                        level++
                    }
                    val time = ((timer.text).toString().toInt())
                    score += (60-(lastSecond - time))*level
                    lastSecond = time

                    scoreText.text = score.toString()
                }else {
                    playSound(this,R.raw.error_sound)
                    if(level > 1){
                        level--
                    }
                }
                removeBtns()
                generateButtons()
            }
            x1 += xTemp
            rl.addView(btn.btn)
            if (i == (numColumn - 1)) {
                marginTop += yTemp
                x1 = tx1
                i = 0
            } else i++
            l++
        }

        buttons[rnd.nextInt(buttons.size)].btn.text = numToFind.toString()
        numText.text = numToFind.toString()


        while (nubOfScaleAnim < numOfTypeAnim){
            val temp = rnd.nextInt(buttons.size)
            if(!buttons[temp].scaleAnim && !buttons[temp].rotateAnim){
                buttons[temp].scaleAnim()
            }

            nubOfScaleAnim++
        }

        while (namOfRotateAims < numOfTypeAnim){
            val temp = rnd.nextInt(buttons.size)
            if(!buttons[temp].scaleAnim && !buttons[temp].rotateAnim){
                buttons[temp].rotateAnim()
            }

            namOfRotateAims++
        }


    }


    private fun numToSearch(): String {
        val rnd = Random()
        var res = 0
        when (level) {
            1 -> {
                res = rnd.nextInt(100)
               while (res == numToFind){
                   res = rnd.nextInt(100)
               }
            }

            2,3 -> {
                res = rnd.nextInt(200)+50
                while (res == numToFind){
                    res = rnd.nextInt(200)+50
                }
            }

            4,5 -> {
                res = rnd.nextInt(200)+200
                while (res == numToFind){
                    res = rnd.nextInt(200) + 200
                }
            }

            6,7 -> {
                res = rnd.nextInt(100)+400
                while (res == numToFind){
                    res = rnd.nextInt(100) + 400
                }
            }

            8,9 -> {
                res = rnd.nextInt(100)+800
                while (res == numToFind){
                    res = rnd.nextInt(100)+800
                }
            }

            10,11 -> {
                res = rnd.nextInt(100) + 1000
                while (res == numToFind){
                    res = rnd.nextInt(100) + 1000
                }
            }

            12 -> {
                res = rnd.nextInt(100)+1500
                while (res == numToFind){
                    res = rnd.nextInt(100)+1500
                }
            }

            13 -> {
                res = rnd.nextInt(100)+2500
                while (res == numToFind){
                    res = rnd.nextInt(100)+2500
                }
            }

            14,15 -> {
                res = rnd.nextInt(200)+4900
                while (res == numToFind){
                    res = rnd.nextInt(200)+4900
                }
            }

            16,17 -> {
                res = rnd.nextInt(50)+ 8000
                while (res == numToFind){
                    res = rnd.nextInt(50)+8000
                }
            }
        }

        return res.toString()
    }

    private fun getTotalHeight(): Int {
        val numText: TextView = findViewById(R.id.num_text)
        return (getScreenHeight() - numText.height)
    }

    private fun removeBtns() {
        val rl: RelativeLayout = findViewById(R.id.rl_game)
        while (buttons.isNotEmpty()) {
            rl.removeView(buttons[buttons.size - 1].btn)
            buttons.removeAt(buttons.size - 1)
        }
    }
}
