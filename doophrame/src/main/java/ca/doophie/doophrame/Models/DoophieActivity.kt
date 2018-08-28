package ca.doophie.doophrame.Models

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.support.v7.app.AppCompatActivity

abstract class DoophieActivity<DependencyType: DoophieActivity.Dependency>: AppCompatActivity() {

    abstract val TAG: String

    protected open val animIn: Int = 0
    protected open val animOut: Int = 0

    protected fun switch(to: DoophieActivity<*>, dependencies: DependencyType){
        val switchActivityIntent = Intent(this, to::class.java)


        for ((k, v) in dependencies.dependencies){
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

    protected fun savePrefSafe(key: String, obj: Any, private: Boolean = false){
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


    class InvalidPrefTypeException: Exception(){
        override val message: String?
            get() = "Invalid pref type being saved. Valid types are: String, Float, Int, Long, Boolean."
    }
}