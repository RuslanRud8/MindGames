package com.developer.rrd_projects

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.developer.rrd_projects.games.anagramsGame.AnagramGame
import com.developer.rrd_projects.games.colorsGame.ColorsGame
import com.developer.rrd_projects.games.fNGame.FindNumGame
import com.developer.rrd_projects.games.lampsGame.LampsGame
import com.developer.rrd_projects.games.readGameSet
import com.developer.rrd_projects.games.sortGame.SortGame
import com.developer.rrd_projects.games.writeGameSet

class Attention : MyGameActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContentView(R.layout.activity_attention)

        val attention: TextView = findViewById(R.id.attention)

        attention.text = getString(R.string.tutorial, intent.getStringExtra("firstLine"), intent.getStringExtra("secondLine"))

        val checkBox:CheckBox = findViewById(R.id.checkbox)
        checkBox.setOnClickListener { playSound(this,R.raw.settings_button_sound) }
    }

    fun contin(view: View){
        playSound(this,R.raw.menu_button_sound)

        val chek: CheckBox = findViewById(R.id.checkbox)
        val game: String = intent.getStringExtra("game")

        if(chek.isChecked){
            val g = readGameSet(applicationContext)

            when (game) {
                "fNGame" -> g.fNGameAttention = false
                "lamps" -> g.lampsGameAttention = false
                "sortGame" -> g.sortGameAttention = false
                "colorsGame" -> g.colorsGameAttention = false
                "anagramGame" -> g.angramsGameAttention = false
            }

            writeGameSet(g, applicationContext)
        }



        var inten = Intent()

        when (game) {
            "fNGame" -> inten = Intent(this, FindNumGame::class.java)
            "lamps" -> inten = Intent(this, LampsGame::class.java)
            "sortGame" -> inten = Intent(this, SortGame::class.java)
            "colorsGame" -> inten = Intent(this, ColorsGame::class.java)
            "anagramGame" -> inten = Intent(this, AnagramGame::class.java)

        }
        inten.putExtra("started", "true")
        startActivity(inten)

        finish()
    }
}
