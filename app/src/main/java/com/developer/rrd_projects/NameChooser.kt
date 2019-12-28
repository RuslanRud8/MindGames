package com.developer.rrd_projects

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import com.developer.rrd_projects.animators.animateGear
import com.developer.rrd_projects.person.Person
import com.developer.rrd_projects.person.readPerson
import com.developer.rrd_projects.person.writePerson

class NameChooser : MyGameActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContentView(R.layout.activity_name_choser)
        startGears()
    }

    private fun startGears(){
        animateGear(findViewById(R.id.left_blue_gear))
        animateGear(findViewById(R.id.left_green_gear))
        animateGear(findViewById(R.id.left_orange_gear))
        animateGear(findViewById(R.id.right_blue_gear))
        animateGear(findViewById(R.id.right_green_gear))
        animateGear(findViewById(R.id.right_orange_gear))
    }

    fun createPerson(view: View){
        playSound(this,R.raw.settings_button_sound)
        val name:EditText = findViewById(R.id.editPersonName)
        val n:String = name.text.toString()


        if(n != ""){
            person.userName = n
            writePerson(person, applicationContext)
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
