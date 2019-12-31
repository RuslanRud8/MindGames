package com.developer.rrd_projects

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.developer.rrd_projects.games.GamesSet
import com.developer.rrd_projects.games.readGameSet
import com.developer.rrd_projects.person.Person
import com.developer.rrd_projects.person.readPerson
import com.developer.rrd_projects.person.writePerson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class MyGameActivity : AppCompatActivity() {

    private lateinit var gamesSet: GamesSet
    private var timeInGame: Int = 0
    lateinit var person: Person
    private var isGameActive:Boolean = true

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
        gamesSet = readGameSet(this)
        setEffectSoundEnabled(gamesSet.effectSoundActive)
        setBackVolume(gamesSet.backgroundMusicVolume)
        setEffectsVolume(gamesSet.effectSoundVolume)
        if (gamesSet.backgroundMusicActive) {
            startBackMusicPlayer(applicationContext)
        }

        readPersonStats()

        GlobalScope.launch { countTimeInGame() }
        Log.i("TIME","launched")
    }

    private fun readPersonStats() {
        person = readPerson(this)
        timeInGame = person.timeInGame
    }

    private suspend fun countTimeInGame(){
        while (true){
            if(isGameActive){
                timeInGame++
                Log.i("TIME",timeInGame.toString())
                delay(10000L)
            }else break
        }
    }

    override fun onPause() {
        isGameActive = false
        Log.i("TIME","saved")

        person.timeInGame = timeInGame
        writePerson(person,this)
        if (gamesSet.backgroundMusicActive) {
            pauseBackMusicPlayer()
        }
        super.onPause()
    }

    override fun onResume() {
        isGameActive = true
        if (gamesSet.backgroundMusicActive) {
            startBackMusicPlayer(applicationContext)
        }
        super.onResume()
    }
}