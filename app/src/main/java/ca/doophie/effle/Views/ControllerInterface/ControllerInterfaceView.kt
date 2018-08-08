package ca.doophie.effle.Views.ControllerInterface

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import ca.doophie.effle.Models.ViewModels.DoophieView
import ca.doophie.effle.R
import ca.doophie.koju.UIObjects.Joystick
import ca.doophie.koju.UIObjects.JoystickMovementCallbacks

interface ControllerViewListener {
    fun handleJoystickMoved(strength: Double, angle: Double)

    val screenWidth: Int
    val screenHeight: Int
}

class ControllerView(context: Context, val listener: ControllerViewListener) : DoophieView(context){

    override val rootView = LayoutInflater.from(context).inflate(R.layout.controller_interface_view, null) as RelativeLayout

    var clickIteration = false

    override fun setUpListeners() {
        buildJoystick()
    }

    private fun buildJoystick(){
        val joystick = Joystick(context, listener.screenWidth / 3, listener.screenHeight / 2)
        rootView.addView(joystick)
        joystick.movementCallbacks = object: JoystickMovementCallbacks{
            override fun joystickMoved(strength: Double, angle: Double) {
                listener.handleJoystickMoved(strength, angle)
            }
        }
    }
}