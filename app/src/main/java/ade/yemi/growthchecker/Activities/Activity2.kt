package ade.yemi.growthchecker.Activities

import ade.yemi.growthchecker.Fragments.Pages.challengeview
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.concurrent.schedule

class Activity2 : AppCompatActivity() {
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
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        when(string){
            "challengeview" -> fragmentTransaction.replace(R.id.fr_activity2, challengeview())

        }

        fragmentTransaction.commit()
    }
//    private fun tofragment(fragment: Fragment){
//        var bundle = Bundle()
//        fragment.arguments = bundle
//        bundle.putInt("Challengetoshow", intent.getIntExtra("Challenge", 0))
//        replacefragment(fragment)
//    }

    internal fun SetToStart(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fr_activity2, fragment)
        fragmentTransaction.commit()
    }
}