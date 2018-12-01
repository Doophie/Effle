package ca.doophie.effle.activities.newKindOfView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ca.doophie.doophrame.models.activity.DoophieView
import ca.doophie.effle.R
import kotlinx.android.synthetic.main.activity_newkindof.view.*

interface NewKindOfViewCallbacks {
    fun closeView()
}

class NewKindOfView(context: Context): DoophieView(context) {

    lateinit var listener: NewKindOfViewCallbacks

    override val layout: Int = R.layout.view_newkindofview

    override val tag: String = "[NewKindOfView]"

    val newKindOfText: TextView?
        get() = view?.findViewById(R.id.newKindOfViewText) as? TextView?

    val closeButton: Button?
        get() = view?.findViewById(R.id.closeViewButton) as? Button?

    fun randomizeBackground() {
        var newColour = ""
        for (i in 0 until 6) {
            newColour += ((Math.random() * 100) % 10).toInt().toString()
        }

        val red = (newColour[0].toString() + newColour[1]).toInt()
        val green = (newColour[2].toString() + newColour[3]).toInt()
        val blue = (newColour[4].toString() + newColour[5]).toInt()

        view?.setBackgroundColor(Color.rgb(red, green, blue))
        newKindOfText?.text = "Color set to: #$newColour"
    }

    fun setUpListeners() {
        closeButton?.setOnClickListener {
            listener.closeView()
        }
    }

}