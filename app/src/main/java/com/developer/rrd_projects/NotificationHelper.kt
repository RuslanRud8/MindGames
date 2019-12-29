package com.developer.rrd_projects

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat

class NotificationHelper(context: Context?) {

    private var mContext: Context? = context
    private val NOTIFICATION_CHANNEL_ID = "10001"

    fun createNotification(){
        val intent = Intent(mContext, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val resultPendingIntent = PendingIntent.getActivity(
            mContext,
            100 /* Request code */, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val mBuilder = NotificationCompat.Builder(mContext)
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
        mBuilder.setContentTitle("Training")
            .setContentText("It's time to play !!!")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setContentIntent(resultPendingIntent)

        val mNotificationManager = mContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        Log.i("Notification", "created")

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Mind games notification",
                importance
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)

            assert(mNotificationManager != null)
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        assert(mNotificationManager != null)
        mNotificationManager.notify(100 /* Request Code */, mBuilder.build())
    }
}