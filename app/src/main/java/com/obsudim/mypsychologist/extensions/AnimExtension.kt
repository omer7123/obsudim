package com.obsudim.mypsychologist.extensions

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

fun View.bounce() {
    val springForce = SpringForce(0f)
        .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
        .setStiffness(SpringForce.STIFFNESS_MEDIUM)

    SpringAnimation(this, DynamicAnimation.TRANSLATION_X).apply {
        setStartVelocity(2000f)
        spring = springForce
        start()
    }
}

fun ViewGroup.shrink() {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f)

    ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        .setDuration(500)
        .start()
}

fun View.expand() {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)

    ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        .setDuration(500)
        .start()
}