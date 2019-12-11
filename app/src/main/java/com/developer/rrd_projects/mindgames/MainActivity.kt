package com.developer.rrd_projects.mindgames

import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.developer.rrd_projects.mindgames.animators.animateButtons
import com.developer.rrd_projects.mindgames.animators.animateGear
import com.developer.rrd_projects.mindgames.games.readGameSet
import com.developer.rrd_projects.mindgames.person.*
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {


    init {
        loadImages()
    }

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

        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)

        val playBtn: Button = findViewById(R.id.play_btn)
        val statBtn: Button = findViewById(R.id.stat_btn)
        val setBtn: Button = findViewById(R.id.settings_btn)
        val extBtn: Button = findViewById(R.id.exit_btn)
        val levelBar: ProgressBar = findViewById(R.id.main_level_bar)
        val userNameText: TextView = findViewById(R.id.user_name_text)
        val userLevelText: TextView = findViewById(R.id.main_level_text)

        val gamesSet = readGameSet(applicationContext)


        val person = readPerson(applicationContext)
        if (person.userName == "-1") {
            createNewPerson()
        } else {
            setUpMainScreen(levelBar, userNameText, userLevelText, person)
        }


        startGears()

        if (gamesSet.buttonsAnimation) {
            moveBtns(playBtn, statBtn, setBtn, extBtn)
        }

        val percent = ((person.exp * 100) / getExpForLevel(person.level)) * 100
        levelBar.progress = percent
        //animateProgressBar(levelBar, percent,0)
    }

    private fun setUpMainScreen(
        levelBar: ProgressBar,
        userNameText: TextView,
        userLevelText: TextView,
        person: Person
    ) {
        levelBar.background = getDrawable(getImageId(person.icon))
        userNameText.text = person.userName
        userLevelText.text = getString(R.string.level_text, person.level)
    }

    private fun createNewPerson() {
        val intent = Intent(this, NameChooser::class.java)
        startActivity(intent)
        finish()
        return
    }

    fun exitGame(view: View) {
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(i)

    }


    private fun getScreenWidth(): Float {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x.toFloat()
    }

    private fun moveBtns(playBtn: Button, statBtn: Button, setBtn: Button, extBtn: Button) {
        val width = getScreenWidth()

        animateButtons(playBtn, width, 1500, 50)
        animateButtons(statBtn, width, 1500, 150)
        animateButtons(setBtn, width, 1500, 250)
        animateButtons(extBtn, width, 1500, 350)
    }


    private fun startGears() {
        animateGear(findViewById(R.id.left_blue_gear))
        animateGear(findViewById(R.id.left_green_gear))
        animateGear(findViewById(R.id.left_orange_gear))
        animateGear(findViewById(R.id.right_blue_gear))
        animateGear(findViewById(R.id.right_green_gear))
        animateGear(findViewById(R.id.right_orange_gear))
    }

    fun goToProfile(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("comesFrom", "mainMenu")
        startActivity(intent)
        finish()
        return
    }

    fun openSettings(view: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
        finish()
        return
    }

    fun goToGames(view: View) {
        val intent = Intent(this, Games::class.java)
        startActivity(intent)
        finish()
        return
    }

    fun goToStatistics(view: View) {
        val intent = Intent(this, StatisticsController::class.java)
        startActivity(intent)
        finish()
        return
    }
}
