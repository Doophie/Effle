package ca.doophie.doophrame.models.activity

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ca.doophie.doophrame.R
import ca.doophie.doophrame.extensions.getObject
import ca.doophie.doophrame.extensions.putObject
import ca.doophie.doophrame.models.ObjectSerializer
import ca.doophie.doophrame.models.activity.DoophieTraveller.Companion.DOOPHIE_TRAVELLER
import java.io.Serializable

interface BaseCallbacks {
    fun switchToActivity(traveller: DoophieTraveller, dependency: DoophieTraveller.Dependency)
}

abstract class DoophieActivity: AppCompatActivity() {

    var baseListener: BaseCallbacks? = null

    abstract val layout: Int

    abstract val tag: String

    private var traveller: DoophieTravellable? = null

    private val activityPrefs: SharedPreferences
        get() { return applicationContext.getSharedPreferences(tag, MODE_PRIVATE) }

    private val appPrefs: SharedPreferences
        get() { return applicationContext.getSharedPreferences("AppPrefs", MODE_PRIVATE) }

    protected var dependencies: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        dependencies = intent
        traveller = try {
            dependencies?.getObject(DOOPHIE_TRAVELLER) as? DoophieTravellable?
        } catch (e: java.lang.Exception) { null }

        if (traveller == null) {
            initAsRoot()
            return
        }

        traveller?.activity = this
    }

    private fun initAsRoot() {
        Log.d("[DoophieActivity]", "Initializing as root.")
        traveller = BaseTraveller()
        traveller?.activity = this
    }

    /***
     * Saves obj with the given key, applies the changes in background
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePref(key: String, obj: Serializable?, private: Boolean = false){
        val prefs = if (private) activityPrefs.edit() else appPrefs.edit()

        if (obj == null) {
            prefs.remove(key)
        } else {
            prefs.putString(key, ObjectSerializer.serialize(obj))
        }

        prefs.apply()
    }

    /***
     * Saves obj with the given key, applies the changes immediately and is a blocking call
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePrefNow(key: String, obj: Serializable?, private: Boolean = false){
        val prefs = if (private) activityPrefs.edit() else appPrefs.edit()

        if (obj == null) {
            prefs.remove(key)
        } else {
            prefs.putString(key, ObjectSerializer.serialize(obj))
        }

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

class BaseTraveller: DoophieTraveller() {
    override val activityClass: Class<*> = DoophieActivity::class.java
    override var animIn: Int = R.anim.fade_in
    override var animOut: Int = R.anim.fade_out
    override val worker: DoophieWorker = BaseWorker()

    init {
        worker.traveller = this
    }
}

class BaseWorker: DoophieWorker(), BaseCallbacks {

    override fun switchToActivity(traveller: DoophieTraveller, dependency: DoophieTraveller.Dependency) {
        this.traveller.switch(traveller, dependency)
    }

    override fun start() {
        // useless
        (traveller.activity)?.baseListener = this
    }
}