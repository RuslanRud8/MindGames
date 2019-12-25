package com.developer.rrd_projects

import android.content.Context
import android.media.MediaPlayer
import kotlin.math.ln

private var mediaPlayerBackMusic: MediaPlayer? = null
private var backgroundVolume = 40f
private var effectsVolume = 40f
private var activeEffects = true
private const val MAX_VOLUME: Double = 100.0


fun setBackVolume(volume: Float) {
    val vl = 1f-(ln(MAX_VOLUME - volume) / ln(MAX_VOLUME)).toFloat()
    backgroundVolume = vl
    if (mediaPlayerBackMusic != null) {
        mediaPlayerBackMusic!!.setVolume(backgroundVolume, backgroundVolume)
    }
}

fun setEffectsVolume(volume: Float) {
    val vl = 1f-(ln(MAX_VOLUME - volume) / ln(MAX_VOLUME)).toFloat()
    effectsVolume = vl
}

fun setEffectSoundEnabled(active: Boolean) {
    activeEffects = active
}

fun playSound(context: Context, _id: Int) {
    if (activeEffects) {
        val mediaPlayerEffects = MediaPlayer.create(context, _id)
        mediaPlayerEffects.setOnCompletionListener { mediaPlayerEffects.release() }
        mediaPlayerEffects.setVolume(effectsVolume, effectsVolume)
        mediaPlayerEffects.start()
    }
}

fun createBackMusicPlayer(context: Context) {
    mediaPlayerBackMusic = MediaPlayer.create(context, R.raw.background_music)
    mediaPlayerBackMusic!!.isLooping = true
    mediaPlayerBackMusic!!.setVolume(backgroundVolume, backgroundVolume)
}

fun startBackMusicPlayer(context: Context) {
    if (mediaPlayerBackMusic != null) {
        mediaPlayerBackMusic!!.start()
    } else {
        createBackMusicPlayer(context)
        mediaPlayerBackMusic!!.start()
    }
}

fun pauseBackMusicPlayer() {
    mediaPlayerBackMusic!!.pause()
}


