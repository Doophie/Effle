package ca.doophie.effle.views

import android.content.Context
import android.view.animation.Animation
import ca.doophie.doophrame.models.viewModels.DoophieManager
import ca.doophie.doophrame.models.viewModels.DoophieView
import ca.doophie.effle.R
import ca.doophie.effle.models.Shit

class ShitDisplayerManager(private val shit: Shit) : DoophieManager(),
// Implements
        ShitDisplayerViewListener {

    val view: ShitDisplayerView
        get() { return (doophieView as ShitDisplayerView) }

    override fun makeRootView(context: Context): DoophieView {
        return ShitDisplayerView(context, this)
    }

    override fun viewLoaded() {
        // do something
        for ((nuggetCount, nugget) in shit.nuggets.withIndex()){
            view.addShitNugget(nugget, nuggetCount)
        }
    }

}
