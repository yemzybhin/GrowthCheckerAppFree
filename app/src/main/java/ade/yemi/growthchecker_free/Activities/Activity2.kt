package ade.yemi.growthchecker_free.Activities

import ade.yemi.growthchecker_free.Fragments.Pages.AboutsPage
import ade.yemi.growthchecker_free.Fragments.Pages.Startchallenge
import ade.yemi.growthchecker_free.Fragments.Pages.challengeview
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.challengecommunicator
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*
import kotlin.concurrent.schedule

class Activity2 : AppCompatActivity(), challengecommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2)
        var cancl = findViewById<CardView>(R.id.cd_challengestartcancel)
        replacefragment(intent.getStringExtra("ActivityToset")!!)


        MobileAds.initialize(this) {}
        var mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        cancl.setOnClickListener {
            cancl.clicking()
            cancl.shortvibrate()
            Timer().schedule(100) {
                startActivity(Intent(this@Activity2, MainActivity::class.java))
                finish()
            }
        }

    }
    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
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