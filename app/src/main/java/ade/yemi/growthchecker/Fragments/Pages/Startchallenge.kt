package ade.yemi.growthchecker.Fragments.Pages
import ade.yemi.growthchecker.Activities.Activity2
import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.AlarmReceiver
import ade.yemi.growthchecker.Data.AlarmInfo
import ade.yemi.growthchecker.Data.DataStoreManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.delay
import ade.yemi.growthchecker.Utilities.shortvibrate
import ade.yemi.roomdatabseapp.Data.Challenge
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class Startchallenge : Fragment() {
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    private val Cadder1:EditText by lazy {
        requireView().findViewById(R.id.et_challengeadder1)
    }
    private val Cadder2:EditText by lazy {
        requireView().findViewById(R.id.et_challengeadder2)
    }
    private val Cadder3:EditText by lazy {
        requireView().findViewById(R.id.et_challengeadder3)
    }
    private var assessmentnotification = false
    private var ungoing = false
    private lateinit var challengeViewModel: ChallengeViewModel

    private var adder1 = ""
    private var adder2 = ""
    private var adder3 = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view = inflater.inflate(R.layout.fragment_startchallenge, container, false)
        var start = view.findViewById<CardView>(R.id.cd_challengestartstart)
        var image = view.findViewById<ImageView>(R.id.iv_startpageimage)
        createnotificationchannel()

        val challenge = arguments?.getString("challengeviewchallenge")
            imageset(challenge!!, image)
            start.setOnClickListener {
                start.clicking()
                start.shortvibrate()
                val alarmInfo = AlarmInfo(requireContext())
                if (checkempty(Cadder1, Cadder2, Cadder3) == true){
                    Toast.makeText(requireContext(), "Please Wait", Toast.LENGTH_SHORT).show()
                    picker = MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                            .setHour(12)
                            .setMinute(0)
                            .setTitleText("Select Time For Daily Assessment")
                            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                            .setTheme(R.style.TimePicker)
                            .build()

                    picker.show(childFragmentManager, "AssessmentNotification")

                    picker.addOnPositiveButtonClickListener {
                        calendar = Calendar.getInstance()
                        calendar[Calendar.HOUR_OF_DAY] = picker.hour
                        calendar[Calendar.MINUTE] = picker.minute
                        calendar[Calendar.SECOND] = 0
                        calendar[Calendar.MILLISECOND] = 0

                        alarmInfo.SaveAlarmInfo(picker.hour, picker.minute)
                        confirmpopup(challenge)
                    }
//                        confirmpopup(challenge)
                }else{
                    Toast.makeText(requireContext(), "Kindly fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        return view
    }

    private fun challengedetails(string: String): Challenge{
        var challengetype = ""
        var days = ""
        var points = mutableListOf<String>()

        when(string){
            "challenge14" -> {challengetype = "14 Days Challenge"
                              days = "14"}
            "challenge30" -> {challengetype = "30 Days Challenge"
                days = "30"}
            "challenge60" -> {challengetype = "60 Days Challenge"
                days = "60"}
            "challenge100" -> {challengetype = "100 Days Challenge"
                                        days = "100"}
            "challenge200" -> {challengetype = "200 Days Challenge"
                days = "200"}
        }
        var challenge = Challenge(0, challengetype, days, points)
        return challenge
    }
    private fun imageset(string: String, imageView: ImageView){
        when(string){
            "challenge14" -> imageView.setImageResource(R.drawable.forteendays)
            "challenge30" -> imageView.setImageResource(R.drawable.thirtydays)
            "challenge60" -> imageView.setImageResource(R.drawable.sixtydays)
            "challenge100"-> imageView.setImageResource(R.drawable.hundreddays)
            "challenge200"-> imageView.setImageResource(R.drawable.twohundreddays)
        }
    }
    private fun confirmpopup( string: String){
        var popup = Dialog(requireContext())
        popup.setCancelable(false)
        popup.setContentView(R.layout.popup_startchallenge_confirmation)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()

        var cancel = popup.findViewById<CardView>(R.id.cd_StartChallengeConfirmationCancel)
        var start = popup.findViewById<Button>(R.id.btn_startchallengeconfirmed)
        cancel.setOnClickListener {
            cancel.clicking()
            cancel.shortvibrate()
            delay(2000)
            popup.dismiss()
        }

        val alarmInfo = AlarmInfo(requireContext())
        start.setOnClickListener {
            start.clicking()
            start.shortvibrate()
            var challenge = challengedetails(string)
            challengeViewModel = ViewModelProvider(this).get(ChallengeViewModel::class.java)
            try {
                setalarm()
                challengeViewModel.insertChallengeInfo(challenge)
                Toast.makeText(requireContext(), "$string Challenge Started Successfully\nPlease wait for first assessment", Toast.LENGTH_LONG).show()
                assessmentnotification = false
                ungoing = true
                alarmInfo.setongoing(true)
                adder1 = Cadder1.text.toString()
                adder2 = Cadder2.text.toString()
                adder3 = Cadder3.text.toString()
                savedata()
                popup.dismiss()
                startActivity(Intent(requireContext(), MainActivity :: class.java))
            }catch(e:Exception){
                ungoing = false
                savedata()
                popup.dismiss()
                Toast.makeText(requireContext(), "Could Not Start Challenge", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity :: class.java))
            }
        }
    }

        private fun savedata(){
        lifecycleScope.launch {
            context?.let { DataStoreManager.saveBoolean(it, "challengeungoing", ungoing) }
            context?.let { DataStoreManager.saveBoolean(it, "assessmentnotification", assessmentnotification) }
            context?.let { DataStoreManager.saveString(it, "Ungoingchallengeadder1", adder1) }
            context?.let { DataStoreManager.saveString(it, "Ungoingchallengeadder2", adder2) }
            context?.let { DataStoreManager.saveString(it, "Ungoingchallengeadder3", adder3) }
        }
    }
    private fun checkempty(text1: EditText, text2:EditText, text3: EditText): Boolean{
        var check = false
        if (text1.text.isEmpty() || text2.text.isEmpty() || text3.text.isEmpty()){
            check = false
        }else{
            check = true
        }
        return check
    }

    private fun setalarm() {
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.action = "normal.alarm.setting"

        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent
        )
        Toast.makeText(requireContext(), "Alarm set successfully", Toast.LENGTH_SHORT).show()

    }


    private fun createnotificationchannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name: CharSequence = "Assessment Notification"
            val description = "Assessment Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("AssessmentNotification", name, importance)
            channel.description = description
            val notificationManager = context?.getSystemService(
                    NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
//challengeungoing