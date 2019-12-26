package com.developer.rrd_projects.games

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.developer.rrd_projects.*


open class GamesActivity : MyGameActivity() {

    private lateinit var preStartTimer:CountDownTimer
    protected lateinit var gameTimer:CountDownTimer
    private lateinit var timerTextView:TextView
    protected var level:Int = 1
    protected var score:Int = 0
    protected var lastSecond:Int = 60
    private lateinit var context: Context
    private lateinit var gameName: String
    private lateinit var startBtn: Button
    private lateinit var leaveBtn: Button
    protected var gameStarted:Boolean = false

    protected fun initGame(context: Context, gameName: String, startBtn:Button, leaveBtn:Button, timerTextView:TextView){
        this.context = context
        this.gameName = gameName
        this.timerTextView = timerTextView
        this.startBtn = startBtn
        this.leaveBtn = leaveBtn

        startBtn.setOnClickListener { startGame() }
        leaveBtn.setOnClickListener { leaveFromStart() }
    }

    protected fun checkAttention(bool:Boolean, firstLine:String, secondLine:String,game:String, mIntent: Intent){
        if (bool) {
            if (mIntent.getStringExtra("started") == "false") {
                val i = Intent(context, Attention::class.java)
                i.putExtra("firstLine", firstLine)
                i.putExtra("secondLine", secondLine)
                i.putExtra("game", game)
                startActivity(i)
            }
        }
    }

    protected fun createPreStartTimer(
        darkScreenView: ImageView, startGameVoid: () -> Unit){

        preStartTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text =
                    getString(R.string.timer_str, (millisUntilFinished / 1000 + 1).toString())
            }

            override fun onFinish() {
                playSound(applicationContext,R.raw.startlevel_sound)
                timerTextView.visibility = View.GONE
                darkScreenView.visibility = View.GONE
                gameTimer.start()
                startGameVoid()
                gameStarted = true
            }
        }
    }

    protected fun createGameTimer(time:Long, gameTimerText: TextView){

        gameTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                gameTimerText.text =
                    getString(R.string.timer_str, (millisUntilFinished / 1000 + 1).toString())

                if(gameTimerText.text == "3"){
                    playSound(applicationContext,R.raw.timer_sound)
                }
            }

            override fun onFinish() {
               leaveGame()
            }
        }
    }

    private fun startGame() {
        playSound(this,R.raw.menu_button_sound)
        playSound(this,R.raw.timer_sound)

        startBtn.visibility = View.GONE
        leaveBtn.visibility = View.GONE

        timerTextView.visibility = View.VISIBLE
        preStartTimer.start()
        gameStarted = true
    }

    private fun leaveFromStart(){
        playSound(this,R.raw.menu_button_sound)
        startActivity(Intent(context, Games::class.java))
    }

    private fun leaveGame() {
        val intent = Intent(context, Reclam::class.java)
        intent.putExtra("game_ended", gameName)
        intent.putExtra("game_score",  score)
        startActivity(intent)
    }

    protected fun getScreenHeight(): Int {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        val height = metrics.heightPixels
        val width = metrics.widthPixels
        return height
    }

    protected fun getScreenWidth(): Int {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        val height = metrics.heightPixels
        val width = metrics.widthPixels
        return width
    }
}
