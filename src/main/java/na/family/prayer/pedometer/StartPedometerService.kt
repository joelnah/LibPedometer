package na.family.prayer.pedometer

import android.app.Activity
import na.family.prayer.pedometer.PedometerFlow.dataFlow
import na.family.prayer.pedometer.data.PedometerData
import na.family.prayer.pedometer.manager.PermissionManager
//import na.family.prayer.pedometer.manager.scheduleUpdateNotification
import na.family.prayer.pedometer.scope.ScopeWork
import na.family.prayer.pedometer.scope.ScopeWork.isFirstLaunch
import nah.prayer.library.NahUtils
import nah.prayer.library.Nlog


fun Activity.startPedometerService(
    title: String,
    startText: String = "",
    endText: String = "",
    icon: Int
) {
    if(isFirstLaunch(this)){
        val oneDayInMillis = 24 * 60 * 60 * 1000L
        val pedometerData = PedometerData().copy(
            pedometerTitle = title,
            startText = startText,
            endText = endText,
            pedometerIcon = icon,
            lastTime = System.currentTimeMillis() - oneDayInMillis
        )
        dataFlow.value = pedometerData
        ScopeWork.putPedometerPref(Constants.PEDOMETER_DATA, pedometerData)
    }
    ScopeWork.initiatePedometerService(applicationContext)
}

//fun Activity.startWorker(
//    intervalMinutes: Long,
//) {
//    val interval = if (intervalMinutes < 15) 15 else intervalMinutes
//    scheduleUpdateNotification(applicationContext, interval)
//}