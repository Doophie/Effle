package ca.doophie.effle.Views.ControllerInterface

import android.content.Context
import android.support.v4.content.ContextCompat
import ca.doophie.effle.Models.Colours
import ca.doophie.effle.Models.ViewModels.DoophieManager
import ca.doophie.effle.Models.ViewModels.DoophieView
import ca.doophie.effle.R

interface ControllerManagerListener {
    fun handleJoystickMoved(strength: Double, angle: Double)
}

class ControllerManager(val listener: ControllerManagerListener, dependency: ControllerManagerDependency): DoophieManager(),
// Implements
ControllerViewListener {

    override val screenWidth = dependency.screenWidth
    override val screenHeight = dependency.screenHeight

    override fun makeRootView(context: Context): DoophieView {
        return ControllerView(context, this)
    }

    override fun viewLoaded() {
        // do something
    }

    override fun handleJoystickMoved(strength: Double, angle: Double) {
        listener.handleJoystickMoved(strength, angle)
    }

    class ControllerManagerDependency(
        val screenWidth: Int,
        val screenHeight: Int
    )

}