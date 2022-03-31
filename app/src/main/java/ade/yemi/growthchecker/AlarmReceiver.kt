package ade.yemi.growthchecker

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.AlarmInfo
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var calendar: Calendar
    private lateinit var c: Calendar
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent!!.action.equals("normal.alarm.setting")){
            Toast.makeText(context, "Time for new assessment", Toast.LENGTH_SHORT).show()

            var alarmInfo = AlarmInfo(context!!)
            alarmInfo.setassess(true)

            var j = Intent(context, MainActivity::class.java)
            var pendingIntent = PendingIntent.getActivity(context, 0, j, 0)
            val builder = NotificationCompat.Builder(context!!, "AssessmentNotification")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("DAILY ASSESSMENT")
                    .setContentText("It is time to take your daily assessment.")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(123, builder.build())
        }else if(intent!!.action.equals("android.intent.action.BOOT_COMPLETED")){
            val alarmInfo = AlarmInfo(context!!)
            val hour = alarmInfo.gethour()
            val minute = alarmInfo.getminute()
            val ongoing = alarmInfo.getongoing()
            val day = alarmInfo.getday()
            c = Calendar.getInstance()
            var CurrentDay = c.get(Calendar.DAY_OF_MONTH)

            if (ongoing == true && day != CurrentDay) {
                calendar = Calendar.getInstance()
                calendar[Calendar.HOUR_OF_DAY] = hour
                calendar[Calendar.MINUTE] = minute
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0

                alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                intent.action = "normal.alarm.setting"

                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                        AlarmManager.INTERVAL_DAY, pendingIntent)
            }
            else{
//                Toast.makeText(context, "Recent ass: $day Today:$CurrentDay true", Toast.LENGTH_SHORT).show()
            }
        }
    }
}