package ca.doophie.doophrame.Models.ViewModels

import android.content.Context
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import java.lang.ref.WeakReference

open class DoophieView(context: Context) {

    open val rootView: ViewGroup? = null // inflate this as a layout
    private val contextRef = WeakReference<Context>(context)
    val context: Context? get() { return contextRef.get() }

    var animation: Animation?
        get() { return rootView?.animation }
        set(value) { rootView?.animation = value }

    fun animate() {
        rootView?.animate()
    }

    fun addToParent(parentView: ViewGroup){
        parentView.addView(rootView)
    }

    open fun setUpListeners() {}
}