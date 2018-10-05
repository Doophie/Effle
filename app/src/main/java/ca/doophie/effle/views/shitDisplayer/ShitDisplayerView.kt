package ca.doophie.effle.views

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import ca.doophie.doophrame.models.viewModels.DoophieView
import ca.doophie.effle.R


interface ShitDisplayerViewListener {
}

class ShitDisplayerView(context: Context, val listener: ShitDisplayerViewListener) : DoophieView(context) {

    override val rootView = LayoutInflater.from(context).inflate(R.layout.view_shitdisplayer, null) as ScrollView

    fun addShitNugget(nugget: String, animDelay: Int){
        val nuggetHolder = TextView(context)


        nuggetHolder.text = nugget

        nuggetHolder.animation = AnimationUtils.loadAnimation(context, R.anim.slide_left)

        rootView.findViewById<LinearLayout>(R.id.shitHolder).addView(nuggetHolder)

        Handler().postDelayed({
            nuggetHolder.animate()
        }, 100.toLong() * animDelay)
    }
}
