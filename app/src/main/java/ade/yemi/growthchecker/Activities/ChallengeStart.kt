package ade.yemi.growthchecker.Activities

import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import java.util.*
import kotlin.concurrent.schedule

class ChallengeStart : AppCompatActivity() {
    private var point = 0
    private var days = 0
    private var stage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_start)

        var image = findViewById<ImageView>(R.id.iv_challengestart)
        var cancl = findViewById<CardView>(R.id.cd_challengestartcancel)
        var next = findViewById<CardView>(R.id.cd_challengestartnext)

        var t0 = findViewById<TextView>(R.id.tv_challengestart0)
        var t1 = findViewById<TextView>(R.id.tv_challengestart1)
        var t2 = findViewById<TextView>(R.id.tv_challengestart2)
        var t3 = findViewById<TextView>(R.id.tv_challengestart3)
        var t4 = findViewById<TextView>(R.id.tv_challengestart4)
        var t5 = findViewById<TextView>(R.id.tv_challengestart5)
        var t6 = findViewById<TextView>(R.id.tv_challengestart6)

        var getIntent = intent.getIntExtra("Challenge", 9)
        values(getIntent, image)

        var text0 = "$days Days Challenge"
        var text1 = "Stage $stage challenge."
        var text2 = "$point points."
        var text3 = "This challenge lasts for $days days. Challenge has 3 positive point adders and one negative point adder."
        var text4 = "There are three ( 3 ) positive points adders in this challenge. First adder is the most significant with 10 positive points, second is the next most significant with 7 positive points, while the third is the least significant with 5 positive points.  Based on significance,3 daily tasks to stop addiction are chosen.  They are arranged in their order of significance.  Each corresponding to the aforementioned positive point adders.  Performing any or all of these tasks lead to the addition of corresponding points to total previously harnessed points."
        var text5 = "For this challenge, a daily indulgence in the behavioural concern leads to the removal of a total of 50 points. The negative adder reduces total points so much, hence should be avoided on a daily basis."
        var text6 = "On a daily basis till the end of the challenge, notifications are sent at the end of the day to assess the activities of the day as a determinant of the points to be added for the respective day. Skipped days will be required to fill just in cases of missed assessment."

        t0.text = text0
        t1.text = text1
        t2.text = text2
        t3.text = text3
        t4.text = text4
        t5.text = text5
        t6.text = text6

        cancl.setOnClickListener {
            cancl.clicking()
            vibrate(10)
            Timer().schedule(100) {
                startActivity(Intent(this@ChallengeStart, MainActivity::class.java))
            }
        }
        next.setOnClickListener {
            next.clicking()
            vibrate(10)
//            Timer().schedule(100) {
//                startActivity(Intent(this@ChallengeStart, MainActivity::class.java))
//            }
        }

    }
private fun values(valx: Int, ige: ImageView){
    when (valx) {
        1 -> {
            point = 308
            days = 14
            stage = 1
            ige.setImageResource(R.drawable.forteendays)
        }
        2-> {
            point = 660
            days = 30
            stage = 2
            ige.setImageResource(R.drawable.thirtydays)
        }
        3-> {
            point = 1320
            days = 60
            stage = 3
            ige.setImageResource(R.drawable.sixtydays)
        }
        4-> {
            point = 2200
            days = 100
            stage = 4
            ige.setImageResource(R.drawable.hundreddays)
        }
        5 -> {
            point = 4400
            days = 200
            stage = 5
            ige.setImageResource(R.drawable.twohundreddays)
        }
    }
}
    private fun vibrate(millisecond: Long) {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(millisecond, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(millisecond)
        }
    }
}