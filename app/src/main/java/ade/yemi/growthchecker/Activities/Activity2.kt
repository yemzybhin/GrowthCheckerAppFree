package ade.yemi.growthchecker.Activities

import ade.yemi.growthchecker.AlarmReceiver
import ade.yemi.growthchecker.Fragments.Pages.AboutsPage
import ade.yemi.growthchecker.Fragments.Pages.Startchallenge
import ade.yemi.growthchecker.Fragments.Pages.challengeview
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.challengecommunicator
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import kotlin.concurrent.schedule

class Activity2 : AppCompatActivity(), challengecommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2)
        var cancl = findViewById<CardView>(R.id.cd_challengestartcancel)
        replacefragment(intent.getStringExtra("ActivityToset")!!)


        cancl.setOnClickListener {
            cancl.clicking()
            cancl.shortvibrate()
            Timer().schedule(100) {
                startActivity(Intent(this@Activity2, MainActivity::class.java))
                finish()
            }
        }

    }

    private fun replacefragment(string: String) {
        var fragment = Fragment()
        when(string){
            "challengeview" -> fragment = challengeview()
            "Aboutspage" -> fragment = AboutsPage()
        }
        var bundle = Bundle()
        fragment.arguments = bundle
        bundle.putString("challengeviewChallenge", intent.getStringExtra("challengeviewChallenge"))
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fr_activity2,fragment)
        fragmentTransaction.commit()
    }
    override fun passchallengedetails(challenge: String) {
        var fragment = Startchallenge()
        val bundle = Bundle()
        bundle.putString("challengeviewchallenge", challenge)
        fragment.arguments = bundle
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fr_activity2, fragment)
        fragmentTransaction.commit()
    }
}