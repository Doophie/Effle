package ca.doophie.effle.Models

import android.content.Context
import android.support.v4.content.ContextCompat
import ca.doophie.effle.Activities.AdventureActivity
import ca.doophie.effle.R
import ca.doophie.effle.Views.Character.CharacterManager

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

    fun get(context: Context, colour: Int): Int{
        return ContextCompat.getColor(context, colour)
    }
}