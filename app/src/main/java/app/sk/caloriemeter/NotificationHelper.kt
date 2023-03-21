package app.sk.caloriemeter

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import app.sk.caloriemeter.ui.DashboardActivity


internal class NotificationHelper(context: Context) {
    private val mContext: Context

    init {
        mContext = context
    }

    fun createNotification() {
        Log.e("createNotification","createNotification")
        val intent = Intent(mContext, DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val resultPendingIntent = PendingIntent.getActivity(mContext,
                0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val mBuilder = NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
        mBuilder.setSmallIcon(R.mipmap.sym_def_app_icon)
        mBuilder.setContentTitle("Calorie Meter")
                .setContentText("Reminder to add daily food intake")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)
        val mNotificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
       // mNotificationManager.notify(0 /* Request Code */, mBuilder.build())
        //startForeground(channelIndex, builder.build());
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "10001"
    }
}