package ade.yemi.growthchecker.Utilities

import ade.yemi.growthchecker.R
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.concurrent.schedule

fun View.zoom_in(){
    val animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    this.startAnimation(animation)
}
fun View.clicking(){
    val animation = AnimationUtils.loadAnimation(context, R.anim.click)
    this.startAnimation(animation)
}
fun View.shortvibrate() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(10)
    }
}
fun delay(time : Long){
    Timer().schedule(time) {
    }
}
