package ca.doophie.doophrame.models.activity

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import ca.doophie.doophrame.models.ObjectSerializer
import ca.doophie.doophrame.models.activity.DoophieTraveller.Companion.DOOPHIE_TRAVELLER
import java.io.Serializable

abstract class DoophieView(context: Context) :
        FrameLayout(context, null, 0) {

    abstract val layout: Int

    abstract val tag: String

    var view: View? = null

    fun lateInit(parent: ViewGroup) {
        parent.addView(this)
        view = LayoutInflater.from(context).inflate(layout, null)
        this.addView(view)
        Log.d(tag, "Attached view")
        traveller?.view = this
    }

    private var traveller: DoophieTravellable? = null

    var dependencies: Bundle? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        traveller = dependencies?.get(DOOPHIE_TRAVELLER) as DoophieTravellable?
    }

    private val viewPrefs: SharedPreferences
        get() { return context.getSharedPreferences(tag, AppCompatActivity.MODE_PRIVATE) }

    private val appPrefs: SharedPreferences
        get() { return context.getSharedPreferences("AppPrefs", AppCompatActivity.MODE_PRIVATE) }


    /***
     * Saves obj with the given key, applies the changes in background
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePref(key: String, obj: Serializable, private: Boolean = false){
        val prefs = if (private) viewPrefs.edit() else appPrefs.edit()
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
        val prefs = if (private) viewPrefs.edit() else appPrefs.edit()
        prefs.putString(key, ObjectSerializer.serialize(obj))
        prefs.commit()
    }


    protected fun <T: Serializable>getPref(key: String, private: Boolean = true): T? {
        val prefs = if (private) viewPrefs else appPrefs
        val obj = ObjectSerializer.deserialize<T>(prefs.getString(key, ""))
        return obj
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