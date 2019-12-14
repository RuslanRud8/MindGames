package com.developer.rrd_projects.mindgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class PremShow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setContentView(R.layout.activity_prem_show)

        val leaveBtn : Button = findViewById(R.id.leave_prem_btn)
        val buyBtn : Button = findViewById(R.id.buy_prem_btn)

        leaveBtn.setOnClickListener { leave() }

    }

    private fun leave() {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("comesFrom", "prem")
        startActivity(intent)
        finish()
    }

}
