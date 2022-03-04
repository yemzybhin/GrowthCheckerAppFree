package ade.yemi.growthchecker.Activities

import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.*
import ade.yemi.growthchecker.PopUp_Fragments.DailyAssessment
import ade.yemi.growthchecker.PopUp_Fragments.Menu
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private var assessmentnotification = false
    private var ne = ""
    private var ungoingchallenge = false
    private var ungoinchallenge = false
    private val challengesscrollview: CardView by lazy {
        findViewById(R.id.cd_homechallangeswidget)
    }
    private val homeview: View by lazy {
        findViewById(R.id.v_home)
    }
    private val analyticsview: View by lazy {
        findViewById(R.id.v_analytics)
    }
    private val achievementsview: View by lazy {
        findViewById(R.id.v_achievements)
    }
    private val notesview: View by lazy {
        findViewById(R.id.v_notes)
    }
    private val tipsview: View by lazy {
        findViewById(R.id.v_tips)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replacefragment(Homepage())

        var menubutton = findViewById<CardView>(R.id.cd_homemenu)
        var notificationbutton = findViewById<CardView>(R.id.cd_assessmentnotification)

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

        var homeimage= findViewById<ImageView>(R.id.iv_home)
        var analyticsimage = findViewById<ImageView>(R.id.iv_analytics)
        var achievementsimage = findViewById<ImageView>(R.id.iv_achievements)
        var notesimage= findViewById<ImageView>(R.id.iv_notes)
        var tipsimage = findViewById<ImageView>(R.id.iv_tips)

        lifecycleScope.launch {
            val pushresult = async {
                DataStoreManager.getBoolean( this@MainActivity , "challengeungoing")
            }
            val pushresult1 = async {
                DataStoreManager.getBoolean( this@MainActivity , "assessmentnotification")
            }
            ungoinchallenge = pushresult.await()
            ungoingchallenge = ungoinchallenge

            assessmentnotification = pushresult1.await()


            if (assessmentnotification == true && ungoingchallenge == true){
                notificationbutton.visibility = View.VISIBLE
                notificationbutton.animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.notification)
            }else{
                notificationbutton.visibility = View.GONE
            }

            if (ungoingchallenge == true){
                challengesscrollview.visibility = View.GONE
            }else{
                challengesscrollview.visibility = View.VISIBLE
            }
        UpdateOnclickElement(listOf(analyticsview, achievementsview, notesview, tipsview))
        var dialog = Menu()
        menubutton.setOnClickListener {
            menubutton.clicking()
            menubutton.shortvibrate()
            Timer().schedule(100) {
            }
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog)
            dialog.show(supportFragmentManager, "Menudialog")
        }
            var dialog2 = DailyAssessment()
            notificationbutton.setOnClickListener {
                notificationbutton.shortvibrate()
                showassessmentdialog(dialog2)
            }

        var challengeintent = Intent(this@MainActivity, Activity2::class.java)
        fourteen.setOnClickListener {

            fourteen.clicking()
            fourteen.shortvibrate()
               Timer().schedule(100) {
                   ne = "challenge14"
                   savedata()
                   challengeintent.putExtra("ActivityToset", "challengeview")
                   startActivity(Intent(challengeintent))
               }
        }
        thirty.setOnClickListener {
            ne = "challenge30"
            savedata()
                thirty.clicking()
                thirty.shortvibrate()
                Timer().schedule(100) {
                    challengeintent.putExtra("ActivityToset", "challengeview")
                    startActivity(Intent(challengeintent))
                }
        }
        sixty.setOnClickListener {
            ne = "challenge60"
            savedata()
            sixty.clicking()
               sixty.shortvibrate()
                Timer().schedule(100) {
                    challengeintent.putExtra("ActivityToset", "challengeview")
                    startActivity(Intent(challengeintent))
                }
        }
        hundred.setOnClickListener {
            ne = "challenge100"
            savedata()
                hundred.clicking()
                hundred.shortvibrate()
                Timer().schedule(100) {
                    challengeintent.putExtra("ActivityToset", "challengeview")
                    startActivity(Intent(challengeintent))
                }
        }
        twohundred.setOnClickListener {
            ne = "challenge200"
            savedata()
                twohundred.clicking()
                twohundred.shortvibrate()
                Timer().schedule(100) {
                    challengeintent.putExtra("ActivityToset", "challengeview")
                    startActivity(Intent(challengeintent))

                }
        }

        homecard.setOnClickListener {
            homecard.shortvibrate()
            homeview.visibility = View.VISIBLE

            if (ungoingchallenge == true){
                challengesscrollview.visibility = View.GONE
            }else{
                challengesscrollview.visibility = View.VISIBLE
            }
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
            analyticscard.shortvibrate()
            analyticsview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            replacefragment(AnalyticsPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, tipsview))
            setpageclickimage(listOf(homeimage, notesimage, achievementsimage, tipsimage), listOf(R.drawable.home2, R.drawable.note2, R.drawable.achievement2, R.drawable.tips2), analyticsimage, R.drawable.analytics1)

        }
        achievementscard.setOnClickListener {
            achievementscard.shortvibrate()
            achievementsview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            replacefragment(AchievementsPage())
            UpdateOnclickElement(listOf(homeview, analyticsview, notesview, tipsview))
            setpageclickimage(listOf(homeimage, analyticsimage, notesimage, tipsimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.note2, R.drawable.tips2), achievementsimage, R.drawable.achievement1)


        }
        notescard.setOnClickListener {
            notescard.shortvibrate()
            notesview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            replacefragment(NotesPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, analyticsview, tipsview))
            setpageclickimage(listOf(homeimage, analyticsimage, achievementsimage, tipsimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.achievement2, R.drawable.tips2), notesimage, R.drawable.note2)

        }
        tipscard.setOnClickListener {
            tipscard.shortvibrate()
            tipsview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            replacefragment(TipsPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, analyticsview))
            setpageclickimage(listOf(homeimage, analyticsimage, achievementsimage, notesimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.achievement2, R.drawable.note2), tipsimage, R.drawable.tips1)
        }
    }}

    internal fun ShowMainpopUp(view: View, dialogFragment: DialogFragment){
        view.clicking()
        view.shortvibrate()
        Timer().schedule(100) {
        }
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog)
        dialogFragment.show(supportFragmentManager, "$dialogFragment popup")
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }
    internal fun replacefragment(fragment:Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fg_home, fragment)
        fragmentTransaction.commit()
    }
    internal fun ChangeToAchivement(fragment: Fragment){
        achievementsview.visibility = View.VISIBLE
        challengesscrollview.visibility = View.GONE
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fg_home, fragment)
        fragmentTransaction.commit()
        UpdateOnclickElement(listOf(homeview, analyticsview, notesview, tipsview))
    }
    internal fun ChangeToAnalytics(fragment: Fragment){
        analyticsview.visibility = View.VISIBLE
        challengesscrollview.visibility = View.GONE
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fg_home, fragment)
        fragmentTransaction.commit()
        UpdateOnclickElement(listOf(homeview, achievementsview, notesview, tipsview))
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
    private fun savedata(){
        lifecycleScope.launch {
            DataStoreManager.saveString(this@MainActivity, "challengeviewChallenge", ne)
        }
    }
    internal fun showassessmentdialog(dialogFragment: DialogFragment){
            dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog)
            dialogFragment.show(supportFragmentManager, "Assessmentdialog")
    }
//    private fun showMenupopup( ){
//        var popup = Dialog(this)
//        popup.setCancelable(true)
//        popup.setContentView(R.layout.fragment_menu)
//        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        popup.show()
//
//        var cancel = popup.findViewById<CardView>(R.id.cd_homemenucancel)
//        cancel.setOnClickListener {
//            cancel.clicking()
//            cancel.shortvibrate()
//            Timer().schedule(100) {
//            }
//            popup.dismiss()
//        }
//    }
}