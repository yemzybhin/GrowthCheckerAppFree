package ade.yemi.growthchecker.splash

import ade.yemi.growthchecker.Adapters.WelcomeAdapter
import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.AlarmReceiver
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class WelcomePage : AppCompatActivity() {

    private lateinit var myAdapter: WelcomeAdapter
    private lateinit var  dotsTv: Array<TextView?>
    private lateinit var layouts: IntArray


    private lateinit var dotsLayout: LinearLayout

    private var NotFirsttime = 0
    private var NotFirsttime2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)
        var btn_next: Button = findViewById(R.id.btn_viewpagernext)
        var btn_skip: Button = findViewById(R.id.btn_viewpagerskip)
        var viewpager= findViewById<ViewPager>(R.id.viewPager)
        createnotificationchannel()


        initdata()
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("firstopen", true)
        statusBarTransparent()
        btn_next.setOnClickListener {
            btn_next.shortvibrate()
            val currentpage: Int = viewpager.currentItem + 1
            if (currentpage < layouts.size){
                viewpager.currentItem = currentpage
            }
            else{
                NotFirsttime2 = 1
                savedata()
                setEncouragement()
                startActivity(intent)
                finish()
            }
        }
        btn_skip.setOnClickListener {
            btn_skip.shortvibrate()
            NotFirsttime2 = 1
            setEncouragement()
            savedata()
            startActivity(intent)
            finish()
        }
        layouts = intArrayOf(R.layout.slide_1, R.layout.slide_2, R.layout.slide_3, R.layout.slide_4)

        myAdapter = WelcomeAdapter(layouts, applicationContext)
        viewpager.adapter = myAdapter
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                if (position == layouts.size - 1){
                    btn_next.text = "Start"
                    btn_skip.visibility = View.GONE
                }else{
                    btn_next.text = "Next"
                    btn_skip.visibility = View.VISIBLE
                }
                setDots(position)
            }
            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        setDots(0)

    }

    private fun statusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21){
            window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
    private fun setDots(page : Int){
        dotsLayout= findViewById<LinearLayout>(R.id.dotsLayout)
        dotsLayout.removeAllViews()
        dotsTv = arrayOfNulls(layouts.size)

        for (i in dotsTv.indices){
            dotsTv[i] = TextView(this)
            dotsTv[i]!!.text = Html.fromHtml("&#8226;")
            dotsTv[i]!!.textSize = 30f
            dotsTv[i]!!.setTextColor(Color.parseColor("#D6D6D6"))
            dotsLayout.addView(dotsTv[i])
        }

        if (dotsTv.isNotEmpty()){
            dotsTv[page]!!.setTextColor(resources.getColor(R.color.primarycolor2))
        }
    }
    private fun initdata(){
        lifecycleScope.launch {
            val pushresult = async {
                DataStoreManager.getInt(this@WelcomePage , "WelcomePageCheck")
            }

            NotFirsttime = pushresult.await()
            NotFirsttime2 = NotFirsttime
        }
    }
    private fun savedata(){
        lifecycleScope.launch {
            DataStoreManager.saveInt(this@WelcomePage, "WelcomePageCheck", NotFirsttime2)
        }
    }
    private fun createnotificationchannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name: CharSequence = "Encourage Notification"
            val description = "Encourage Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Encouragement", name, importance)
            channel.description = description
            val notificationManager = this.getSystemService(
                    NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)

            val name1: CharSequence = "Quote Notification"
            val description1 = "Quote Channel"
            val importance1 = NotificationManager.IMPORTANCE_HIGH
            val channel1 = NotificationChannel("Quote", name1, importance1)
            channel.description = description1
            val notificationManager1 = this.getSystemService(
                    NotificationManager::class.java
            )
            notificationManager1?.createNotificationChannel(channel1)
        }
    }
    private fun setEncouragement() {
        var alarmManager1 = this.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.action = "normal.alarm.encourage"
        var calendar1 = Calendar.getInstance()
        calendar1[Calendar.HOUR_OF_DAY] = 8
        calendar1[Calendar.MINUTE] = 0
        calendar1[Calendar.SECOND] = 0
        calendar1[Calendar.MILLISECOND] = 0

        var pendingIntent1 = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager1.setRepeating(
                AlarmManager.RTC_WAKEUP, calendar1.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent1
        )

    }
}