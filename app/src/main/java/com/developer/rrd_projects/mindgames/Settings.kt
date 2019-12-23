package com.developer.rrd_projects.mindgames

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SwitchCompat
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.developer.rrd_projects.mindgames.animators.animateGear
import com.developer.rrd_projects.mindgames.games.GamesSet
import com.developer.rrd_projects.mindgames.games.readGameSet
import com.developer.rrd_projects.mindgames.games.writeGameSet
import java.util.*

class Settings : MyGameActivity() {

    var totalMin = 0
    var gamesSet = GamesSet()
    private lateinit var backgroundMusicSeeker:SeekBar
    private lateinit var effectsSoundSeeker:SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContentView(R.layout.activity_settings)

        gamesSet = readGameSet(applicationContext)

        val btnAnimSw : SwitchCompat = findViewById(R.id.buttons_animation_sw)
        btnAnimSw.isChecked = gamesSet.buttonsAnimation
        btnAnimSw.setOnClickListener { playSound(this,R.raw.settings_button_sound) }

        val alertSw : SwitchCompat = findViewById(R.id.alert_sw)
        alertSw.setOnCheckedChangeListener { buttonView, isChecked -> changeMode(isChecked) }
        alertSw.isChecked = gamesSet.alarmMode
        alertSw.setOnClickListener {  playSound(this,R.raw.settings_button_sound) }

        setTime(gamesSet.time/60, gamesSet.time%60)

        val backgroundMusicSw : SwitchCompat = findViewById(R.id.background_music_switch)
        backgroundMusicSw.isChecked = gamesSet.backgroundMusicActive
        backgroundMusicSw.setOnCheckedChangeListener {buttonView, isChecked -> changeBackgroundMusicMode(isChecked) }

        val effectMusicSw : SwitchCompat = findViewById(R.id.effects_sound_switch)
        effectMusicSw.isChecked = gamesSet.effectSoundActive
        effectMusicSw.setOnCheckedChangeListener {buttonView, isChecked -> changeEffectsSoundMode(isChecked) }

        backgroundMusicSeeker = findViewById(R.id.background_music_seek_bar)
        backgroundMusicSeeker.progress = (gamesSet.backgroundMusicVolume).toInt()
        backgroundMusicSeeker.isEnabled = gamesSet.backgroundMusicActive
        backgroundMusicSeeker.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setBackVolume(progress.toFloat())
                gamesSet.backgroundMusicVolume = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playSound(applicationContext, R.raw.settings_button_sound)
            }

        } )

        effectsSoundSeeker = findViewById(R.id.effects_sound_seek_bar)
        effectsSoundSeeker.progress = (gamesSet.effectSoundVolume).toInt()
        effectsSoundSeeker.isEnabled = gamesSet.effectSoundActive
        effectsSoundSeeker.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setEffectsVolume(progress.toFloat())
                gamesSet.effectSoundVolume = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playSound(applicationContext, R.raw.settings_button_sound)
            }

        })

        val chooseBtn : Button = findViewById(R.id.select_time_btn)
        chooseBtn.setOnClickListener{chooseTime()}

        val changeName :Button = findViewById(R.id.change_name_btn)
        changeName.setOnClickListener {changeN()}

        startGears()
    }

    private fun changeEffectsSoundMode(checked: Boolean) {
        playSound(this,R.raw.settings_button_sound)
        effectsSoundSeeker.isEnabled = checked
        gamesSet.effectSoundActive = checked
    }

    private fun changeBackgroundMusicMode(checked: Boolean) {
        playSound(this,R.raw.settings_button_sound)
        gamesSet.backgroundMusicActive = checked
        backgroundMusicSeeker.isEnabled = checked
    }

    private fun changeN() {
        playSound(this,R.raw.settings_button_sound)
        val intent = Intent(this, NameChooser::class.java)
        startActivity(intent)
        finish()
    }

    private fun chooseTime() {
        playSound(this,R.raw.settings_button_sound)
        TimePickerDialog(this, t , gamesSet.time/60,gamesSet.time%60,true).show()
    }

    private val t :TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> setTime(hourOfDay,minute) }

    private fun setTime(hourOfDay: Int, minute: Int) {
        val timeText: TextView = findViewById(R.id.time_str)
        timeText.text = getString(R.string.time, hourOfDay, minute)
        totalMin = minute + (60 * hourOfDay)
    }


    private fun changeMode(isChecked: Boolean) {
        if(isChecked){
            enableTimeChooser()
        }else diableTimeChooser()
    }


    private fun diableTimeChooser() {
        val chooseBtn: Button = findViewById(R.id.select_time_btn)
        val timeText: TextView = findViewById(R.id.time_str)

        chooseBtn.isEnabled = false
        chooseBtn.setTextColor(resources.getColor(R.color.gray))
        timeText.setTextColor(resources.getColor(R.color.gray))
    }


    private fun enableTimeChooser() {
        val chooseBtn: Button = findViewById(R.id.select_time_btn)
        val timeText: TextView = findViewById(R.id.time_str)

        chooseBtn.setTextColor(resources.getColor(R.color.white))
        chooseBtn.isEnabled = true
        timeText.setTextColor(resources.getColor(R.color.white))
    }

    fun saveSettings(view: View){
        playSound(this,R.raw.menu_button_sound)
        val set = readGameSet(applicationContext)
        set.buttonsAnimation = findViewById<SwitchCompat>(R.id.buttons_animation_sw).isChecked

        if( (findViewById<SwitchCompat>(R.id.alert_sw).isChecked != set.alarmMode || totalMin != set.time)){
            createNotification(findViewById<SwitchCompat>(R.id.alert_sw).isChecked )
        }

        set.time = totalMin
        set.alarmMode = findViewById<SwitchCompat>(R.id.alert_sw).isChecked

        set.effectSoundVolume = gamesSet.effectSoundVolume
        set.backgroundMusicVolume = gamesSet.backgroundMusicVolume
        set.effectSoundActive = gamesSet.effectSoundActive
        set.backgroundMusicActive = gamesSet.backgroundMusicActive

        writeGameSet(set, applicationContext)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun createNotification(b:Boolean) {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, totalMin/60)
        calendar.set(Calendar.MINUTE, totalMin%60)

        val i = Intent(applicationContext,NotificationReceiver::class.java)

        val pI = PendingIntent.getBroadcast(applicationContext,100,i,PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if(b) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pI)
        }else alarmManager.cancel(pI)
    }

    private fun startGears() {
        animateGear(findViewById(R.id.left_blue_gear))
        animateGear(findViewById(R.id.left_green_gear))
        animateGear(findViewById(R.id.left_orange_gear))
        animateGear(findViewById(R.id.right_blue_gear))
        animateGear(findViewById(R.id.right_green_gear))
        animateGear(findViewById(R.id.right_orange_gear))
    }
}
