package com.runcode.tasbee7.ui.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.runcode.tasbee7.R

const val Channel_Id = "5";


fun notify(
    context: Context, title: String? = null,
    description: String? = null, icon: Int? = null, notificationId: Int? = null,
    pending: PendingIntent
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        createChannel(context, Channel_Id, context.getString(R.string.app_name))

    val notification = createNotification(
        context, title ?: "title",
        description ?: "description",
        icon ?: R.drawable.person_img, pending
    )

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        notify(
            notificationId ?: 1, notification
        )
    }

}

private fun createNotification(
    context: Context,
    title: String,
    description: String,
    icon: Int,
    pending: PendingIntent
): Notification {
    return NotificationCompat.Builder(context, Channel_Id)
        .setContentTitle(title)
        .setContentText(description)
        .setSmallIcon(icon)
        .setStyle(NotificationCompat.BigTextStyle().bigText(description))
        .setContentIntent(pending)
        .build()

}


@RequiresApi(Build.VERSION_CODES.O)
fun createChannel(
    context: Context,
    channelId: String,
    name: String,
    importance: Int = NotificationManager.IMPORTANCE_HIGH
) {
    val channel = NotificationChannel(channelId, name, importance)
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}
