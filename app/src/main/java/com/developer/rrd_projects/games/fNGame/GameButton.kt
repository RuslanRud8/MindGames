package com.developer.rrd_projects.games.fNGame

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import android.view.Gravity
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.developer.rrd_projects.R
import com.developer.rrd_projects.animators.rotateAnimationRight
import com.developer.rrd_projects.animators.scaleAnimIn
import java.util.*

class GameButton(x:Float, y:Float, height: Int, width: Int, context: Context, num:String, textSize:Float) {
    var text = ""
    var btn: TextView = TextView(context)
    val con = context
    var scaleAnim:Boolean = false
    var rotateAnim:Boolean = false

    private val gameTimer = object : CountDownTimer(60000, 500) {

        override fun onFinish() {
        }

        override fun onTick(millisUntilFinished: Long) {
            setRandomColor()
        }
    }

    init {
        btn.width = width
        btn.height = height
        setRandomColor()

        btn.text = num

        btn.setTextColor(Color.WHITE)
        btn.gravity = Gravity.CENTER
        btn.translationY = y
        btn.translationX = x
        btn.isClickable = true

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(btn,10,100,1,TypedValue.COMPLEX_UNIT_DIP)

        gameTimer.start()
    }

    fun scaleAnim(){
        scaleAnim = true

        scaleAnimIn(btn)
    }

    fun rotateAnim(){
        rotateAnim = true

        rotateAnimationRight(btn)
    }



    fun setRandomColor() {
        val rnd: Random = Random()
        when (rnd.nextInt(6)) {
            0 -> btn.background = getDrawable(con, R.drawable.f_n_game_btn_dr_1)
            1 -> btn.background = getDrawable(con, R.drawable.f_n_game_btn_dr_2)
            2 -> btn.background = getDrawable(con, R.drawable.f_n_game_btn_dr_3)
            3 -> btn.background = getDrawable(con, R.drawable.f_n_game_btn_dr_4)
            4 -> btn.background = getDrawable(con, R.drawable.f_n_game_btn_dr_5)
            5 -> btn.background = getDrawable(con, R.drawable.f_n_game_btn_dr_6)
        }
    }


}