package na.family.prayer.pedometer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import na.family.prayer.pedometer.data.PedometerData

object PedometerFlow {
    internal val dataFlow = MutableStateFlow(PedometerData())

    val dataStateFlow: StateFlow<PedometerData> = dataFlow.stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.Eagerly,
        initialValue = PedometerData()
    )
}