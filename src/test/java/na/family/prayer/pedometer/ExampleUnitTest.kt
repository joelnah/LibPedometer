package na.family.prayer.pedometer

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val originalTimestamp = 1722388679099L
        val oneDayInMillis = 24 * 60 * 60 * 1000L
        val newTimestamp = originalTimestamp + oneDayInMillis
        println(newTimestamp)
    }
}