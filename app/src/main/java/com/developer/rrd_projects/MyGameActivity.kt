package com.developer.rrd_projects

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.developer.rrd_projects.games.GamesSet
import com.developer.rrd_projects.games.readGameSet

open class MyGameActivity : AppCompatActivity() {

    private lateinit var gamesSet: GamesSet

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
        if(gamesSet.backgroundMusicActive) {
            startBackMusicPlayer(applicationContext)
        }
    }

    override fun onPause() {
        if(gamesSet.backgroundMusicActive) {
            pauseBackMusicPlayer()
        }
        super.onPause()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        if(gamesSet.backgroundMusicActive) {
            startBackMusicPlayer(applicationContext)
        }
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}