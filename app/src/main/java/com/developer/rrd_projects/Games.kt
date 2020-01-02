package com.developer.rrd_projects

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.developer.rrd_projects.games.anagramsGame.AnagramGame
import com.developer.rrd_projects.games.colorsGame.ColorsGame
import com.developer.rrd_projects.games.fNGame.FindNumGame
import com.developer.rrd_projects.games.lampsGame.LampsGame
import com.developer.rrd_projects.games.sortGame.SortGame

class Games : MyGameActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContentView(R.layout.activity_games)

        setUpButtons()
    }

    private fun setUpButtons(){
        val profileBtn: Button = findViewById(R.id.profile_btn)
        profileBtn.setOnClickListener { goToProfile() }

        val menuBtn : Button = findViewById(R.id.menu_btn)
        menuBtn.setOnClickListener { goToMenu() }

        val findNumGamePr:ImageView = findViewById(R.id.findNumGamePr)
        findNumGamePr.setOnClickListener { goToGame(FindNumGame::class.java) }


        val lampsGamePr:ImageView = findViewById(R.id.pr_1)
        lampsGamePr.setOnClickListener { goToGame(LampsGame::class.java) }


        val sortGamePr:ImageView = findViewById(R.id.pr_2)
        sortGamePr.setOnClickListener { goToGame(SortGame::class.java) }


        val colorsGamePr: ImageView = findViewById(R.id.pr_3)
        colorsGamePr.setOnClickListener { goToGame(ColorsGame::class.java) }


        val anagramGamePr:ImageView = findViewById(R.id.anagram)
        anagramGamePr.setOnClickListener { goToGame(AnagramGame::class.java) }

    }

    private fun goToMenu() {
        playSound(this,R.raw.menu_button_sound)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun goToGame(game:Class<*>){
        playSound(this,R.raw.menu_button_sound)
        val intent = Intent(this, game)
        intent.putExtra("started", "false")
        startActivity(intent)
    }


    private fun goToProfile() {
        playSound(this,R.raw.menu_button_sound)
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("comesFrom", "games")
        startActivity(intent)
    }
}
