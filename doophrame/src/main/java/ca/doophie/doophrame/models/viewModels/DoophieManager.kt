package ca.doophie.doophrame.models.viewModels

import android.content.Context
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import io.reactivex.Observable
import java.lang.ref.WeakReference
import java.util.*
import kotlin.properties.Delegates

abstract class DoophieManager{

    abstract class DoophieManagerExitCallback: Observer {
        override fun update(p0: java.util.Observable?, p1: Any?) {
            exit(p1 as Int)
        }

        abstract fun exit(exitCode: Int)
    }

    val exitObservable = java.util.Observable()

    lateinit var parentViewRef: WeakReference<FrameLayout>
    private lateinit var rootViewRef: WeakReference<ViewGroup?>
    var doophieView: DoophieView? = null

    var exit = -523
        set(value) {
            exitObservable.notifyObservers(value)
            popView()
        }

    val rootView: ViewGroup?
        get() { return rootViewRef.get() }

    abstract fun viewLoaded()

    fun fillView(view: FrameLayout, context: Context, exit: DoophieManagerExitCallback, withAnimation: Int = -5) {
        parentViewRef = WeakReference(view)
        doophieView = makeRootView(context)
        doophieView?.addToParent(view)
        if (withAnimation != -5) doophieView?.animation = AnimationUtils.loadAnimation(context, withAnimation)
        doophieView?.animate()
        rootViewRef = WeakReference(doophieView?.rootView)
        doophieView?.setUpListeners()

        viewLoaded()

        exitObservable.addObserver(exit)
    }

    abstract fun makeRootView(context: Context): DoophieView

    fun popView(){
        parentViewRef.get()?.removeView(doophieView?.rootView)
        doophieView = null
    }
}