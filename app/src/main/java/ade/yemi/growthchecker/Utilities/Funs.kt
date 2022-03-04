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

class Commenntings(var state: String, var solution: String, var encourage: String){
}


fun thecomments(int: Int): Commenntings{
    var state = ""
    var solution = ""
    var encourage = ""
    when{
        int < -100 -> {state = "Your cumulative point is slightly too negative"
                        solution = "Is there a noticeable cause or reason for this? If so, put down this cause on notes at the notes page. Constantly check your notes for better reference on what to do and what to avoid."
                        encourage = "There are always setbacks, no one is perfect. Addictions can always be broken tho regardless of how deep one is into it. Don't mind the state of the graph and your cumulative points, you can still raise your points and ultimately get fulfilled"}
        int < 0 && int >= -100 -> { state= "Your cumulative point is negative"
            solution = "Have you noticed any cause as to why your cumulative is negative, it would be a good idea to jot it down. Keep your notes at the notes page. Also check and update your notes for easy reference"
            encourage = "It might seem like addictions are hard to break. It all balls down to our state of mind. You can break free from anything, you are in control. Don't be discouraged. Keep trying to get your points higher by avoiding the addiction"
        }
        int == 0 ->{
            state = "Your cumulative point is zero"
            solution = "This is a neutral state. Settling and thinking of best possible things to engage in which may reduce the possibilities of indulging in the addiction could be a good idea."
            encourage = "You can still raise your points. See this as a start of the journey. Limitations only exist in the mind, it's our choice to make them our realities or not"
        }
        int > 0 && int < 100 -> {
            state = "Your cumulative point is positive"
            solution = "Are there new activities you are doing that prevent you from indulging in addictions?. Well those activities are paying off. KUDOS!!! Also, make sure you update your notes at the notes page to keep yourself in check"
            encourage = "You are on a good path. Yet there's room for improvement and proper addiction breaking"
        }
        int >= 100 && int < 300 -> {
            state = "Amazing!! Your cumulative is very positive"
            solution = "Are there by any means, helpful activities you have been engaging in which has resulted to this positive response?! Kindly put these down at the notes page for easy reference. KUDOS!!"
            encourage = "Limitations only occur in the mind, in reality there is nothing that cannot be achieved. You have done well. Getting better is also a priority, there is room for improvement"
        }
        int >= 300 ->{
            state = "Very impressive!! Your cumulative is far positive "
            solution = "Definitely!! You are at the best point of addiction breaking now. Are there helpful activities you do that enable you stick to doing what is deemed right? Kindly put these down at the notes page for easy reference"
            encourage = "Absolutely impressive"
        }

    }
    var commenntings : Commenntings = Commenntings(state, solution, encourage)
    return commenntings
}