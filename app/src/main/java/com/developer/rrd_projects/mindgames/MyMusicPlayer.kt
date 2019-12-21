package com.developer.rrd_projects.mindgames

import android.content.Context
import android.media.MediaPlayer

private var mediaPlayerBackMusic:MediaPlayer? = null
private var backgroundVolume = 0.4f
private var effectsVolume = 0.4f
private var activeEffects = true


fun setBackVolume(volume:Float){
    backgroundVolume = volume
    if(mediaPlayerBackMusic != null) {
        mediaPlayerBackMusic!!.setVolume(backgroundVolume, backgroundVolume)
    }
}

fun setEffectsVolume(volume:Float){
    effectsVolume = volume
}

fun setEffectSoundEnabled(active:Boolean){
    activeEffects = active
}

fun playSound(context: Context, _id: Int) {
    if(activeEffects) {
        val mediaPlayerEffects = MediaPlayer.create(context, _id)
        mediaPlayerEffects.setOnCompletionListener { mediaPlayerEffects.release() }
        mediaPlayerEffects.setVolume(effectsVolume, effectsVolume)
        mediaPlayerEffects.start()
    }
}

fun createBackMusicPlayer(context: Context){
    mediaPlayerBackMusic = MediaPlayer.create(context,R.raw.background_music)
    mediaPlayerBackMusic!!.isLooping = true
    mediaPlayerBackMusic!!.setVolume(backgroundVolume, backgroundVolume)
}

fun startBackMusicPlayer(context:Context){
    if(mediaPlayerBackMusic != null) {
        mediaPlayerBackMusic!!.start()
    }else {
        createBackMusicPlayer(context)
        mediaPlayerBackMusic!!.start()
    }
}

fun pauseBackMusicPlayer(){

        mediaPlayerBackMusic!!.pause()

}


