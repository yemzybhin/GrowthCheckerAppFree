package ade.yemi.growthchecker_free.Activities

import ade.yemi.growthchecker_free.Fragments.AllAds
import ade.yemi.growthchecker_free.Fragments.Pages.AboutsPage
import ade.yemi.growthchecker_free.Fragments.Pages.Startchallenge
import ade.yemi.growthchecker_free.Fragments.Pages.challengeview
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.challengecommunicator
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*
import kotlin.concurrent.schedule

class Activity2 : AppCompatActivity(), challengecommunicator {
    private val cancl: CardView by lazy {
        findViewById(R.id.cd_challengestartcancel)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2)
        replacefragment(intent.getStringExtra("ActivityToset")!!)


        cancl.setOnClickListener {
            cancl.clicking()
            cancl.shortvibrate()
            Handler().postDelayed({
                startActivity(Intent(this@Activity2, MainActivity::class.java))
                finish()
            }, 0)
            loading()
        }

    }
    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun loading(){
        var load = Dialog(this)
        load.setCancelable(false)
        load.setContentView(R.layout.loading_popuup)
        load.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        load.show()
    }
    internal fun replacefragment(string: String) {
        var fragment = Fragment()
        when(string){
            "challengeview" ->{
                fragment = challengeview()
                cancl.visibility = View.VISIBLE
            }
            "Aboutspage" ->{
                fragment = AboutsPage()
                cancl.visibility = View.GONE
            }
            "AllAds" -> {
                fragment = AllAds()
                cancl.visibility = View.GONE
            }
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