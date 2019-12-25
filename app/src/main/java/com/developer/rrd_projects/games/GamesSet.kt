package com.developer.rrd_projects.games

import java.io.Serializable

class GamesSet :Serializable {

    var fNGameAttention:Boolean = true
    var lampsGameAttention:Boolean = true
    var sortGameAttention:Boolean = true
    var colorsGameAttention:Boolean = true
    var angramsGameAttention:Boolean = true
    var mathGameAttention:Boolean = true
    var findItemGameAttention:Boolean = true
    var numsGameAttention:Boolean = true
    var buttonsAnimation:Boolean = true
    var alarmMode:Boolean = true
    var time:Int = 720
    var backgroundMusicActive:Boolean = true
    var effectSoundActive:Boolean = true
    var backgroundMusicVolume: Float = 40f
    var effectSoundVolume: Float = 40f
 }