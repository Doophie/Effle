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

    private lateinit var parentViewRef: WeakReference<FrameLayout>
    var frame: FrameLayout? = parentViewRef.get()
    private lateinit var rootViewRef: WeakReference<ViewGroup?>
    var doophieView: DoophieView? = null

    val rootView: ViewGroup?
        get() { return rootViewRef.get() }

    abstract fun viewLoaded()

    fun fillView(view: FrameLayout, context: Context, withAnimation: Int = -5) {
        parentViewRef = WeakReference(view)
        doophieView = makeRootView(context)
        doophieView?.addToParent(view)
        if (withAnimation != -5) doophieView?.animation = AnimationUtils.loadAnimation(context, withAnimation)
        doophieView?.animate()
        rootViewRef = WeakReference(doophieView?.rootView)
        doophieView?.setUpListeners()

        viewLoaded()
    }

    abstract fun makeRootView(context: Context): DoophieView

    open fun onDetach() {}

    fun popView(){
        onDetach()
        parentViewRef.get()?.removeView(doophieView?.rootView)
        doophieView = null
    }
}