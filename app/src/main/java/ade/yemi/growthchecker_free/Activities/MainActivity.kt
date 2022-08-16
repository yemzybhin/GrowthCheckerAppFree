package ade.yemi.growthchecker_free.Activities

import ade.yemi.growthchecker_free.BuildConfig
import ade.yemi.growthchecker_free.Data.AlarmInfo
import ade.yemi.growthchecker_free.Data.AllQuotes
import ade.yemi.growthchecker_free.Data.DataStoreManager
import ade.yemi.growthchecker_free.Data.Preferencestuff
import ade.yemi.growthchecker_free.Fragments.Pages.*
import ade.yemi.growthchecker_free.PopUp_Fragments.*
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.*
import ade.yemi.moreapps.Network.RetrofitInterface
import ade.yemi.moreapps.Network.RetrofitInterface1
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.*
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_behavioural_concern.*
import kotlinx.android.synthetic.main.fragment_behavioural_concern.view.*
import kotlinx.android.synthetic.main.fragment_change_images.*
import kotlinx.android.synthetic.main.fragment_change_images.view.*
import kotlinx.android.synthetic.main.loading_popuup.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), NoteCommunicator{
    private var counterr = 0
    private var ungoinchallenge = false
    private val challengesscrollview: CardView by lazy {
        findViewById(R.id.cd_homechallangeswidget)
    }
    private  val load:Dialog by lazy {
        Dialog(this)
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
        loading()


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

        var pointcount = findViewById<TextView>(R.id.pointcount)

        var homeimage= findViewById<ImageView>(R.id.iv_home)
        var analyticsimage = findViewById<ImageView>(R.id.iv_analytics)
        var achievementsimage = findViewById<ImageView>(R.id.iv_achievements)
        var notesimage= findViewById<ImageView>(R.id.iv_notes)
        var tipsimage = findViewById<ImageView>(R.id.iv_tips)

        var updatelayout = findViewById<LinearLayout>(R.id.updatelayout)
        var updatebutton = findViewById<Button>(R.id.updatebutton)
        var cancelupdate = findViewById<CardView>(R.id.cancelupdate)
        updatelayout.visibility = View.GONE

        pointcount.text = "${Preferencestuff(this).getPoint()} Tokens"

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
            notificationbutton.setOnSingleClickListener{
               checkpoint("Daily Assessment", 20)
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
        homecard.setOnClickListener {
            homecard.shortvibrate()

            //checkpoint()
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
                challengesscrollview.visibility = View.GONE
                analyticsview.visibility = View.VISIBLE
                replacefragment(AnalyticsPage())
                UpdateOnclickElement(listOf(homeview, achievementsview, notesview, tipsview))
                setpageclickimage(listOf(homeimage, notesimage, achievementsimage, tipsimage), listOf(R.drawable.home2, R.drawable.note2, R.drawable.achievement2, R.drawable.tips2), analyticsimage, R.drawable.analytics1)

        }
        achievementscard.setOnClickListener {
            achievementscard.shortvibrate()
                challengesscrollview.visibility = View.GONE
                achievementsview.visibility = View.VISIBLE

                replacefragment(AchievementsPage())
                UpdateOnclickElement(listOf(homeview, analyticsview, notesview, tipsview))
                setpageclickimage(listOf(homeimage, analyticsimage, notesimage, tipsimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.note2, R.drawable.tips2), achievementsimage, R.drawable.achievement1)
           }
        notescard.setOnClickListener {
            notescard.shortvibrate()
                challengesscrollview.visibility = View.GONE
                notesview.visibility = View.VISIBLE
                replacefragment(NotesPage())
                UpdateOnclickElement(listOf(homeview, achievementsview, analyticsview, tipsview))
                setpageclickimage(listOf(homeimage, analyticsimage, achievementsimage, tipsimage), listOf(R.drawable.home2, R.drawable.analytics2, R.drawable.achievement2, R.drawable.tips2), notesimage, R.drawable.note1)
          }
        tipscard.setOnClickListener {
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
            Handler().postDelayed({
            cancelload()
             }, 50)
            Handler().postDelayed({
                getversion(updatelayout, updatebutton, cancelupdate)
            }, 2000)


    }
    }

    private fun checkpoint(destination: String, requiredpoints : Int){
        var view = Dialog(this)
        view.setCancelable(true)
        view.setContentView(R.layout.pointschecker)
        view.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.show()

        var title = view.findViewById<TextView>(R.id.title)
        var points = view.findViewById<TextView>(R.id.points)
        var comment = view.findViewById<TextView>(R.id.comment)
        var proceed = view.findViewById<TextView>(R.id.approval)


        var prefereceStuffs = Preferencestuff(this)
        var currentpoints = prefereceStuffs.getPoint()

        title.text = "${destination.toUpperCase()} REQUIRES $requiredpoints Tokens"
        points.text = "$currentpoints Tokens"

        if (currentpoints >= requiredpoints){
            comment.text = "You have enough Tokens"
            proceed.text = "PROCEED"
            proceed.setBackgroundResource(R.drawable.stroke12)
        }else{
            comment.text = "You do not have enough Tokens"
            proceed.text = "GET FREE Tokens"
            proceed.setBackgroundResource(R.drawable.stroke11)
        }

        proceed.setOnClickListener {
            proceed.clicking()
            if (currentpoints >= requiredpoints){
                prefereceStuffs.setPoint(currentpoints - requiredpoints)
                Toast.makeText(this, "$requiredpoints Tokens Removed", Toast.LENGTH_SHORT).show()
                when(destination){
                    "Daily Assessment" -> {
                        Handler().postDelayed({
                            notificationbutton.visibility = View.GONE
                            notificationbutton.shortvibrate()
                            showassessmentdialog(DailyAssessment())
                        }, 0)
                    }
                    "SAVED QUESTIONS" -> {

                    }
                    "PAST QUESTIONS" -> {

                    }
                    "LIFE CHANGER" -> {

                    }

                }
            }else{
                Handler().postDelayed({
                    var intent = Intent(this, Activity2::class.java)
                    intent.putExtra("ActivityToset", "Aboutspage")
                    startActivity(intent)
                }, 0)
                loading()
            }

        }

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
        loading()
        Handler().postDelayed({
            achievementsview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            fragmentTransaction.replace(R.id.fg_home, fragment)
            fragmentTransaction.commit()
            UpdateOnclickElement(listOf(homeview, analyticsview, notesview, tipsview))
        }, 0)

    }
    internal fun ChangeToAnalytics(fragment: Fragment){
        loading()
        Handler().postDelayed({
            analyticsview.visibility = View.VISIBLE
            challengesscrollview.visibility = View.GONE
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            fragmentTransaction.replace(R.id.fg_home, fragment)
            fragmentTransaction.commit()
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, tipsview))
        }, 0)

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


        var alarmInfo = AlarmInfo(this)
        if (count > quotes.size-1){
            count = 0
            quote.text = "\"${AllQuotes()[count].quote}\""
            author.text = "-${AllQuotes()[count].author}"
            savedata(count+1)
            alarmInfo.setQuoteIndex(count + 1)
        }else{
            quote.text = "\"${AllQuotes()[count].quote}\""
            author.text = "-${AllQuotes()[count].author}"
            if (count > quotes.size -1){
                savedata(0)
                alarmInfo.setQuoteIndex(0)
            }else{
                savedata(count)
                alarmInfo.setQuoteIndex(count)
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
    internal fun loading(){
        load.setCancelable(false)
        load.setContentView(R.layout.loading_popuup)
        load.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        load.show()
    }

    internal fun shoowinfo(){
        var popup = Dialog(this)
        popup.setCancelable(false)
        popup.setContentView(R.layout.fragment_myinfo1)
       popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
       popup.show()


       var cancel = popup.findViewById<CardView>(R.id.cd_myinfopopupcancel1)
       var title = popup.findViewById<TextView>(R.id.tv_myinfotitle)
       var namee = popup.findViewById<TextView>(R.id.tv_myinfoname)
       var agee = popup.findViewById<TextView>(R.id.tv_myinfoage)
       var image = popup.findViewById<ImageView>(R.id.iv_myinfoprofile1)
       var edit = popup.findViewById<Button>(R.id.btn_myinfoedit1)


        var preferencestuff = Preferencestuff(this)
        var name = preferencestuff.getUserAttributes("userName")
        var age = preferencestuff.getUserAttributes("userAge")
        var picnum = preferencestuff.getUserAttributes("displayImage")

        title.text = "Hi, $name"
        namee.text = name
        agee.text = age.toString()
        setimage(image, picnum!!.toInt())

       edit.setOnClickListener {
               popup.dismiss()
               myinfo2()
       }
       cancel.setOnClickListener {
           cancel.clicking()
           cancel.shortvibrate()
           popup.dismiss()
       }
    }

    private fun myinfo2() {
        var popup = Dialog(this)
        popup.setCancelable(false)
        popup.setContentView(R.layout.fragment_myinfo2)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()

        var image = popup.findViewById<ImageView>(R.id.iv_myinfo2image)
        var changeimage = popup.findViewById<TextView>(R.id.tv_changeimage)
        var title = popup.findViewById<TextView>(R.id.tv_myinfotitle2)
        var namee = popup.findViewById<EditText>(R.id.et_myinfoname)
        var agee = popup.findViewById<EditText>(R.id.et_myinfoage)
        var save = popup.findViewById<Button>(R.id.btn_saveMyinfodetails)
        var cancel = popup.findViewById<CardView>(R.id.cd_myinfopopupcancel22)



            var preferencestuff = Preferencestuff(this)
            var name = preferencestuff.getUserAttributes("userName")
            var age = preferencestuff.getUserAttributes("userAge")
            var picnum = preferencestuff.getUserAttributes("displayImage")


            title.text = "Hi, $name"
            setimage(image, picnum!!.toInt())
            namee.setText(name)
            agee.setText(age.toString())

            save.setOnClickListener {
                save.clicking()
                save.shortvibrate()
                if (namee.text.isEmpty() || agee.text.isEmpty()) {
                    Toast.makeText(this, "Kindly Enter all details", Toast.LENGTH_SHORT).show()
                } else {
                    preferencestuff.setUserAttributes("userName",namee.text.toString() )
                    preferencestuff.setUserAttributes("userAge",agee.text.toString())
                   // Toast.makeText(this, "${preferencestuff.getUserAttributes("userName")}", Toast.LENGTH_SHORT).show()
                    popup.dismiss()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            changeimage.setOnClickListener {

                if (namee.text.isEmpty() || agee.text.isEmpty()) {
                    Toast.makeText(this, "No detail entered", Toast.LENGTH_SHORT).show()
                } else {
                        preferencestuff.setUserAttributes("userName",namee.text.toString() )
                        preferencestuff.setUserAttributes("userAge",agee.text.toString())
                        popup.dismiss()
                        picChange()
                }

            }
        cancel.setOnClickListener {
            cancel.shortvibrate()
            cancel.clicking()
            popup.dismiss()
        }
    }

    fun picChange(){
        var popup = Dialog(this)
        popup.setCancelable(false)
        popup.setContentView(R.layout.fragment_change_images)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()

        var preferencestuff = Preferencestuff(this)
        var name = preferencestuff.getUserAttributes("userName")
        var age = preferencestuff.getUserAttributes("userAge")
        var picnum = preferencestuff.getUserAttributes("displayImage")


            popup.tv_imagenum.text = picnum.toString()
            setimage(popup.image17, picnum!!.toInt())

        var profilelist = listOf(
                profileimages(popup.image1, R.drawable.a40),
                profileimages(popup.image2, R.drawable.a41),
                profileimages(popup.image3, R.drawable.a42),
                profileimages(popup.image4, R.drawable.a43),
                profileimages(popup.image5, R.drawable.a44),
                profileimages(popup.image6, R.drawable.a45),
                profileimages(popup.image7, R.drawable.a46),
                profileimages(popup.image8, R.drawable.a47),
                profileimages(popup.image9, R.drawable.a48),
                profileimages(popup.image10, R.drawable.a49),
                profileimages(popup.image11, R.drawable.a50),
                profileimages(popup.image12, R.drawable.a51),
                profileimages(popup.image13, R.drawable.a53),
                profileimages(popup.image14, R.drawable.a54),
                profileimages(popup.image15, R.drawable.a55),
                profileimages(popup.image16, R.drawable.a56),
                profileimages(popup.image18, R.drawable.a1),
                profileimages(popup.image19, R.drawable.a2),
                profileimages(popup.image20, R.drawable.a3),
                profileimages(popup.image21, R.drawable.a4),
                profileimages(popup.image22, R.drawable.a5),
                profileimages(popup.image23, R.drawable.a6),
                profileimages(popup.image24, R.drawable.a7),
                profileimages(popup.image25, R.drawable.a8),
                profileimages(popup.image26, R.drawable.a9),
                profileimages(popup.image27, R.drawable.a10),
                profileimages(popup.image28, R.drawable.a11),
                profileimages(popup.image29, R.drawable.a12),
                profileimages(popup.image30, R.drawable.a13),
                profileimages(popup.image31, R.drawable.a14),
                profileimages(popup.image32, R.drawable.a15),
                profileimages(popup.image33, R.drawable.a16),
                profileimages(popup.image34, R.drawable.a17),
                profileimages(popup.image35, R.drawable.a18),
                profileimages(popup.image36, R.drawable.a19),
                profileimages(popup.image37, R.drawable.a20),
                profileimages(popup.image38, R.drawable.a21),
                profileimages(popup.image39, R.drawable.a22),
                profileimages(popup.image40, R.drawable.a23),
                profileimages(popup.image41, R.drawable.a24),
                profileimages(popup.image42, R.drawable.a25),
                profileimages(popup.image43, R.drawable.a26),
                profileimages(popup.image44, R.drawable.a27),
                profileimages(popup.image45, R.drawable.a28),
                profileimages(popup.image46, R.drawable.a29),
                profileimages(popup.image47, R.drawable.a30),
                profileimages(popup.image48, R.drawable.a31),
                profileimages(popup.image49, R.drawable.a32),
                profileimages(popup.image50, R.drawable.a33),
                profileimages(popup.image51, R.drawable.a34),
                profileimages(popup.image52, R.drawable.a35),
                profileimages(popup.image53, R.drawable.a36),
                profileimages(popup.image54, R.drawable.a37),
                profileimages(popup.image55, R.drawable.a38),
                profileimages(popup.image56, R.drawable.a39), )
        for (i in profilelist){
            i.image.setOnClickListener {
                i.image.clicking()
                i.image.shortvibrate()
                popup.tv_imagenum.setText("${profilelist.indexOf(i) + 1}")
                popup.image17.setImageResource(i.num)
            }
        }
        popup.btn_saveimage.setOnClickListener {
            popup.btn_saveimage.clicking()
            popup.btn_saveimage.shortvibrate()

            if (popup.tv_imagenum.text == "0"){
                //Toast.makeText(requireContext(), "Choose An Image", Toast.LENGTH_SHORT).show()
            }else{
                var saveimagenum = popup.tv_imagenum.text.toString().toInt()
                preferencestuff.setUserAttributes("displayImage", saveimagenum.toString())
            }

            startActivity(Intent(this, MainActivity::class.java))

        }
        popup.cd_myinfopopupcancel.setOnClickListener {
            popup.cd_myinfopopupcancel.clicking()
            popup.cd_myinfopopupcancel.shortvibrate()
            popup.dismiss()
        }

    }
    internal fun addiction(){
        var view = Dialog(this)
        view.setCancelable(false)
        view.setContentView(R.layout.fragment_behavioural_concern)
        view.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.show()

        var preferencestuff = Preferencestuff(this)

        var addiction = preferencestuff.getAddiction()
        view.et_addictionword.setText(addiction)

        view.btn_saveaddiction.setOnClickListener {
            view.btn_saveaddiction.clicking()
            view.btn_saveaddiction.shortvibrate()
            if (TextUtils.isEmpty(view.et_addictionword.toString())){
                Toast.makeText(this, "No addiction specified", Toast.LENGTH_SHORT).show()
            }else{
                preferencestuff.setAddiction(view.et_addictionword.text.toString())
                view.dismiss()
            }
        }

        view.cd_myaddictionpopupcancel22.setOnClickListener {
            view.cd_myaddictionpopupcancel22.clicking()
            view.cd_myaddictionpopupcancel22.shortvibrate()
            view.dismiss()
        }
    }
    internal fun cancelload(){
        load.dismiss()
    }
    private fun savedata(counter: Int){
        lifecycleScope.launch {
            DataStoreManager.saveInt(this@MainActivity, "currentquote", counter)
        }
    }
    private fun getversion(updatelayout : LinearLayout, updatebutton : Button, cancelupdate : CardView){
        var rf = Retrofit.Builder()
                .baseUrl(RetrofitInterface1.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        var API = rf.create(RetrofitInterface::class.java)
        var call =API.versioncheck
        call?.enqueue(object : Callback<Map<String, String>?> {
            override fun onResponse(call: Call<Map<String, String>?>, response: Response<Map<String, String>?>) {
                var versions: Map<String, String>? = response.body() as Map<String, String>
                var UpdateVersion = versions!!["Growth Checker App"]

                var versioncode = BuildConfig.VERSION_NAME
                //Toast.makeText(this@MainActivity, "$UpdateVersion and version code $versioncode", Toast.LENGTH_SHORT).show()

                var animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.adscale)
                if (UpdateVersion != versioncode){
                    updatelayout.visibility = View.VISIBLE
                    updatelayout.startAnimation(animation)
                }
                cancelupdate.setOnClickListener {
                    cancelupdate.clicking()
                    updatelayout.clearAnimation()
                    updatelayout.visibility = View.GONE
                }
                updatebutton.setOnClickListener {
                    updatebutton.clicking()
                    updatebutton.clicking()
                    updatelayout.clearAnimation()
                    updatelayout.visibility = View.GONE
                    val uriUri = Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                    val launchBrowser = Intent(Intent.ACTION_VIEW, uriUri)
                    startActivity(launchBrowser)
                }
            }
            override fun onFailure(call: Call<Map<String, String>?>, t: Throwable) {
            }
        })
    }
}