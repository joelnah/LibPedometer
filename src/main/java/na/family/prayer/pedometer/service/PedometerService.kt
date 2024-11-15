package na.family.prayer.pedometer.service

//import dagger.hilt.android.AndroidEntryPoint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import na.family.prayer.pedometer.PedometerFlow.dataStateFlow
import na.family.prayer.pedometer.notification.NOTIFY_ID
import na.family.prayer.pedometer.extentions.isNewDay
import na.family.prayer.pedometer.notification.PedometerNotification
import na.family.prayer.pedometer.scope.ScopeWork.getReadyScope
//import na.family.prayer.pedometer.PedometerData.lastTime
import na.family.prayer.pedometer.scope.ScopeWork.initiatePedometerService
import nah.prayer.library.Nlog
import java.util.Calendar

internal class PedometerService : Service() {

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null

    private val step = MutableStateFlow(0)

    private var initialSteps: Int = 0
    private var totalSteps: Int = 0

    private val pedometerNotification = PedometerNotification(this)

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.values?.get(0)?.toInt()?.let { currentSteps ->
                totalSteps = currentSteps
                setStep()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    private val dateChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (Intent.ACTION_DATE_CHANGED == intent?.action) {
                setStep()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Nlog.d("onCreate")
        getReadyScope {
            Nlog.d("onCreate getReadyScope")
            Nlog.d("totalSteps: ${totalSteps}")
            Nlog.d("dataStateFlow.value: ${dataStateFlow.value}")
            if (
                totalSteps == 0
                && dataStateFlow.value.pedometerSteps > totalSteps
                && dataStateFlow.value.lastTime.isNewDay()
                ) {
                initialSteps = dataStateFlow.value.pedometerSteps * -1
            }
        }

        sensorManager = ContextCompat.getSystemService(this, SensorManager::class.java)!!
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounterSensor?.also { sensor ->
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI)
        }
        registerReceiver(dateChangeReceiver, IntentFilter(Intent.ACTION_DATE_CHANGED))
//        scheduleUpdateNotification(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = pedometerNotification.createNotification()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(NOTIFY_ID, notification)
        } else {
            startForeground(
                NOTIFY_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Nlog.d("onDestroy")
        initiatePedometerService(applicationContext)
    }

    private fun setStep() {
        Nlog.d("totalSteps: $totalSteps")
        Nlog.d("initialSteps: $initialSteps")
        if (dataStateFlow.value.lastTime.isNewDay()) {
            Nlog.d("isNewDay")
            initialSteps = totalSteps
            step.value = 0
        } else {
            Nlog.d("not isNewDay")
            step.value = totalSteps - initialSteps
        }

        pedometerNotification.updateNotification(step.value)
    }

}