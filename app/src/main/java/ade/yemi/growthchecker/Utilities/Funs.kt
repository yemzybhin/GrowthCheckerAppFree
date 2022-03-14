package ade.yemi.growthchecker.Utilities

import ade.yemi.growthchecker.R
import ade.yemi.roomdatabseapp.Data.Challenge
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.renderscript.Int2
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.concurrent.schedule

class OnSingleClickListener(private val block: () -> Unit) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 800) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()

        block()
    }
}

fun View.setOnSingleClickListener(block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(block))
}

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
                        encourage = "There are always setbacks, no one is perfect. Addictions can always be broken though regardless of how deep one is into it. Don't mind the state of the graph and your cumulative points, you can still raise your points and ultimately get fulfilled"}
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
fun generatecumulative(stuff: MutableList<String>):String{
    var list1 = mutableListOf<Int>()
    var num1 = 0
    var shown2 = ""
    for (i in stuff){
        num1 = num1 + i.toInt()
        list1.add(num1)
    }
    for (i in list1){
        shown2 = "$shown2 $i \n\n"
    }
    return shown2
}
fun generatepoints(stuff: MutableList<String>) :String{
    var shown = ""
    for(i in stuff){
        shown = "$shown $i \n\n"
    }
    return shown
}
fun generatedays(stuff: MutableList<String>) :String{
    var shown = ""
    for (i in 1..stuff.size){
        shown = "$shown $i \n\n"
    }
    return  shown
}
class awardpagepointsearned(var attained: Int, var attainable : Int ){}
fun getpoints(points: MutableList<String>, challenge: Challenge) : awardpagepointsearned{
    var attained = 0

    for (i in points){
        attained = attained + i.toInt()
    }
    var attainable = challenge.Days.toInt() * 22
    return awardpagepointsearned(attained, attainable)
}

fun setranking(attained: Int, attainable: Int):String{
    var word = ""
    when{
        attained < (attainable / 7) -> word ="Fifth rank attained. No award for this rank"
        attained >= (attainable /7) && attained < (attainable/4.5) -> word ="Fourth rank attained. No award for this rank"
        attained >= (attainable /4.5) && attained < (attainable/2) -> word ="Third rank attained. Nice attempt!"
        attained >= (attainable /2) && attained < (attainable/1.3) -> word ="Second rank attained. Great Job"
        attained >= (attainable /1.3) && attained <= attainable-> word ="You attained the highest rank. Congratulations!!"
        attained > attainable -> word ="You attained the highest rank. Congratulations!!"
    }
    return word
}

fun setrankimage(attained: Int, attainable: Int, days: String, imageView: ImageView){
    var rank = 0
    when{
        attained < attainable / 7 -> rank = 5
        attained >= attainable /7 && attained < attainable/4.5 -> rank =4
        attained >= attainable /4.5 && attained < attainable/2 -> rank =3
        attained >= attainable /2 && attained < attainable/1.3 -> rank =2
        attained >= attainable /1.3 && attained <= attainable-> rank =1
        attained > attainable -> rank = 1
    }

    when{
        days == "14" && rank == 1 -> imageView.setImageResource(R.drawable.firstchallengefirst)
        days == "14" && rank == 2 -> imageView.setImageResource(R.drawable.firstchallengesecond)
        days == "14" && rank == 3 -> imageView.setImageResource(R.drawable.firstchallengethird)
        days == "14" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none1)

        days == "30" && rank == 1 -> imageView.setImageResource(R.drawable.secondchallengefirst)
        days == "30" && rank == 2 -> imageView.setImageResource(R.drawable.secondchallengesecond)
        days == "30" && rank == 3 -> imageView.setImageResource(R.drawable.secondchallengethird)
        days == "30" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none2)

        days == "60" && rank == 1 -> imageView.setImageResource(R.drawable.thirdchallengefirst)
        days == "60" && rank == 2 -> imageView.setImageResource(R.drawable.thirdchallengesecond)
        days == "60" && rank == 3 -> imageView.setImageResource(R.drawable.thirdchallengethird)
        days == "60" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none3)

        days == "100" && rank == 1 -> imageView.setImageResource(R.drawable.fourthchallengefirst)
        days == "100" && rank == 2 -> imageView.setImageResource(R.drawable.fourthchallengesecond)
        days == "100" && rank == 3 -> imageView.setImageResource(R.drawable.fourthchallengethird)
        days == "100" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none4)

        days == "200" && rank == 1 -> imageView.setImageResource(R.drawable.fifthchallengefirst)
        days == "200" && rank == 2 -> imageView.setImageResource(R.drawable.fifthchallengesecond)
        days == "200" && rank == 3 -> imageView.setImageResource(R.drawable.fifthchallengethird)
        days == "200" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none5)
    }
}

class profileimages(var image: ImageView, var num: Int)
fun setimage(imageView: ImageView, int: Int){
    when(int){
        1 -> imageView.setImageResource(R.drawable.a40)
        2 -> imageView.setImageResource(R.drawable.a41)
        3 -> imageView.setImageResource(R.drawable.a42)
        4 -> imageView.setImageResource(R.drawable.a43)
        5 -> imageView.setImageResource(R.drawable.a44)
        6 -> imageView.setImageResource(R.drawable.a45)
        7 -> imageView.setImageResource(R.drawable.a46)
        8 -> imageView.setImageResource(R.drawable.a47)
        9 -> imageView.setImageResource(R.drawable.a48)
        10 -> imageView.setImageResource(R.drawable.a49)
        11 -> imageView.setImageResource(R.drawable.a50)
        12 -> imageView.setImageResource(R.drawable.a51)
        13 -> imageView.setImageResource(R.drawable.a53)
        14 -> imageView.setImageResource(R.drawable.a54)
        15 -> imageView.setImageResource(R.drawable.a55)
        16 -> imageView.setImageResource(R.drawable.a56)

        17 -> imageView.setImageResource(R.drawable.a1)
        18 -> imageView.setImageResource(R.drawable.a2)
        19 -> imageView.setImageResource(R.drawable.a3)
        20 -> imageView.setImageResource(R.drawable.a4)
        21 -> imageView.setImageResource(R.drawable.a5)
        22 -> imageView.setImageResource(R.drawable.a6)
        23 -> imageView.setImageResource(R.drawable.a7)
        24 -> imageView.setImageResource(R.drawable.a8)
        25 -> imageView.setImageResource(R.drawable.a9)
        26 -> imageView.setImageResource(R.drawable.a10)
        27 -> imageView.setImageResource(R.drawable.a11)
        28 -> imageView.setImageResource(R.drawable.a12)
        29 -> imageView.setImageResource(R.drawable.a13)
        30 -> imageView.setImageResource(R.drawable.a14)
        31 -> imageView.setImageResource(R.drawable.a15)
        32 -> imageView.setImageResource(R.drawable.a16)


        33 -> imageView.setImageResource(R.drawable.a17)
        34 -> imageView.setImageResource(R.drawable.a18)
        35 -> imageView.setImageResource(R.drawable.a19)
        36 -> imageView.setImageResource(R.drawable.a20)
        37 -> imageView.setImageResource(R.drawable.a21)
        38 -> imageView.setImageResource(R.drawable.a22)
        39 -> imageView.setImageResource(R.drawable.a23)
        40 -> imageView.setImageResource(R.drawable.a24)
        41 -> imageView.setImageResource(R.drawable.a25)
        42 -> imageView.setImageResource(R.drawable.a26)
        43 -> imageView.setImageResource(R.drawable.a27)
        44 -> imageView.setImageResource(R.drawable.a28)
        45 -> imageView.setImageResource(R.drawable.a29)
        46 -> imageView.setImageResource(R.drawable.a30)
        47 -> imageView.setImageResource(R.drawable.a31)
        48 -> imageView.setImageResource(R.drawable.a32)

        49 -> imageView.setImageResource(R.drawable.a33)
        50 -> imageView.setImageResource(R.drawable.a34)
        51 -> imageView.setImageResource(R.drawable.a35)
        52 -> imageView.setImageResource(R.drawable.a36)
        53 -> imageView.setImageResource(R.drawable.a37)
        54 -> imageView.setImageResource(R.drawable.a38)
        55 -> imageView.setImageResource(R.drawable.a39)



        else -> imageView.setImageResource(R.drawable.a40)
    }
}

fun levelimage(OverallScore: Int, image: ImageView, comment: TextView){
    when{
       OverallScore < 0  -> {image.setImageResource(R.drawable.l1)
           comment.text = "Still Moving"}
        OverallScore >=1 && OverallScore < 200 -> {image.setImageResource(R.drawable.l2)
            comment.text = "Amazing!!"}
        OverallScore >=200 && OverallScore < 1000  -> {image.setImageResource(R.drawable.l3)
            comment.text = "Superb!!!"}
        OverallScore >=1000 && OverallScore < 5000  -> {image.setImageResource(R.drawable.l4)
            comment.text = "Wonderful!!"}
        OverallScore >=5000 && OverallScore < 15000  -> {image.setImageResource(R.drawable.l5)
            comment.text = "You are a CHAMPION!!!"}
        OverallScore >=15000 -> {image.setImageResource(R.drawable.l6)
            comment.text = "Congratulations CHAMPION!!!"}
    }
}


