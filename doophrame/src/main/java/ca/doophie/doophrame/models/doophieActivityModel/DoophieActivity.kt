package ca.doophie.doophrame.models.doophieActivityModel

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import ca.doophie.doophrame.extensions.putObject
import ca.doophie.doophrame.models.ObjectSerializer
import java.io.Serializable

abstract class DoophieActivity: AppCompatActivity() {

    abstract val tag: String

    protected open val animIn: Int = 0
    protected open val animOut: Int = 0

    @Synchronized
    protected fun switch(to: DoophieActivity, dependencies: Dependency){
        val switchActivityIntent = Intent(this, to::class.java)

        val depType = to::class.java.toString().split("Activity")[0]
        if (dependencies::javaClass.toString().contains(depType)){
            throw InvalidDependencyTypeException(dependencies::class.java.toString(), depType + "Dependency")
        }

        for ((k, v) in dependencies.dependencies) {
            switchActivityIntent.putObject(k, v)
        }

        startActivity(switchActivityIntent)
        overridePendingTransition(animIn, animOut)
    }

    interface Dependency{
        val dependencies: HashMap<String, Serializable>
    }

    private val activityPrefs: SharedPreferences
        get() { return applicationContext.getSharedPreferences(tag, MODE_PRIVATE) }

    private val appPrefs: SharedPreferences
        get() { return applicationContext.getSharedPreferences("EfflePrefs", MODE_PRIVATE) }

    /***
     * Saves obj with the given key, applies the changes in background
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePref(key: String, obj: Serializable, private: Boolean = false){
        val prefs = if (private) activityPrefs.edit() else appPrefs.edit()
        prefs.putString(key, ObjectSerializer.serialize(obj))
        prefs.apply()
    }

    /***
     * Saves obj with the given key, applies the changes immediately and is a blocking call
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePrefNow(key: String, obj: Serializable, private: Boolean = false){
        val prefs = if (private) activityPrefs.edit() else appPrefs.edit()
        prefs.putString(key, ObjectSerializer.serialize(obj))
        prefs.commit()
    }


    protected fun <T: Serializable>getPref(key: String, private: Boolean = true): T? {
        val prefs = if (private) activityPrefs else appPrefs
        val obj = ObjectSerializer.deserialize<T>(prefs.getString(key, ""))
        return obj
    }

    protected val screenWidth: Int
        get() {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getRealSize(size)
            return size.x
        }

    protected val screenHeight: Int
        get() {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getRealSize(size)
            return size.y
        }

    class InvalidDependencyTypeException(
            private val used: String, private val expected: String):
            Exception(){
        override val message: String?
            get() = "Expected dependency type $expected, but found $used\nDependency type must share naming convention with Activity type."
    }

    class InvalidPrefTypeException: Exception(){
        override val message: String?
            get() = "Invalid pref type being saved. Valid types are: String, Float, Int, Long, Boolean."
    }
}