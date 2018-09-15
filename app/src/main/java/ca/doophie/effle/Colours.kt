package ca.doophie.effle

import android.content.Context
import android.support.v4.content.ContextCompat

class Colours(context: Context){

    companion object {
        fun init(context: Context?){
            if (context == null) return
            mColours = Colours(context)
        }
        private var mColours: Colours? = null

        val primary: Int get() { return  mColours?.primary ?: -1 }
        val accent: Int get() { return  mColours?.accent ?: -1 }
    }

    var primary: Int = get(context, R.color.colorPrimary)
    var accent: Int = get(context, R.color.colorAccent)
    var stealthBomber: Int = 0

    fun get(context: Context, colour: Int): Int{
        return ContextCompat.getColor(context, colour)
    }
}