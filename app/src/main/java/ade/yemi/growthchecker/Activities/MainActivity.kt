package ade.yemi.growthchecker.Activities

import ade.yemi.growthchecker.Data.AlarmInfo
import ade.yemi.growthchecker.Data.AllQuotes
import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.*
import ade.yemi.growthchecker.PopUp_Fragments.DailyAssessment
import ade.yemi.growthchecker.PopUp_Fragments.Menu
import ade.yemi.growthchecker.PopUp_Fragments.Popup_AddNote
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.*
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), NoteCommunicator{
    private var counterr = 0
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
    private val notificationbutton: CardView by lazy {
        findViewById(R.id.cd_assessmentnotification)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replacefragment(Homepage())

        var menubutton = findViewById<CardView>(R.id.cd_homemenu)

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

//        var incomming = intent?.getStringExtra("assessmentnotification11")
//        Toast.makeText(this@MainActivity, "$incomming This is it", Toast.LENGTH_LONG).show()



        UpdateOnclickElement(listOf(analyticsview, achievementsview, notesview, tipsview))
        lifecycleScope.launch {
            val pushresult = async {
                DataStoreManager.getBoolean( this@MainActivity , "challengeungoing")
            }
            val pushresult2 = async {
                DataStoreManager.getInt( this@MainActivity , "currentquote")
            }

            counterr = pushresult2.await()
            ungoinchallenge = pushresult.await()
            var ungoingchallenge = ungoinchallenge

            var alarmInfo = AlarmInfo(this@MainActivity)
            var tome = alarmInfo.getassess()

            var incoming = intent?.getBooleanExtra("firstopen", false)


            if (ungoingchallenge == true){
                challengesscrollview.visibility = View.GONE
            }else{
                challengesscrollview.visibility = View.VISIBLE
            }

            if (tome == true && ungoingchallenge == true){
                notificationbutton.visibility = View.VISIBLE
                notificationbutton.animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.notification)
            }else{
                notificationbutton.visibility = View.GONE
            }


        var dialog = Menu()
        menubutton.setOnSingleClickListener {
            menubutton.clicking()
            menubutton.shortvibrate()
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog)
            dialog.show(supportFragmentManager, "Menudialog")

        }
            var dialog2 = DailyAssessment()
            notificationbutton.setOnSingleClickListener{
                notificationbutton.visibility = View.GONE
                notificationbutton.shortvibrate()
                showassessmentdialog(dialog2)
            }

        var challengeintent = Intent(this@MainActivity, Activity2::class.java)
        fourteen.setOnSingleClickListener {
            fourteen.clicking()
            fourteen.shortvibrate()
               Timer().schedule(100) {
                   challengeintent.putExtra("challengeviewChallenge", "challenge14")
                   challengeintent.putExtra("ActivityToset", "challengeview")
                   startActivity(Intent(challengeintent))
               }
        }
        thirty.setOnSingleClickListener {
                thirty.clicking()
                thirty.shortvibrate()
                Timer().schedule(100) {

                }
            challengeintent.putExtra("ActivityToset", "challengeview")
            challengeintent.putExtra("challengeviewChallenge", "challenge30")
            startActivity(Intent(challengeintent))
        }
        sixty.setOnSingleClickListener {
            sixty.clicking()
               sixty.shortvibrate()
                Timer().schedule(100) {

                }
            challengeintent.putExtra("challengeviewChallenge", "challenge60")
            challengeintent.putExtra("ActivityToset", "challengeview")
            startActivity(Intent(challengeintent))
        }
        hundred.setOnSingleClickListener {
                hundred.clicking()
                hundred.shortvibrate()
                Timer().schedule(100) {

                }
            challengeintent.putExtra("challengeviewChallenge", "challenge100")
            challengeintent.putExtra("ActivityToset", "challengeview")
            startActivity(Intent(challengeintent))
        }
        twohundred.setOnSingleClickListener {
                twohundred.clicking()
                twohundred.shortvibrate()
                Timer().schedule(100) {

                }
            challengeintent.putExtra("challengeviewChallenge", "challenge200")
            challengeintent.putExtra("ActivityToset", "challengeview")
            startActivity(Intent(challengeintent))
        }

        homecard.setOnSingleClickListener {
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
        analyticscard.setOnSingleClickListener {
            analyticscard.shortvibrate()
            challengesscrollview.visibility = View.GONE
            analyticsview.visibility = View.VISIBLE
            replacefragment(AnalyticsPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, tipsview))
            setpageclickimage(listOf(homeimage, notesimage, achievementsimage, tipsimage), listOf(R.drawable.home2, R.drawable.note2, R.drawable.achievement2, R.drawable.tips2), analyticsimage, R.drawable.analytics1)

        }
        achievementscard.setOnSingleClickListener {
            achievementscard.shortvibrate()
            challengesscrollview.visibility = View.GONE
            achievementsview.visibility = View.VISIBLE

            replacefragment(AchievementsPage())
            UpdateOnclickElement(listOf(homeview, analyticsview, notesview, tipsview))
            setpageclickimage(listOf(homeimage, analyticsimage, notesimage, tipsimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.note2, R.drawable.tips2), achievementsimage, R.drawable.achievement1)


        }
        notescard.setOnSingleClickListener {
            notescard.shortvibrate()
            challengesscrollview.visibility = View.GONE
            notesview.visibility = View.VISIBLE
            replacefragment(NotesPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, analyticsview, tipsview))
            setpageclickimage(listOf(homeimage, analyticsimage, achievementsimage, tipsimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.achievement2, R.drawable.tips2), notesimage, R.drawable.note1)

        }
        tipscard.setOnSingleClickListener {
            tipscard.shortvibrate()
            challengesscrollview.visibility = View.GONE
            tipsview.visibility = View.VISIBLE
            replacefragment(TipsPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, analyticsview))
            setpageclickimage(listOf(homeimage, analyticsimage, achievementsimage, notesimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.achievement2, R.drawable.note2), tipsimage, R.drawable.tips1)
        }

            if (incoming == true){
                dailyquote(counterr)
            }

    }
    }

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
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        fragmentTransaction.replace(R.id.fg_home, fragment)
        fragmentTransaction.commit()
    }
    internal fun ChangeToAchivement(fragment: Fragment){
        achievementsview.visibility = View.VISIBLE
        challengesscrollview.visibility = View.GONE
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        fragmentTransaction.replace(R.id.fg_home, fragment)
        fragmentTransaction.commit()
        UpdateOnclickElement(listOf(homeview, analyticsview, notesview, tipsview))
    }
    internal fun ChangeToAnalytics(fragment: Fragment){
        analyticsview.visibility = View.VISIBLE
        challengesscrollview.visibility = View.GONE
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
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
    internal fun showassessmentdialog(dialogFragment: DialogFragment){
            dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog)
            dialogFragment.show(supportFragmentManager, "Assessmentdialog")
    }
    override fun passnotedetails(id: Int, title: String, content: String, notetype: String, noteuse: Int) {
        var dialog = Popup_AddNote()
        val bundle = Bundle()
        bundle.putString("notetype", "Edit")
        bundle.putInt("id", id)
        bundle.putString("title", title)
        bundle.putString("content", content)
        bundle.putString("notetype1", notetype)
        bundle.putInt("noteuse", noteuse)
        dialog.arguments = bundle
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog)
        dialog.show(supportFragmentManager, "fefef")
    }
    private fun dailyquote(counter: Int){
        var popup = Dialog(this)
        popup.setCancelable(true)
        popup.setContentView(R.layout.sharequotee)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()

        var quote = popup.findViewById<TextView>(R.id.quoteshare)
        var author = popup.findViewById<TextView>(R.id.authorshare)
        var share = popup.findViewById<Button>(R.id.quotesharebutton)
        var cardtoshare = popup.findViewById<CardView>(R.id.cardquotetoshare)
        var cancel = popup.findViewById<CardView>(R.id.quotecancel)

        var quotes = AllQuotes()
        var count = counter + 1


        if (count > quotes.size-1){
            count = 0
            quote.text = "\"${AllQuotes()[count].quote}\""
            author.text = "-${AllQuotes()[count].author}"
            savedata(count+1)
        }else{
            quote.text = "\"${AllQuotes()[count].quote}\""
            author.text = "-${AllQuotes()[count].author}"
            if (count > quotes.size -1){
                savedata(0)
            }else{
                savedata(count)
            }

        }
        cancel.setOnClickListener {
            cancel.clicking()
            cancel.shortvibrate()
            popup.dismiss()
        }

        share.setOnClickListener {
            share.clicking()
            share.shortvibrate()
            toshare(this, cardtoshare)
        }

    }
    private fun savedata(counter: Int){
        lifecycleScope.launch {
            DataStoreManager.saveInt(this@MainActivity, "currentquote", counter)
        }
    }
}