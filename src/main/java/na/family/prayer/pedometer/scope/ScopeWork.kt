package na.family.prayer.pedometer.scope

import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import na.family.prayer.pedometer.Constants
import na.family.prayer.pedometer.PedometerFlow.dataFlow
import na.family.prayer.pedometer.PedometerFlow.dataStateFlow
import na.family.prayer.pedometer.data.PedometerData
import na.family.prayer.pedometer.service.PedometerService
import na.family.prayer.pedometer.utils.GsonUtil
import nah.prayer.library.Npref
import nah.prayer.library.Nstore

object ScopeWork {
    private val scope = CoroutineScope(Dispatchers.IO)

    @OptIn(FlowPreview::class)
    internal fun getReadyScope(onReady: () -> Unit) {

        scope.launch {
            getPedometerPref(Constants.PEDOMETER_DATA).collect{
                if(it.isNotEmpty()) {
                    dataFlow.value = GsonUtil.fromJson(it, PedometerData::class.java)
                }
            }
        }

        scope.launch {
            dataStateFlow.debounce(500).collect{
                if(it.lastTime != 0L) onReady()
            }
        }
    }

    internal fun isFirstLaunch(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("pedometer_prefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean(Constants.IS_FIRST_LAUNCH, true)
        if (isFirstLaunch) {
            sharedPreferences.edit().putBoolean(Constants.IS_FIRST_LAUNCH, false).apply()
        }
        return isFirstLaunch
    }


    internal fun initiatePedometerService(context: Context) {
        getReadyScope {
            val serviceIntent = Intent(context, PedometerService::class.java)
            serviceIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startForegroundService(serviceIntent)
        }
    }

    internal fun putPedometerPref(name: String, value: PedometerData) {
        Nstore.putDS(scope, name, GsonUtil.toJson(value))
    }

    fun getPedometerPref(name: String, ):StateFlow<String> {
        return Nstore.getDS(scope, name, "")
    }
}