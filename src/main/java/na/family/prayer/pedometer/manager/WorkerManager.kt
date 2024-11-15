//package na.family.prayer.pedometer.manager
//
//import android.content.Context
//import androidx.work.Constraints
//import androidx.work.CoroutineWorker
//import androidx.work.NetworkType
//import androidx.work.PeriodicWorkRequestBuilder
//import androidx.work.WorkManager
//import androidx.work.WorkerParameters
//import na.family.prayer.pedometer.PedometerFlow.dataStateFlow
//import na.family.prayer.pedometer.PedometerFlow.workerFlow
//import nah.prayer.library.Nlog
//import java.util.concurrent.TimeUnit
//
//internal class WorkerManager(appContext: Context, workerParams: WorkerParameters):
//    CoroutineWorker(appContext, workerParams) {
//
//    override suspend fun doWork(): Result {
//        workerFlow.value = dataStateFlow.value.copy()
//
//        Nlog.d("WorkerManager ${workerFlow.value}")
//        return Result.success()
//    }
//}
//
//
//
//internal fun scheduleUpdateNotification(context: Context, min: Long) {
//    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED)
//        .build()
//
//    val updateNotificationRequest = PeriodicWorkRequestBuilder<WorkerManager>(min, TimeUnit.MINUTES)
//        .setConstraints(constraints)
//        .build()
//
//    WorkManager.getInstance(context).enqueue(updateNotificationRequest)
//}