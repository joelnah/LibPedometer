package na.family.prayer.pedometer.utils
import com.google.gson.Gson

object GsonUtil {
    private val gson = Gson()

    fun <T> toJson(obj: T): String {
        return gson.toJson(obj)
    }

    fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

//    inline fun <reified T> fromJson(json: String): T {
//        return gson.fromJson(json, object : TypeToken<T>() {}.type)
//    }
}