package ade.yemi.growthchecker_free.PopUp_Fragments

import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.AlarmReceiver
import ade.yemi.growthchecker_free.Data.AlarmInfo
import ade.yemi.growthchecker_free.Data.DataStoreManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.delay
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import ade.yemi.roomdatabseapp.Data.Challenge
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class DailyAssessment : DialogFragment() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var challengeViewModel: ChallengeViewModel
    private lateinit var some: Challenge

    private var word1 = ""
    private var word2 = ""
    private var word3 = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view = inflater.inflate(R.layout.fragment_daily_assessment, container, false)

        var addertext1 = view.findViewById<TextView>(R.id.tv_addertext1)
        var addertext2 = view.findViewById<TextView>(R.id.tv_addertext2)
        var addertext3 = view.findViewById<TextView>(R.id.tv_addertext3)

        var text1 = view.findViewById<TextView>(R.id.tv_adder1)
        var text2 = view.findViewById<TextView>(R.id.tv_adder2)
        var text3 = view.findViewById<TextView>(R.id.tv_adder3)
        var text4 = view.findViewById<TextView>(R.id.tv_adder4)
        var text5 = view.findViewById<TextView>(R.id.tv_adder5)
        var text6 = view.findViewById<TextView>(R.id.tv_adder6)
        var text7 = view.findViewById<TextView>(R.id.tv_adder7)
        var text8 = view.findViewById<TextView>(R.id.tv_adder8)
        var score1 = view.findViewById<TextView>(R.id.tv_score1)
        var score2 = view.findViewById<TextView>(R.id.tv_score2)
        var score3 = view.findViewById<TextView>(R.id.tv_score3)
        var score4 = view.findViewById<TextView>(R.id.tv_score4)
        var submit = view.findViewById<CardView>(R.id.cd_submitAssessment)
        var cancel = view.findViewById<CardView>(R.id.cd_assessmentcancel)
        var imae = view.findViewById<ImageView>(R.id.iv_assmentChallengeimage)
        var challengeteext = view.findViewById<TextView>(R.id.tv_assessmentChallenge)
        var finishchal = view.findViewById<CardView>(R.id.cd_fisnishAssessment)


        var partiday = view.findViewById<TextView>(R.id.tv_assessmentday)
        var progresspercent = view.findViewById<TextView>(R.id.tv_assessmentpercent)
        var viewcol = view.findViewById<View>(R.id.v_assessmentpercentview)

        var c1 = false
        var c2 = false
        var c3 = false
        var c4 = false

        cancel.setOnClickListener {
            cancel.clicking()
            cancel.shortvibrate()
            delay(150)
            dismiss()
        }
        text1.setOnClickListener {
            updateclick(text1, text2)
            c1 = true
            var a = -50
            score1.text = a.toString()
        }
        text2.setOnClickListener {
            updateclick(text2, text1)
            c1 = true
            var a = 0
            score1.text = a.toString()
        }
        text3.setOnClickListener {
            updateclick(text3, text4)
            c2 = true
            var a = 10
            score2.text = a.toString()
        }
        text4.setOnClickListener {
            updateclick(text4, text3)
            c2 = true
            var a = 0
            score2.text = a.toString()
        }
        text5.setOnClickListener {
            updateclick(text5, text6)
            c3 = true
            var a = 7
            score3.text = a.toString()
        }
        text6.setOnClickListener {
            updateclick(text6, text5)
            c3 = true
            var a = 0
            score3.text = a.toString()
        }
        text7.setOnClickListener {
            updateclick(text7, text8)
            c4 = true
            var a = 5
            score4.text = a.toString()
        }
        text8.setOnClickListener {
            updateclick(text8, text7)
            c4 = true
            var a = 0
            score4.text = a.toString()
        }



        lifecycleScope.launch {
                val pushresult1 = async {
                    context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder1") }
                }
                val pushresult2 = async {
                    context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder2") }
                }
                val pushresult3 = async {
                    context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder3") }
                }
            word1 = pushresult1.await()!!
            word2 = pushresult2.await()!!
            word3 = pushresult3.await()!!
            addertext1.text = word1
            addertext2.text = word2
            addertext3.text = word3

            }

        challengeViewModel = ViewModelProvider(this).get(ChallengeViewModel::class.java)
        challengeViewModel.allChallenges.observe(this, Observer {

            var id = it.first().id
            var challnegtype = it.first().ChallengeType
            var days = it.first().Days
            var point: MutableList<String> = it.first().Point
            some = Challenge(id, challnegtype, days, point)

            challengeimage(days, imae)
            challengeteext.text = challnegtype
            partiday.text = "Day ${it.first().Point.size + 1}"
            var toset1 = it.first().Point.size + 1
            var toset2 = 100/(it.first().Days.toDouble())
            var toset = toset1 * toset2
            progresspercent.text = "${toset.toInt()}%"
            val mParams = viewcol.layoutParams as FrameLayout.LayoutParams
            mParams.apply {
                width = (toset * 1.34).toInt()
                height *= 1
            }
            viewcol.layoutParams = mParams
            if (  (point.size + 1 ) >= days.toInt()  ){
                submit.visibility = View.GONE
                finishchal.visibility = View.VISIBLE
            }else{
                submit.visibility = View.VISIBLE
                finishchal.visibility = View.GONE
            }
        })
        var calendar = Calendar.getInstance()
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        submit.setOnClickListener {
                submit.shortvibrate()
                submit.clicking()
                val alarmInfo = AlarmInfo(requireContext())
               if (c1 == true && c2 == true && c3==true && c4 ==true){
                   var one = some.id
                   var two = some.ChallengeType
                   var three = some.Days
                   var four = some.Point
                   var total  = score1.text.toString().toInt() + score2.text.toString().toInt() + score3.text.toString().toInt() + score4.text.toString().toInt()


                   four.add(total.toString())
                   challengeViewModel.updateChallenge(Challenge(one, two, three, four))
                       lifecycleScope.launch {
                           //change back to false
                           context?.let { DataStoreManager.saveBoolean(it, "challengeungoing", true) }
                           alarmInfo.setday(day)
                           alarmInfo.setassess(false)
                           dismiss()
                           startActivity(Intent(requireContext(), MainActivity::class.java))
                       }
                   Toast.makeText(requireContext(), "Today's point is ${four.last()}  ", Toast.LENGTH_LONG).show()
               }else{
                   Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
               }
           }
        val alarmInfo = AlarmInfo(requireContext())
        finishchal.setOnClickListener {
            finishchal.shortvibrate()
            finishchal.clicking()
            if (c1 == true && c2 == true && c3==true && c4 ==true){
                var one = some.id
                var two = some.ChallengeType
                var three = some.Days
                var four = some.Point
                var total  = score1.text.toString().toInt() + score2.text.toString().toInt() + score3.text.toString().toInt() + score4.text.toString().toInt()

                four.add(total.toString())
             try {
                 challengeViewModel.updateChallenge(Challenge(one, two, three, four))
             lifecycleScope.launch {
                   context?.let { DataStoreManager.saveBoolean(it, "challengeungoing", false) }
                   cancelalarm()
                   alarmInfo.setongoing(false)
                 alarmInfo.setassess(false)
                   dismiss()
                   startActivity(Intent(requireContext(), MainActivity::class.java))
                    }
                    Toast.makeText(requireContext(), "Today's point is ${four.last()}  \n Challenge Finished Successfully \n Check analytics page for analytics", Toast.LENGTH_LONG).show()
            }catch (Ex: Exception){
             Toast.makeText(requireContext(), "One Extra day added", Toast.LENGTH_SHORT).show() }
            }else{
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
    private fun updateclick(text1: TextView, text2: TextView){
        text1.clicking()
        text1.shortvibrate()
        text1.setBackgroundResource(R.color.primarycolor2)
        text1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        text2.setBackgroundResource(R.drawable.assessmentinfo)
        text2.setTextColor(ContextCompat.getColor(requireContext(), R.color.primarycolor2))
    }
    private fun challengeimage(string: String, imageView: ImageView){
        when(string){
            "14" -> imageView.setImageResource(R.drawable.forteendays)
            "30" -> imageView.setImageResource(R.drawable.thirtydays)
            "60" -> imageView.setImageResource(R.drawable.sixtydays)
            "100" -> imageView.setImageResource(R.drawable.hundreddays)
            "200" -> imageView.setImageResource(R.drawable.twohundreddays)
        }
    }
    private fun cancelalarm(){
        alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmManager.cancel(pendingIntent)
    }

}