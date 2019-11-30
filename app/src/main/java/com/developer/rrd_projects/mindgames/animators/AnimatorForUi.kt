package com.developer.rrd_projects.mindgames.animators

import android.animation.ObjectAnimator
import androidx.core.view.ViewCompat
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

fun animateButtons(gear: Button, width: Float, duration:Long, delay:Long) {
    val btnWidth = gear.translationX

    gear.translationX = width

    ViewCompat.animate(gear)
        .translationX(btnWidth)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .setStartDelay(delay)
}


fun animateShapeToSort(img:ImageView, x:Float, y:Float , duration:Long){
    ViewCompat.animate(img)
        .translationX(x)
        .translationY(y)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
}


fun rotateAnimationRight(view:TextView) {
    ViewCompat.animate(view)
        .rotation(45F)
        .setDuration(800)
        .setInterpolator(LinearInterpolator())
        .setStartDelay(10)
        .withEndAction { rotateAnimationLeft(view) }
}

fun rotateAnimationLeft(view:TextView) {
    ViewCompat.animate(view)
        .rotation(-45F)
        .setDuration(800)
        .setInterpolator(LinearInterpolator())
        .setStartDelay(10)
        .withEndAction { rotateAnimationRight(view) }
}


fun scaleAnimIn(view:TextView){
    ViewCompat.animate(view)
        .scaleX(0.4F)
        .scaleY(0.4F)
        .setDuration(700)
        .setInterpolator(LinearInterpolator())
        .setStartDelay(10)
        .withEndAction { scaleAnimOut(view) }
}

fun scaleAnimOut(view: TextView){
    ViewCompat.animate(view)
        .scaleX(1F)
        .scaleY(1F)
        .setDuration(700)
        .setInterpolator(LinearInterpolator())
        .setStartDelay(10)
        .withEndAction { scaleAnimIn(view) }
}


fun animateGear(gear: ImageView) {
    val anim = RotateAnimation(0f,360f, gear.layoutParams.width/2.toFloat(),gear.layoutParams.height/2.toFloat())
    anim.duration = 10000
    anim.repeatCount = RotateAnimation.INFINITE
    anim.interpolator = LinearInterpolator()

    gear.animation = anim
}

fun animateProgressBar(progressBar:ProgressBar, exp:Int, startProgress:Int){
    ObjectAnimator.ofInt(progressBar, "progress",0, exp)
        .setDuration(1000)
        .start()
}

