package ca.doophie.doophrame.models.viewModels

import android.content.Context
import android.util.AttributeSet
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import java.lang.ref.WeakReference

abstract class DoophieView(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {

    abstract val layoutID: Int

    val contentView: ViewGroup

    init {
        contentView = LayoutInflater.from(context).inflate(layoutID, null, false) as ViewGroup

        this.addView(contentView)
    }

}