package ca.doophie.doophrame.Models.DoophieActivityModel

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class DoophieActivity: AppCompatActivity() {

    abstract val TAG: String

    protected open val animIn: Int = 0
    protected open val animOut: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected inline fun switch(to: DoophieActivity, dependencies: Dependency){
        val switchActivityIntent = Intent(this, to::class.java)

        val depType = to::class.java.toString().split("Activity")[0]
        if (dependencies::javaClass.toString().contains(depType)){
            throw InvalidDependencyTypeException(dependencies::class.java.toString(), depType + "Dependency")
        }

        for ((k, v) in dependencies.dependencies) {
            switchActivityIntent.putExtra(k, v)
        }

        startActivity(switchActivityIntent)
        overridePendingTransition( animIn, animOut )
    }

    interface Dependency{
        val dependencies: HashMap<String, String>


    }

    private val activityPrefs: SharedPreferences
        get() { return applicationContext.getSharedPreferences(TAG, MODE_PRIVATE) }

    private val appPrefs: SharedPreferences
        get() { return applicationContext.getSharedPreferences("EfflePrefs", MODE_PRIVATE) }

    /***
     * Saves obj with the given key, applies the changes in background
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePref(key: String, obj: Any, private: Boolean = false){
        val prefs = if (private) activityPrefs.edit() else appPrefs.edit()
        when (obj::class.java){
            String::class.java -> prefs.putString(key, obj as String)
            Int::class.java -> prefs.putInt(key, obj as Int)
            Long::class.java -> prefs.putLong(key, obj as Long)
            Boolean::class.java -> prefs.putBoolean(key, obj as Boolean)
            else -> throw InvalidPrefTypeException()
        }
        prefs.apply()
    }

    /***
     * Saves obj with the given key, applies the changes immediately and is a blocking call
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePrefNow(key: String, obj: Any, private: Boolean = false){
        val prefs = if (private) activityPrefs.edit() else appPrefs.edit()
        when (obj::class.java){
            String::class.java -> prefs.putString(key, obj as String)
            Int::class.java -> prefs.putInt(key, obj as Int)
            Long::class.java -> prefs.putLong(key, obj as Long)
            Float::class.java -> prefs.putFloat(key, obj as Float)
            Boolean::class.java -> prefs.putBoolean(key, obj as Boolean)
            else -> throw InvalidPrefTypeException()
        }
        prefs.commit()
    }

    protected fun getPref(key: String, objType: Class<*>, private: Boolean = true): Any{
        val prefs = if (private) activityPrefs else appPrefs
        val obj = when (objType){
            String::class.java -> prefs.getString(key, "")
            Int::class.java -> prefs.getInt(key, 0)
            Long::class.java -> prefs.getLong(key, 0)
            Float::class.java -> prefs.getFloat(key, 0f)
            Boolean::class.java -> prefs.getBoolean(key, false)
            else -> throw InvalidPrefTypeException()
        }
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