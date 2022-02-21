package ade.yemi.growthchecker

import ade.yemi.growthchecker.Fragments.Pages.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replacefragment(Homepage())
        var fragment = findViewById<View>(R.id.fg_home)

        var homecard = findViewById<CardView>(R.id.cd_home)
        var analyticscard = findViewById<CardView>(R.id.cd_analytics)
        var achievementscard = findViewById<CardView>(R.id.cd_achievements)
        var notescard = findViewById<CardView>(R.id.cd_notes)
        var tipscard = findViewById<CardView>(R.id.cd_tips)

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


        homecard.setOnClickListener {
            replacefragment(Homepage())
            UpdateOnclickElement(listOf(analyticsview, achievementsview, notesview, tipsview))
            homeimage.setImageResource(R.drawable.home1)
            analyticsimage.setImageResource(R.drawable.analytics2)
            achievementsimage.setImageResource(R.drawable.achievement2)
            notesimage.setImageResource(R.drawable.note2)
            tipsimage.setImageResource(R.drawable.tips2)
        }
        analyticscard.setOnClickListener {
            replacefragment(AnalyticsPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, tipsview))
            homeimage.setImageResource(R.drawable.home2)
            analyticsimage.setImageResource(R.drawable.analytics1)
            achievementsimage.setImageResource(R.drawable.achievement2)
            notesimage.setImageResource(R.drawable.note2)
            tipsimage.setImageResource(R.drawable.tips2)
        }
        achievementscard.setOnClickListener {
            replacefragment(AchievementsPage())
            UpdateOnclickElement(listOf(homeview, analyticsview, notesview, tipsview))
            homeimage.setImageResource(R.drawable.home2)
            analyticsimage.setImageResource(R.drawable.analytics2)
            achievementsimage.setImageResource(R.drawable.achievement1)
            notesimage.setImageResource(R.drawable.note2)
            tipsimage.setImageResource(R.drawable.tips2)
        }
        notescard.setOnClickListener {
            replacefragment(NotesPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, analyticsview, tipsview))
            homeimage.setImageResource(R.drawable.home2)
            analyticsimage.setImageResource(R.drawable.analytics2)
            achievementsimage.setImageResource(R.drawable.achievement2)
            notesimage.setImageResource(R.drawable.note1)
            tipsimage.setImageResource(R.drawable.tips2)
        }
        tipscard.setOnClickListener {
            replacefragment(TipsPage())
            UpdateOnclickElement(listOf(homeview, achievementsview, notesview, analyticsview))
            homeimage.setImageResource(R.drawable.home2)
            analyticsimage.setImageResource(R.drawable.analytics2)
            achievementsimage.setImageResource(R.drawable.achievement2)
            notesimage.setImageResource(R.drawable.note2)
            tipsimage.setImageResource(R.drawable.tips1)
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
}