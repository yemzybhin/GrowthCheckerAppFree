package ade.yemi.growthchecker.Activities

import ade.yemi.growthchecker.Fragments.Pages.*
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replacefragment(Homepage())

        var challengesscrollview = findViewById<CardView>(R.id.cd_homechallangeswidget)

        var homecard = findViewById<CardView>(R.id.cd_home)
        var analyticscard = findViewById<CardView>(R.id.cd_analytics)
        var achievementscard = findViewById<CardView>(R.id.cd_achievements)
        var notescard = findViewById<CardView>(R.id.cd_notes)
        var tipscard = findViewById<CardView>(R.id.cd_tips)

        var fourteen = findViewById<CardView>(R.id.cd_14days)
        var thirty = findViewById<CardView>(R.id.cd_30days)
        var sixty = findViewById<CardView>(R.id.cd_60days)
        var hundred = findViewById<CardView>(R.id.cd_100days)
        var twohundred = findViewById<CardView>(R.id.cd_200days)

        var homeview= findViewById<View>(R.id.v_home)
        var analyticsview = findViewById<View>(R.id.v_analytics)
        var achievementsview = findViewById<View>(R.id.v_achievements)
        var notesview = findViewById<View>(R.id.v_notes)
        var tipsview = findViewById<View>(R.id.v_tips)

        var homeimage= findViewById<ImageView>(R.id.iv_home)
        var analyticsimage = findViewById<ImageView>(R.id.iv_analytics)
        var achievementsimage = findViewById<ImageView>(R.id.iv_achievements)
        var notesimage= findViewById<ImageView>(R.id.iv_notes)
        var tipsimage = findViewById<ImageView>(R.id.iv_tips)
        UpdateOnclickElement(listOf(analyticsview, achievementsview, notesview, tipsview))

        var challengeintent = Intent(this@MainActivity, ChallengeStart::class.java)

        fourteen.setOnClickListener {
                fourteen.clicking()
                vibrate(10)
               Timer().schedule(100) {
                   challengeintent.putExtra("Challenge", 1)
                   startActivity(Intent(challengeintent))
               }
        }
        thirty.setOnClickListener {
                thirty.clicking()
                vibrate(10)
                Timer().schedule(100) {
                    challengeintent.putExtra("Challenge", 2)
                    startActivity(Intent(challengeintent))
                }
        }
        sixty.setOnClickListener {
                sixty.clicking()
                vibrate(10)
                Timer().schedule(100) {
                    challengeintent.putExtra("Challenge", 3)
                    startActivity(Intent(challengeintent))
                }
        }
        hundred.setOnClickListener {
                hundred.clicking()
                vibrate(10)
                Timer().schedule(100) {
                    challengeintent.putExtra("Challenge", 4)
                    startActivity(Intent(challengeintent))
                }
        }
        twohundred.setOnClickListener {
                twohundred.clicking()
                vibrate(10)
                Timer().schedule(100) {
                    challengeintent.putExtra("Challenge", 5)
                    startActivity(Intent(challengeintent))

                }
        }

        homecard.setOnClickListener {
            vibrate(10)
            homeview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.VISIBLE
            replacefragment(Homepage())
            UpdateOnclickElement(listOf(analyticsview, achievementsview, notesview, tipsview))
            homeimage.setImageResource(R.drawable.home1)
            analyticsimage.setImageResource(R.drawable.analytics2)
            achievementsimage.setImageResource(R.drawable.achievement2)
            notesimage.setImageResource(R.drawable.note2)
            tipsimage.setImageResource(R.drawable.tips2)

            setpageclickimage(listOf(notesimage, analyticsimage, achievementsimage, tipsimage), listOf(R.drawable.note2, R.drawable.analytics2, R.drawable.achievement2, R.drawable.tips2), homeimage, R.drawable.home1)

        }
        analyticscard.setOnClickListener {
            vibrate(10)
            analyticsview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            replacefragment(AnalyticsPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, tipsview))
            setpageclickimage(listOf(homeimage, notesimage, achievementsimage, tipsimage), listOf(R.drawable.home2, R.drawable.note2, R.drawable.achievement2, R.drawable.tips2), analyticsimage, R.drawable.analytics1)

        }
        achievementscard.setOnClickListener {
            vibrate(10)
            achievementsview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            replacefragment(AchievementsPage())
            UpdateOnclickElement(listOf(homeview, analyticsview, notesview, tipsview))
            setpageclickimage(listOf(homeimage, analyticsimage, notesimage, tipsimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.note2, R.drawable.tips2), achievementsimage, R.drawable.achievement1)


        }
        notescard.setOnClickListener {
            vibrate(10)
            notesview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            replacefragment(NotesPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, analyticsview, tipsview))
            setpageclickimage(listOf(homeimage, analyticsimage, achievementsimage, tipsimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.achievement2, R.drawable.tips2), notesimage, R.drawable.note2)

        }
        tipscard.setOnClickListener {
            vibrate(10)
            tipsview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            replacefragment(TipsPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, analyticsview))
            setpageclickimage(listOf(homeimage, analyticsimage, achievementsimage, notesimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.achievement2, R.drawable.note2), tipsimage, R.drawable.tips1)
        }
    }
    private fun replacefragment(fragment:Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fg_home, fragment)
        fragmentTransaction.commit()
    }
    private fun UpdateOnclickElement(views: List<View>){
        for (i in views){
            i.visibility = View.GONE
        }
    }
    private fun setpageclickimage(imagelist: List<ImageView>, images: List<Int>, setimage: ImageView, image: Int){
        for (i in 0..imagelist.size -1){
            imagelist[i].setImageResource(images[i])
        }
        setimage.setImageResource(image)
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