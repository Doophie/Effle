package ca.doophie.koju.UIObjects

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import ca.doophie.effle.Models.Colours
import ca.doophie.effle.R
import java.lang.Math.atan2
import kotlin.math.pow

interface JoystickMovementCallbacks {
    fun joystickMoved(strength: Double, angle: Double)
}

class Joystick(context: Context, width: Int, height:  Int):
        SurfaceView(context), View.OnTouchListener {

    var movementCallbacks: JoystickMovementCallbacks? = null

    override fun onTouch(v: View?, e: MotionEvent?): Boolean {
        if(v?.equals(this) == true){
            if(e == null) return true
            if(e.action != MotionEvent.ACTION_UP){
                val displacement = Math.sqrt(Math.pow((e.x - centerX).toDouble(), 2.0)
                                                 + Math.pow((e.y - centerY).toDouble(), 2.0)).toFloat()
                if(displacement < baseRadius)
                    drawJoystick(e.x, e.y)
                else {
                    val ratio = baseRadius / displacement
                    val constrainedX = centerX + (e.x - centerX) * ratio
                    val constrainedY = centerY + (e.y - centerY) * ratio
                    drawJoystick(constrainedX, constrainedY)
                }
            } else {
                drawJoystick(centerX, centerY)
            }
        }
        return true
    }

    init {
        layoutParams = FrameLayout.LayoutParams(width, height)
    }

    private var padColor = Colours.primary
    private var stickColor = Colours.accent

    private val centerX = width / 2f
    private val centerY = height / 2f

    private val baseRadius = Math.min(width, height) / 3f
    private val hatRadius =  baseRadius / 3

    init {
        setOnTouchListener(this)
        this.setBackgroundResource(R.color.stealthBomber)
        this.setBackgroundColor(Color.TRANSPARENT)
        this.setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSPARENT)
    }

    fun reset(){
        drawJoystick(centerX, centerY)
    }

    private fun drawJoystick(newX: Float, newY: Float){
        val myCanvas = holder.lockCanvas()
        val color = Paint()
        myCanvas.drawColor(ContextCompat.getColor(context, R.color.stealthBomber), PorterDuff.Mode.SRC_IN)

        // draw pad
        color.color = padColor
        myCanvas.drawCircle(centerX, centerY, baseRadius, color)

        // draw stick
        color.color = stickColor
        myCanvas.drawCircle(newX, newY, hatRadius, color)

        holder.unlockCanvasAndPost(myCanvas)
        movementCallbacks?.joystickMoved(calculateStrength(newX, newY), calculateAngle(newX, newY))
    }

    private fun calculateAngle(x2: Float, y2: Float): Double {
        val deltaX = x2 - centerX
        val deltaY = centerY - y2
        val result = Math.toDegrees(atan2(deltaY.toDouble(), deltaX.toDouble()))
        return if (result < 0) 360.0 + result else result
    }

    private fun calculateStrength(x2: Float, y2: Float): Double {
        val hyp = (Math.sqrt((centerX-x2).toDouble().pow(2) + (centerY-y2).toDouble().pow(2)))
        return hyp/baseRadius
    }
}