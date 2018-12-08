package ca.doophie.doophrame.models.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import ca.doophie.doophrame.extensions.putObject
import java.io.Serializable
import java.lang.ref.WeakReference

interface DoophieTravellable: Serializable {
    var activity: DoophieActivity?
    var view: DoophieView?
    fun switch(to: DoophieTraveller, dependencies: DoophieTraveller.Dependency)
    fun present(to: DoophieTraveller, dependencies: DoophieTraveller.Dependency, inView: ViewGroup)
}

abstract class DoophieTraveller: DoophieTravellable {

    companion object {
        const val DOOPHIE_TRAVELLER = "AEFNAUWGWYVTUBYINULMLKJNHBGVFCYVGJBHKANJGMRKSSHBEYFKEUCINMWINRHG"
    }

    abstract val activityClass: Class<*>

    private var hasStarted: Boolean = false

    @Transient
    private var __activityRef: WeakReference<DoophieActivity?>? = null
    override var activity: DoophieActivity?
        get() = __activityRef?.get()
        set(value) {
            __activityRef = WeakReference(value)
            if (!hasStarted) {
                worker.traveller = this
                worker.start()
                hasStarted = true
            }
        }

    @Transient
    private var __viewRef: WeakReference<DoophieView?>? = null
    override var view: DoophieView?
        get() = __viewRef?.get()
        set(value) {
            __viewRef = WeakReference(value)
            if (!hasStarted) {
                worker.traveller = this
                worker.start()
                hasStarted = true
            }
        }

    abstract var animIn: Int
    abstract var animOut: Int

    abstract val worker: DoophieWorker

    @Synchronized
    override fun switch(to: DoophieTraveller, dependencies: Dependency){
        val context = activity?.applicationContext ?: view?.context ?: return

        val switchActivityIntent = Intent(context, to.activityClass)

        switchActivityIntent.putObject(DOOPHIE_TRAVELLER, to)

        for ((k, v) in dependencies.dependencies) {
            switchActivityIntent.putObject(k, v)
        }

        activity?.startActivity(switchActivityIntent)
        activity?.overridePendingTransition(animIn, animOut)


    }

    @Synchronized
    override fun present(to: DoophieTraveller, dependencies: Dependency, inView: ViewGroup) {
        val context = activity?.applicationContext ?: view?.context ?: return

        val viewIntent = Intent(context, to.activityClass)

        viewIntent.putObject(DOOPHIE_TRAVELLER, to)

        for ((k, v) in dependencies.dependencies) {
            viewIntent.putObject(k, v)
        }

        to.view?.lateInit(inView)

        to.view?.dependencies = viewIntent.extras

    }

    interface Dependency{
        val dependencies: HashMap<String, Serializable>
    }
}