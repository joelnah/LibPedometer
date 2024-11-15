package na.family.prayer.pedometer.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn



data class PedometerData(
    val pedometerSteps: Int = 0,
    val pedometerTitle: String = "",
    val startText: String = "",
    val endText: String = "",
    val pedometerIcon: Int = 0,
    val lastTime: Long = 0L
)