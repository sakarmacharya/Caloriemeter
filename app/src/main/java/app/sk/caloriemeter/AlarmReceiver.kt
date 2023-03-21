package app.sk.caloriemeter

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import app.sk.caloriemeter.ui.DashboardActivity


class AlarmReceiver : BroadcastReceiver() {


    /**
     * sends notification when receives alarm
     * and then reschedule the reminder again
     * */
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendReminderNotification(
                applicationContext = context,
                channelId = "1"
        )
        // Remove this line if you don't want to reschedule the reminder
        RemindersManager.startReminder(context.applicationContext)
    }
}

fun NotificationManager.sendReminderNotification(
        applicationContext: Context,
        channelId: String,
) {
    val contentIntent = Intent(applicationContext, DashboardActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Reminder")
            .setContentText("Tap to add food")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

    notify(1, builder.build())
}


