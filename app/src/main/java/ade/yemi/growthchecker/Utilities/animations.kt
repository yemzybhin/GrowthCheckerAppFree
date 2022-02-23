package ade.yemi.growthchecker.Utilities

import ade.yemi.growthchecker.R
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

fun View.zoom_in(){
    val animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    this.startAnimation(animation)
}
fun View.click(){
    val animation = AnimationUtils.loadAnimation(context, R.anim.click)
    this.startAnimation(animation)
}