package ade.yemi.growthchecker

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.PopUp_Fragments.DailyAssessment
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.window.SplashScreen
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Time for new assessment", Toast.LENGTH_SHORT).show()

        //(context as MainActivity()).savedata(true)


        var i = Intent(context, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.putExtra("toacess", true)
        context?.startActivity(i)

        var pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
        val builder = NotificationCompat.Builder(context!!, "AssessmentNotification")
            .setSmallIcon(R.drawable.forteendays)
            .setContentTitle("DAILY ASSESSMENT")
            .setContentText("It is time to take your daily assessment.")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())
    }
}