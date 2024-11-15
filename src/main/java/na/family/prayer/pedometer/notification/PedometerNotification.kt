package na.family.prayer.pedometer.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import kotlinx.coroutines.flow.asStateFlow
import na.family.prayer.pedometer.Constants
import na.family.prayer.pedometer.PedometerFlow.dataFlow
import na.family.prayer.pedometer.PedometerFlow.dataStateFlow
import na.family.prayer.pedometer.R
import na.family.prayer.pedometer.scope.ScopeWork
import nah.prayer.library.Nlog

const val NOTIFY_ID = 6420

class PedometerNotification(private val context: Context) {

    fun createNotification(): Notification {
        val channelId = "pedometer_channel"
        val serviceChannel = NotificationChannel(
            channelId,
            "Pedometer Service Channel",
            NotificationManager.IMPORTANCE_MIN
        ).apply {
            setShowBadge(false)
        }
        val data = dataStateFlow.value

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(serviceChannel)
        val notification = Notification.Builder(context, channelId)
            .setContentTitle(data.pedometerTitle)
            .setContentText(data.startText + "%,d".format(data.pedometerSteps)+data.endText)
            .setSmallIcon(if(data.pedometerIcon == 0) R.mipmap.notification else data.pedometerIcon)
            .build()

        return notification
    }

    fun updateNotification(stepCount: Int) {
        dataFlow.value = dataStateFlow.value.copy(
            pedometerSteps = stepCount,
            lastTime = System.currentTimeMillis()
        )
        Nlog.e("dataFlow.value: ${dataFlow.value}")
        ScopeWork.putPedometerPref(Constants.PEDOMETER_DATA, dataFlow.value)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = createNotification()
        notificationManager.notify(NOTIFY_ID, notification)
    }


}