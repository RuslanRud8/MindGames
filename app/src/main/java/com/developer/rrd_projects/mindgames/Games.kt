package com.developer.rrd_projects.mindgames

import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.ImageView
import com.developer.rrd_projects.mindgames.games.anagramsGame.AnagramGame
import com.developer.rrd_projects.mindgames.games.colorsGame.ColorsGame
import com.developer.rrd_projects.mindgames.games.fNGame.FindNumGame
import com.developer.rrd_projects.mindgames.games.lampsGame.LampsGame
import com.developer.rrd_projects.mindgames.games.sortGame.SortGame

class Games : AppCompatActivity() {

    var previews: ArrayList<ImageView> = ArrayList()

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


        setContentView(R.layout.activity_games)

        previews.add(findViewById(R.id.findNumGamePr))
        previews.add(findViewById(R.id.pr_1))
        previews.add(findViewById(R.id.pr_2))
        previews.add(findViewById(R.id.pr_3))

        setWidthForImages()

    }

    fun goToMenu(view: View) {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun goToProfile(view: View) {
        val intent: Intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("comesFrom", "games")
        startActivity(intent)
    }

    private fun setWidthForImages() {
        val totalWidth = getScreenWidth()*0.9
        val width:Int = (totalWidth / 4).toInt()

        var i = 0
        while (i <4){
            previews[i].layoutParams.width = width
            previews[i].layoutParams.height = (width*0.63).toInt()
            i++
        }

    }


    private fun getScreenWidth(): Int {
        val display: Display = windowManager.defaultDisplay
        val size: Point = Point()
        display.getSize(size)
        return size.x
    }


    fun goToFNGame(view: View){
        val intent: Intent = Intent(this, FindNumGame::class.java)
        intent.putExtra("started", "false")
        startActivity(intent)
    }


    fun goToLamps(view: View){
        val intent: Intent = Intent(this, LampsGame::class.java)
        intent.putExtra("started", "false")
        startActivity(intent)
    }

    fun goToSortGame(view: View){
        val intent: Intent = Intent(this, SortGame::class.java)
        intent.putExtra("started", "false")
        startActivity(intent)
    }

    fun goToColorsGame(view: View){
        val intent: Intent = Intent(this, ColorsGame::class.java)
        intent.putExtra("started", "false")
        startActivity(intent)
    }

    fun goToAnagramGame(view: View){
        val intent: Intent = Intent(this, AnagramGame::class.java)
        intent.putExtra("started", "false")
        startActivity(intent)
    }
}
