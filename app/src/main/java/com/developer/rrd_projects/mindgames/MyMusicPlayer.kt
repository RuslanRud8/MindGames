package com.developer.rrd_projects.mindgames

import android.content.Context
import android.media.MediaPlayer

private var mediaPlayerBackMusic:MediaPlayer? = null
private var length:Int = 0

fun playSound(context: Context, _id: Int) {
    val mediaPlayerEffects = MediaPlayer.create(context, _id)
    mediaPlayerEffects.setOnCompletionListener { mediaPlayerEffects.release() }
    mediaPlayerEffects.setVolume(100F,100F)
    mediaPlayerEffects.start()
}

fun createBackMusicPlayer(context: Context){
    mediaPlayerBackMusic = MediaPlayer.create(context,R.raw.background_music)
    mediaPlayerBackMusic!!.isLooping = true
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

