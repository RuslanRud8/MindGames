package com.developer.rrd_projects.mindgames

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.developer.rrd_projects.mindgames.animators.animateButtons
import com.developer.rrd_projects.mindgames.animators.animateGear
import com.developer.rrd_projects.mindgames.games.readGameSet
import com.developer.rrd_projects.mindgames.person.Person
import com.developer.rrd_projects.mindgames.person.getExpForLevel
import com.developer.rrd_projects.mindgames.person.getImageId
import com.developer.rrd_projects.mindgames.person.readPerson
import com.google.ads.consent.*
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import java.net.MalformedURLException
import java.net.URL


class MainActivity : MyGameActivity() {

    private lateinit var playBtn: Button
    private lateinit var statBtn: Button
    private lateinit var setBtn: Button
    private lateinit var extBtn: Button
    private lateinit var levelBar: ProgressBar
    private lateinit var userNameText: TextView
    private lateinit var userLevelText: TextView
    private lateinit var consentForm: ConsentForm

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

        val consentInformation = ConsentInformation.getInstance(applicationContext)
        val publisherIds = arrayOf("pub-9050823804847454")

        consentInformation.requestConsentInfoUpdate(
            publisherIds,
            object : ConsentInfoUpdateListener {
                override fun onConsentInfoUpdated(consentStatus: ConsentStatus) { // User's consent status successfully updated.



                    if(consentStatus == ConsentStatus.NON_PERSONALIZED){
                        val extras = Bundle()
                        extras.putString("npa", "1")

                        val request: AdRequest = AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
                            .build()

                    }else if(consentStatus == ConsentStatus.UNKNOWN) {

                        var privacyUrl: URL? = null
                        try {
                            privacyUrl = URL("https://google.com")
                        } catch (e: MalformedURLException) {
                            e.printStackTrace()
                            // Handle error.
                        }

                         consentForm = ConsentForm.Builder(this@MainActivity, privacyUrl)
                            .withListener(object : ConsentFormListener() {
                                override fun onConsentFormLoaded() { // Consent form loaded successfully.
                                    consentForm.show()


                                }

                                override fun onConsentFormOpened() { // Consent form was displayed.

                                }

                                override fun onConsentFormClosed(
                                    consentStatus: ConsentStatus, userPrefersAdFree: Boolean
                                ) { // Consent form was closed.
                                    if(consentStatus == ConsentStatus.NON_PERSONALIZED){
                                        val extras = Bundle()
                                        extras.putString("npa", "1")

                                        val request: AdRequest = AdRequest.Builder()
                                            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
                                            .build()
                                    }
                                }

                                override fun onConsentFormError(errorDescription: String) { // Consent form error.
                                }
                            })
                            .withPersonalizedAdsOption()
                            .withNonPersonalizedAdsOption()
                            //.withAdFreeOption()
                            .build()

                        consentForm.load()
                    }
                }

                override fun onFailedToUpdateConsentInfo(errorDescription: String) { // User's consent status failed to update.
                }
            })


        MobileAds.initialize(this) {}

        initUiElements()

        initPersonsData()
    }

    private fun initPersonsData() {
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
    }

    private fun initUiElements() {
        playBtn = findViewById(R.id.play_btn)
        statBtn = findViewById(R.id.stat_btn)
        setBtn = findViewById(R.id.settings_btn)
        extBtn = findViewById(R.id.exit_btn)
        levelBar = findViewById(R.id.main_level_bar)
        userNameText = findViewById(R.id.user_name_text)
        userLevelText = findViewById(R.id.main_level_text)

        playBtn.setOnClickListener { openNewActivity(Games::class.java) }
        statBtn.setOnClickListener { openNewActivity(StatisticsController::class.java) }
        setBtn.setOnClickListener { openNewActivity(Settings::class.java) }
        extBtn.setOnClickListener { exitGame() }

        levelBar.setOnClickListener { goToProfile() }
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
    }

    private fun exitGame() {
        playSound(this, R.raw.menu_button_sound)
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(i)
        finish()
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

    private fun goToProfile() {
        playSound(this, R.raw.menu_button_sound)
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("comesFrom", "mainMenu")
        startActivity(intent)
        finish()
    }

    private fun openNewActivity(targetActivity: Class<*>) {
        playSound(this, R.raw.menu_button_sound)
        val intent = Intent(this, targetActivity)
        startActivity(intent)
        finish()
    }
}
