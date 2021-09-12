package com.developer.rrd_projects.games

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.developer.rrd_projects.*
import com.developer.rrd_projects.games.end_game_screen.EndGameScreen
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


open class GamesActivity : MyGameActivity() {

    private lateinit var gameTimerText: TextView
    private lateinit var darkScreenView: ImageView
    private lateinit var startGameVoid: () -> Unit
    private lateinit var timerTextView:TextView
    protected var level:Int = 1
    protected var score:Int = 0
    protected var lastSecond:Int = 60
    private lateinit var context: Context
    private lateinit var gameName: String
    private lateinit var startBtn: Button
    private lateinit var leaveBtn: Button
    protected var gameStarted:Boolean = false
    private var isPausedGame = false
    private var timeForGame:Int = 60
    private lateinit var gameTimerRunnable:Runnable
    private val gameTimerHandler:Handler = Handler()

    override fun onResume() {
        super.onResume()
        isPausedGame = false
    }

    override fun onPause() {
        super.onPause()
        if(!isPausedGame){
            isPausedGame = true
        }
    }

    protected fun initGame(context: Context, gameName: String, startBtn:Button, leaveBtn:Button, timerTextView:TextView, gameTimerText: TextView){
        this.context = context
        this.gameName = gameName
        this.timerTextView = timerTextView
        this.startBtn = startBtn
        this.leaveBtn = leaveBtn
        this.gameTimerText = gameTimerText

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

    protected fun createPreStartTimer(darkScreenView: ImageView, startGameVoid: () -> Unit){
        this.darkScreenView = darkScreenView
        this.startGameVoid = startGameVoid
    }

    protected fun createGameTimer(time:Int){

        timeForGame = time
        gameTimerText.text = time.toString()

    }

    private  fun gameTimer(){
        gameTimerRunnable = Runnable { updateTimer() }
        gameTimerHandler.postDelayed(gameTimerRunnable,1000)
    }

    private fun updateTimer() {
        if(!isPausedGame){
            timeForGame--
            if(timeForGame == 0){
                leaveGame()
            }else {
                if(timeForGame == 3){
                    playSound(this, R.raw.timer_sound)
                }

                gameTimerText.text = timeForGame.toString()
                gameTimerHandler.postDelayed(gameTimerRunnable,1000)
            }
        }else gameTimerHandler.postDelayed(gameTimerRunnable,1000)
    }

    private suspend fun preStartTimer(){
        for (i in 3 downTo 1){
            timerTextView.text = i.toString()
            delay(1000L)
        }

        playSound(applicationContext,R.raw.startlevel_sound)
        timerTextView.visibility = View.GONE
        darkScreenView.visibility = View.GONE
        startGameVoid()
        gameStarted = true
        gameTimer()
    }

    private fun startGame() {
        playSound(this,R.raw.menu_button_sound)
        playSound(this,R.raw.timer_sound)

        startBtn.visibility = View.GONE
        leaveBtn.visibility = View.GONE

        timerTextView.visibility = View.VISIBLE
        MainScope().launch { preStartTimer() }
    }

    private fun leaveFromStart(){
        playSound(this,R.raw.menu_button_sound)
        startActivity(Intent(context, Games::class.java))
    }

    private fun leaveGame() {
        val intent = Intent(context, EndGameScreen::class.java)
        intent.putExtra("game_ended", gameName)
        intent.putExtra("game_score", score)
        startActivity(intent)
    }

    protected fun getScreenHeight(): Int {
        return  this.window.decorView.height
    }

    protected fun getScreenWidth(): Int {
        return  this.window.decorView.width
    }
}
