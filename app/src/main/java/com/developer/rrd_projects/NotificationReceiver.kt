package com.developer.rrd_projects
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context?, intent: Intent?) {

        createNotificationChannel(context)

        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val i = Intent(context, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        
        val pendingIntent = PendingIntent.getActivity(context,100,i,PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, context.getString(R.string.CHANNEL_ID))
            .setSmallIcon(R.drawable.settings)
            .setContentTitle("Training")
            .setContentText("It's time to play !!!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(100,builder.build())
    }


    private fun createNotificationChannel(context: Context?){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(context!!.getString(R.string.CHANNEL_ID),context.getString(R.string.CHANNEL_NEWS), NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = context.getString(R.string.CHANNEL_DESCRIPTION)
            notificationChannel.setShowBadge(true)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }
}
 