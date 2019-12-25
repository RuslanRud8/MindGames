package com.developer.rrd_projects

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.developer.rrd_projects.animators.animateGear
import com.developer.rrd_projects.person.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Icons : MyGameActivity() {

    private var iconsView: ArrayList<ImageView> = ArrayList()
    private var iconsSet: ArrayList<TextView> = ArrayList()

    private var iconsViewPrem: ArrayList<ImageView> = ArrayList()
    private var iconsSetPrem: ArrayList<TextView> = ArrayList()

    var prevIcon: Int = -1
    var iconLast: Int = -1
    private lateinit var person:Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContentView(R.layout.activity_icons)


        iconsSet.add(findViewById(R.id.icon_1_view_set))
        iconsView.add(findViewById(R.id.icon_1_view))

        iconsSet.add(findViewById(R.id.icon_2_view_set))
        iconsView.add(findViewById(R.id.icon_2_view))

        iconsSet.add(findViewById(R.id.icon_3_view_set))
        iconsView.add(findViewById(R.id.icon_3_view))

        iconsSet.add(findViewById(R.id.icon_4_view_set))
        iconsView.add(findViewById(R.id.icon_4_view))

        iconsSet.add(findViewById(R.id.icon_5_view_set))
        iconsView.add(findViewById(R.id.icon_5_view))

        iconsSet.add(findViewById(R.id.icon_6_view_set))
        iconsView.add(findViewById(R.id.icon_6_view))

        iconsSet.add(findViewById(R.id.icon_7_view_set))
        iconsView.add(findViewById(R.id.icon_7_view))

        iconsSet.add(findViewById(R.id.icon_8_view_set))
        iconsView.add(findViewById(R.id.icon_8_view))

        iconsSet.add(findViewById(R.id.icon_9_view_set))
        iconsView.add(findViewById(R.id.icon_9_view))

        iconsSet.add(findViewById(R.id.icon_10_view_set))
        iconsView.add(findViewById(R.id.icon_10_view))

        iconsSet.add(findViewById(R.id.icon_11_view_set))
        iconsView.add(findViewById(R.id.icon_11_view))

        iconsSet.add(findViewById(R.id.icon_12_view_set))
        iconsView.add(findViewById(R.id.icon_12_view))

        iconsSet.add(findViewById(R.id.icon_13_view_set))
        iconsView.add(findViewById(R.id.icon_13_view))

        iconsSet.add(findViewById(R.id.icon_14_view_set))
        iconsView.add(findViewById(R.id.icon_14_view))

        iconsSet.add(findViewById(R.id.icon_15_view_set))
        iconsView.add(findViewById(R.id.icon_15_view))

        iconsSet.add(findViewById(R.id.icon_16_view_set))
        iconsView.add(findViewById(R.id.icon_16_view))

        iconsSet.add(findViewById(R.id.icon_17_view_set))
        iconsView.add(findViewById(R.id.icon_17_view))

        iconsSet.add(findViewById(R.id.icon_18_view_set))
        iconsView.add(findViewById(R.id.icon_18_view))

        iconsView.add(findViewById(R.id.icon_19_view))
        iconsSet.add(findViewById(R.id.icon_19_view_set))
        iconsView.add(findViewById(R.id.icon_20_view))
        iconsSet.add(findViewById(R.id.icon_20_view_set))
        iconsView.add(findViewById(R.id.icon_21_view))
        iconsSet.add(findViewById(R.id.icon_21_view_set))
        iconsView.add(findViewById(R.id.icon_22_view))
        iconsSet.add(findViewById(R.id.icon_22_view_set))
        iconsView.add(findViewById(R.id.icon_23_view))
        iconsSet.add(findViewById(R.id.icon_23_view_set))

        iconsViewPrem.add(findViewById(R.id.icon_prem_1_view))
        iconsSetPrem.add(findViewById(R.id.icon_prem_1_view_set))

        setWidthForImages()

        person = readPerson(this)

        unlockIcons(person.level)

        Log.i("My","${person.level}")
        prevIcon = person.icon

        val preview: ImageView = findViewById(R.id.icon_preview)

        preview.setImageDrawable(getDrawable(getImageId(person.icon)))

        iconsSet[prevIcon].background = getDrawable(getImageId(24))

        startGears()
    }

    private fun getPerson(context: Context): Single<Person> {
        return Single.create { s ->
            s.onSuccess(readPerson(context))
        }
    }

    private fun startGears() {
        animateGear(findViewById(R.id.left_blue_gear))
        animateGear(findViewById(R.id.left_green_gear))
        animateGear(findViewById(R.id.left_orange_gear))
        animateGear(findViewById(R.id.right_blue_gear))
        animateGear(findViewById(R.id.right_green_gear))
        animateGear(findViewById(R.id.right_orange_gear))
    }

    private fun unlockIcons(level: Int) {
        var i = 0
        val num = getLevel(level)

        while (i < num) {
            iconsSet[i].background = null
            iconsSet[i].text = ""
            i++
        }

    }

    fun goBackToProfile(view: View) {

        playSound(this,R.raw.menu_button_sound)
        if (iconLast != -1) {
            person.icon = iconLast
            writePerson(person, applicationContext)
        }


        val inten = Intent(this, ProfileActivity::class.java)
        inten.putExtra("comesFrom", intent.getStringExtra("comesFrom"))
        startActivity(inten)
        finish()
    }

    private fun setWidthForImages() {
        val totalWidth = getScreenWidth() * 0.69
        val width: Int = (totalWidth / 5).toInt()

        var i = 0
        while (i < 23) {
            iconsView[i].layoutParams.width = width
            iconsSet[i].layoutParams.width = width
            iconsSet[i].layoutParams.height = width
            iconsView[i].layoutParams.height = width
            i++
        }

        for (l in iconsViewPrem.indices) {
            iconsViewPrem[l].layoutParams.width = width
            iconsViewPrem[l].layoutParams.height = width
            iconsSetPrem[l].layoutParams.width = width
            iconsSetPrem[l].layoutParams.height = width
        }

    }

    private fun getScreenWidth(): Int {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun selectIcon(icon: View) {
        val tempIcon: TextView = icon as TextView
        val preview: ImageView = findViewById(R.id.icon_preview)
        val ind: Int = iconsSet.indexOf(tempIcon)

        println(ind)
        if (iconsSet[ind].background == null) {
            playSound(this,R.raw.settings_button_sound)
            iconsSet[ind].background = getDrawable(getImageId(24))
            preview.setImageDrawable(iconsView[ind].drawable)
            iconsSet[prevIcon].background = null
            prevIcon = ind
            iconLast = ind
        }else playSound(this,R.raw.error_sound)
    }
}
