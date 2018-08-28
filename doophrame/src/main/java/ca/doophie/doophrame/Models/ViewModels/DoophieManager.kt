package ca.doophie.doophrame.Models.ViewModels

import android.content.Context
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import java.lang.ref.WeakReference

abstract class DoophieManager{

    lateinit var parentViewRef: WeakReference<FrameLayout>
    private lateinit var rootViewRef: WeakReference<ViewGroup?>
    private var doophieView: DoophieView? = null

    val rootView: ViewGroup?
        get() { return rootViewRef.get() }

    abstract fun viewLoaded()

    fun fillView(view: FrameLayout, context: Context, withAnimation: Int = -5) {
        parentViewRef = WeakReference(view)
        doophieView = makeRootView(context)
        doophieView?.addToParent(view)
        if (withAnimation != -5) doophieView?.animation = AnimationUtils.loadAnimation(context, withAnimation)
        view.addView(doophieView)
        doophieView?.animate()
        rootViewRef = WeakReference(doophieView?.rootView)
        doophieView?.setUpListeners()

        viewLoaded()
    }

    abstract fun makeRootView(context: Context): DoophieView

    fun popView(){
        parentViewRef.get()?.removeView(doophieView)
        doophieView = null
    }
}