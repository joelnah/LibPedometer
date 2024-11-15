package na.family.prayer.pedometer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import na.family.prayer.pedometer.scope.ScopeWork.initiatePedometerService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            initiatePedometerService(context)
        }
    }
}