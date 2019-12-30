package com.developer.rrd_projects

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.developer.rrd_projects.animators.animateButtons
import com.developer.rrd_projects.animators.animateGear
import com.developer.rrd_projects.games.readGameSet
import com.developer.rrd_projects.person.getExpForLevel
import com.developer.rrd_projects.person.getImageId

class ProfileActivity : MyGameActivity() {

    var comeFr: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setContentView(R.layout.activity_profile)

        startGears()

        val changeBtn: Button = findViewById(R.id.change_btn)
        val backBtn: Button = findViewById(R.id.back_btn)
        val premAkkBtn: Button = findViewById(R.id.prem_akk_btn)

        if(readGameSet(applicationContext).buttonsAnimation) {moveBtns(changeBtn, backBtn, premAkkBtn)}


        setUpProfileScreen()

        comeFr = intent.getStringExtra("comesFrom")

        premAkkBtn.setOnClickListener { goToPrem() }
    }

    private fun goToPrem() {
        val intent = Intent(this, PremShow::class.java)
        startActivity(intent)
        finish()
    }

    private fun setUpProfileScreen() {

        findViewById<ImageView>(R.id.profile_icon_view).setImageDrawable(ContextCompat.getDrawable(this,getImageId(person.icon)))
        findViewById<TextView>(R.id.user_name_text).text = person.userName
        findViewById<TextView>(R.id.profile_level_text).text = getString(R.string.level_text, person.level)

        findViewById<TextView>(R.id.games_played_label).text = getString(R.string.games_played_str, person.gamesPlayed)
        findViewById<TextView>(R.id.fav_game_label).text = getString(R.string.favourite_game_str, person.favGame)
        findViewById<TextView>(R.id.max_exp_label).text = getString(R.string.max_exp_str, person.maxExp)
        findViewById<TextView>(R.id.time_in_game_label).text = getString(R.string.time_play_str, (person.timeInGame/6)/60, (person.timeInGame)/6%60)
        findViewById<TextView>(R.id.profile_exp_text).text = getString(R.string.profile_level_progress, person.exp , getExpForLevel(person.level))

        val levelBar : ProgressBar = findViewById(R.id.profile_level_bar)
        val percent = ((person.exp * 100) / getExpForLevel(person.level))*100
        levelBar.progress = percent
        //animateProgressBar(levelBar, percent, 0)
    }

    fun goBackToMain(view: View) {
        playSound(this,R.raw.menu_button_sound)
        var inten = Intent()
        if (comeFr == "mainMenu" || comeFr == "prem") {
            inten = Intent(this, MainActivity::class.java)
        } else if (comeFr == "games") {
            inten = Intent(this, Games::class.java)
        }
        startActivity(inten)
        finish()
    }


    private fun startGears() {
        animateGear(findViewById(R.id.left_blue_gear))
        animateGear(findViewById(R.id.left_green_gear))
        animateGear(findViewById(R.id.left_orange_gear))
        animateGear(findViewById(R.id.right_blue_gear))
        animateGear(findViewById(R.id.right_green_gear))
        animateGear(findViewById(R.id.right_orange_gear))
    }


    private fun moveBtns(changeBtn: Button, backBtn: Button, premBtn: Button) {
        val width = getScreenWidth()

        animateButtons(premBtn, width, 1500, 50)
        animateButtons(changeBtn, width, 1500, 100)
        animateButtons(backBtn, width, 1500, 150)
    }

    private fun getScreenWidth(): Float {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x.toFloat()
    }

    fun goToIcons(view: View) {
        playSound(this,R.raw.menu_button_sound)
        val intent = Intent(this, Icons::class.java)
        intent.putExtra("comesFrom", comeFr)
        startActivity(intent)
        finish()
    }
}
