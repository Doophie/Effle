package ca.doophie.doophrame.Models.ViewModels

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout

open class DoophieView(context: Context): FrameLayout(context) {

    open val rootView: ViewGroup? = null //LayoutInflater.from(context).inflate(R.id.main_rootView, null)

    fun addToParent(parentView: ViewGroup){
        parentView.addView(rootView)
    }

    open fun setUpListeners() {}
}