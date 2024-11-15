package na.family.prayer.pedometer.extentions

import java.util.Calendar

fun Long.isNewDay(): Boolean {
    val currentTime = System.currentTimeMillis()
    val lastUpdateCalendar = Calendar.getInstance().apply { timeInMillis = this@isNewDay }
    val currentCalendar = Calendar.getInstance().apply { timeInMillis = currentTime }
    val isNewDay = lastUpdateCalendar.get(Calendar.DAY_OF_YEAR) != currentCalendar.get(Calendar.DAY_OF_YEAR) ||
            lastUpdateCalendar.get(Calendar.YEAR) != currentCalendar.get(Calendar.YEAR)

    return isNewDay
}