package com.example.mypsychologist.extensions

import android.view.View
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